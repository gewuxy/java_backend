package cn.medcn.csp.controller.web;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.service.ChargeService;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspPackageService;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/9/12.
 */
@Controller
@RequestMapping("/mgr/charge/")
public class WebChargeController extends CspBaseController {


    @Autowired
    protected ChargeService chargeService;

    @Autowired
    protected CspPackageOrderService cspPackageOrderService;

    @Autowired
    protected CspPackageService cspPackageService;

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
    public String toCharge(Integer flux, String channel, HttpServletRequest request, Model model) {
        // 流量值错误
        if (flux == null || FluxOrder.getInternalPrice(flux) == null) {
            return error(local("flux.err.amount"));
        }
        //支付渠道为空
        if (StringUtils.isEmpty(channel)) {
            return error(local("charge.err.channel"));
        }
        String path = this.getClass().getClassLoader().getResource("privateKey.pem").getPath();
        Pingpp.apiKey = apiKey;
        String orderNo = StringUtils.nowStr();
        String userId = getWebPrincipal().getId();
        String ip = request.getRemoteAddr();
        Pingpp.privateKeyPath = path;
        Charge charge = null;

        Float money = FluxOrder.getInternalPrice(flux);

        try {
            //生成Charge对象
            charge = chargeService.createCharge(orderNo, money, channel, ip, "流量充值", appId);
        } catch (Exception e) {
            e.printStackTrace();
            return error(local("charge.fail"));
        }
        //创建订单
        chargeService.createOrder(userId, orderNo, flux, channel);
        model.addAttribute("charge", charge.toString());

        //微信扫码支付
        if ("wx_pub_qr".equals(channel)) {
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

        if(FluxOrder.getOverseasPrice(flux) == null){
            throw new SystemException("Incorrect flux amount");
        }
        //正式线mode为live，测试线mode为sandbox
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        Float money = FluxOrder.getOverseasPrice(flux);
        Payment payment = chargeService.generatePayment(money);
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
            if(paymentId.contains(CspConstants.PACKAGE_ORDER_FLAG)){  //套餐购买支付成功
                CspPackageOrder condition = new CspPackageOrder();
                condition.setTradeId(paymentId);
                CspPackageOrder order = cspPackageOrderService.selectOne(condition);
                if (order == null) {
                    throw new SystemException("No related orders");
                }
                //更新订单状态，修改用户套餐信息
                Integer oldPackageId = cspPackageOrderService.updateOrderAndUserPackageInfo(order);
                //更新用户套餐信息缓存
                updatePackagePrincipal(order.getUserId());
                updatePackageMsg(order.getUserId(),order.getPackageId(),oldPackageId == null ? Constants.NUMBER_THREE:Constants.NUMBER_ONE);
                boolean yearType = cspPackageOrderService.yearPay(order.getPackageId(),order.getShouldPay());
                Map<String,Object> results = cspPackageService.getOrderParams(order.getPackageId(),yearType == true ? order.getNum() * 12 : order.getNum(),Constants.NUMBER_ZERO);
                return "redirect:/mgr/charge/success?money="+ results.get("money");
            }else{  //流量充值支付成功
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
                return "redirect:/mgr/charge/success?money="+(order.getMoney());
            }
        }
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







