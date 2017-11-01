package cn.medcn.csp.controller.web;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.service.ChargeService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lixuan on 2017/9/12.
 */
@Controller
@RequestMapping("/mgr/charge/")
public class WebChargeController extends CspBaseController {


    @Autowired
    protected ChargeService chargeService;

    @Value("${app.yaya.base}")
    protected String appBase;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${appId}")
    private String appId;


    /**
     * 购买流量，需要传递flux(流量值),channel(支付渠道)
     */
    @RequestMapping("/toCharge")
    public String toCharge(Integer flux, String channel, HttpServletRequest request,Model model)  {
        String path = this.getClass().getClassLoader().getResource("privateKey.pem").getPath();
        Pingpp.apiKey = apiKey;
        String orderNo = StringUtils.nowStr();
        String userId = getWebPrincipal().getId();
        String ip = request.getRemoteAddr();
        Pingpp.privateKeyPath = path;
        Charge charge = null;


        try {
            //生成Charge对象
            charge = chargeService.createCharge(orderNo, appId, flux, channel, ip);
        } catch (RateLimitException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (APIException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (ChannelException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (APIConnectionException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        //创建订单
        chargeService.createOrder(userId, orderNo, flux, channel);
        model.addAttribute("charge",charge.toString());
        return localeView("/userCenter/newPage");

    }



    /**
     * paypal支付创建订单
     * @param flux 流量值
     * @return
     */
    @RequestMapping("/createOrder")
    public String createOrder(Integer flux) throws SystemException {
        if(flux == null){
            throw new SystemException("流量值不能为空");
        }
        APIContext apiContext = new APIContext("AeSVNO9AifaUTa0UEl1wZ2x0uIdE-CCPENpM-z9xOnVsS2hDW2XoaDzn9P_lh4FJzBEVMjVIBky_N9cR", "EOdCsjuJj1RnU90_2eS5PHrinLO-v61cm6k7ulBlOcVL4IvCg09coXP4P0pH9Zz4da2EFMAlZ_myt8M2", "sandbox");

        appBase = "http://medcn.synology.me:8889/";
        Payment payment = generatePayment(flux);
        Payment createdPayment;
        String url = null;
        try {
            createdPayment = payment.create(apiContext);
            Iterator links = createdPayment.getLinks().iterator();
            while (links.hasNext()) {
                Links link = (Links) links.next();
                //用户登录地址
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    url = link.getHref();
                    break;
                }
            }
        } catch (PayPalRESTException e) {
            throw new SystemException(e.getMessage());
        }

        if(url != null){
            //创建订单
            String userId = getWebPrincipal().getId();
            String paymentId = createdPayment.getId();
            chargeService.createPaypalWebOrder(userId,flux,paymentId);
            return "redirect:" + url;
        }

        return error();
    }

    private Payment generatePayment(Integer flux) {
        Payment createdPayment = null;

        //建立金额与币种
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(flux + ".00");
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        //建立支付方式
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setNoteToPayer("Pay Order ");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(appBase + "mgr/charge/cancel");
        redirectUrls.setReturnUrl(appBase + "mgr/charge/callback");//回调路径
        payment.setRedirectUrls(redirectUrls);
        return payment;
    }


    /**
     * paypal支付回调
     * @param paymentId
     * @param PayerID
     * @return
     * @throws SystemException
     */
    @RequestMapping("/callback")
    public String callback(String paymentId,String PayerID ) throws SystemException {
        APIContext apiContext = new APIContext("AeSVNO9AifaUTa0UEl1wZ2x0uIdE-CCPENpM-z9xOnVsS2hDW2XoaDzn9P_lh4FJzBEVMjVIBky_N9cR", "EOdCsjuJj1RnU90_2eS5PHrinLO-v61cm6k7ulBlOcVL4IvCg09coXP4P0pH9Zz4da2EFMAlZ_myt8M2", "sandbox");
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(PayerID);

        Payment createdPayment = null;
        try {
            createdPayment = payment.execute(apiContext, paymentExecution);
        } catch (PayPalRESTException e) {
            throw new SystemException(e.getMessage());
        }

        if(paymentId.equals(createdPayment.getId())){
                //查找订单
                FluxOrder order = new FluxOrder();
                order.setTradeId(paymentId);
                order = chargeService.selectOne(order);
                //没有相关订单
                if(order == null ){
                   throw new SystemException("没有相关订单");
                }
                //更新订单状态，修改用户流量值
                chargeService.updateOrderAndUserFlux(order);
                return localeView("/userCenter/toFlux");
        }


        return "";
    }

    @RequestMapping("/cancel")
    public String cancel(){
        return "";
    }
}







