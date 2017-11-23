package cn.medcn.csp.controller.web;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.service.ChargeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    @Value("${app.csp.base}")
    protected String appBase;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${appId}")
    private String appId;


    @Value("${paypal_clientId}")
    protected String clientId;

    @Value("${paypal_secret}")
    protected String clientSecret;

    @Value("${paypal_mode}")
    protected String mode;


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
            charge = chargeService.createCharge(orderNo,appId, flux, channel, ip,appBase);
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

        //微信扫码支付
        if("wx_pub_qr".equals(channel)){
            return localeView("/userCenter/wxPay");
        }
        return localeView("/userCenter/newPage");




    }



    /**
     * 创建paypal支付订单
     * @param flux 流量值
     * @return
     */
    @RequestMapping("/createOrder")
    public String createOrder(Integer flux) throws SystemException {
        if(flux == null){
            throw new SystemException("please enter flux amount");
        }
        //正式线mode为live，测试线mode为sandbox
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        Payment payment = chargeService.generatePayment(flux,appBase);
        Payment responsePayment;
        String url = null;
        try {
            //响应对象
            responsePayment = payment.create(apiContext);
            Iterator links = responsePayment.getLinks().iterator();
            while (links.hasNext()) {
                Links link = (Links) links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    //用户登录paypal页面 url
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
            String paymentId = responsePayment.getId();
            chargeService.createPaypalWebOrder(userId,flux,paymentId);
            return "redirect:" + url;
        }

        return error();
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
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
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

        //支付成功
        if(createdPayment.getState().equals("approved")){
                //查找订单
                FluxOrder order = new FluxOrder();
                order.setTradeId(paymentId);
                order = chargeService.selectOne(order);
                //没有相关订单
                if(order == null ){
                   throw new SystemException("No related orders");
                }
                //更新订单状态，修改用户流量值
                chargeService.updateOrderAndUserFlux(order);
                return "redirect:/mgr/charge/success?money="+(order.getFlux()/1024);
        }


        return "";
    }

    @RequestMapping("/cancel")
    public String cancel(){
        return "";
    }


    /**
     * 支付成功跳转页面
     * @param money
     * @param model
     * @return
     */
    @RequestMapping("/success")
    public String success(Float money,Model model){
        model.addAttribute("money",money);
        return localeView("/userCenter/paySuccess");
    }


    /**
     * 异步获取订单状态
     * @param tradeId
     * @return
     */
    @RequestMapping("/orderStatus")
    @ResponseBody
    public String orderState(String tradeId){

        Object status = redisCacheUtils.getCacheObject(tradeId);
        if(status != null){
            //订单支付成功
            return success();
        }else{
            return error();
        }


    }
}







