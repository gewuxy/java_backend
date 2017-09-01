package cn.medcn.api.wexin;

import cn.medcn.api.wexin.security.Principal;
import cn.medcn.api.wexin.security.SecurityUtils;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.CookieUtils;
import cn.medcn.common.utils.FileUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.user.service.RechargeOrderService;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.pay.*;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lixuan on 2017/8/4.
 */
@Controller
@RequestMapping(value = "/weixin/credits")
public class WXPayController extends BaseController {

    @Value("${WeChat.pay.callback}")
    protected String wxPayCallback;

    @Value("${WeChat.pay.mch_id}")
    protected String mchId;

    @Value("${WeChat.pay.api_key}")
    protected String payApiKey;

    @Value("${WeChat.Server.app_id}")
    protected String appId;

    @Value("${WeChat.pay.domain}")
    protected String domain;

    @Autowired
    protected CreditsService creditsService;

    @Autowired
    protected RechargeOrderService rechargeOrderService;

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String toPay(Model model) throws SystemException {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Credits credits = creditsService.doFindMyCredits(principal.getId());
        model.addAttribute("credits", credits);
        return "/weixin/credits/toBuy";
    }


    @RequestMapping(value = "/unified/order")
    @ResponseBody
    public String unifiedOrder(Integer payType, HttpServletRequest request){
        if (payType == null){
            payType = 0;//微信支付
        }
        if (payType == 0){
            return buildWxPay(request);
        } else {

        }
        return null;
    }

    /**
     * 微信统一下单API
     * @param request
     * @return
     */
    protected String buildWxPay(HttpServletRequest request){
        String openid = CookieUtils.getCookieValue(request, WeixinConfig.COOKIE_NAME_OPEN_ID);
        WXPayConfig config = new MyWXPayConfig(appId, mchId, payApiKey, domain);
        String total_fee = request.getParameter("total_fee");
        if (CheckUtils.isEmpty(total_fee)){
            return error("支付金额不能为空");
        }
        try {
            //执行微信统一下单接口
            WXPay wxPay = new WXPay(config, wxPayCallback, false);
            Map<String, String> requestData = Maps.newHashMap();
            String outTradeNo = UUIDUtil.getNowStringID();
            requestData.put("body", "YaYa医师象数充值");
            requestData.put("out_trade_no", outTradeNo);
            requestData.put("total_fee", String.valueOf(Integer.valueOf(total_fee) * 100));
            requestData.put("spbill_create_ip", request.getRemoteAddr());
            requestData.put("trade_type", "JSAPI");
            requestData.put("openid", openid);
            Map<String, String> unifiedOrder = wxPay.unifiedOrder(requestData);
            if (!WeixinConfig.DEFAULT_REPLY_SUCESS.toUpperCase().equals(unifiedOrder.get("result_code"))){
                return error();
            }

            //生成jsAPI参数
            Map<String, String> resultMap = Maps.newHashMap();
            resultMap.put("appId", appId);
            resultMap.put("nonceStr", WXPayUtil.generateNonceStr());
            resultMap.put("signType", WXPayConstants.MD5);
            resultMap.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
            resultMap.put("package", "prepay_id="+unifiedOrder.get("prepay_id"));
            String paySign = WXPayUtil.generateSignature(resultMap, payApiKey);
            resultMap.put("paySign", paySign);

            //生成本地订单
            rechargeOrderService.createRechargeOrder(SecurityUtils.getCurrentUserInfo().getId(),
                    outTradeNo, Integer.valueOf(total_fee));

            return success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
    }


    @RequestMapping(value = "/callback")
    @ResponseBody
    public String callback(HttpServletRequest request) throws Exception {
        //接收微信回调数据
        String responseXML = FileUtils.readFromInputStream(request.getInputStream());
        Map<String, String> callbackMap = WXPayUtil.xmlToMap(responseXML);
        String resultCode = callbackMap.get("result_code");
        if (WeixinConfig.DEFAULT_REPLY_SUCESS.equalsIgnoreCase(resultCode)){
            String orderNo = callbackMap.get("out_trade_no");
            String thirdPartyTradeNo = callbackMap.get("transaction_id");
            rechargeOrderService.modifyOrderPayed(orderNo, thirdPartyTradeNo);
        }

        //回复微信
        Map<String, String> answerMap = Maps.newHashMap();
        answerMap.put("return_code", WeixinConfig.DEFAULT_REPLY_SUCESS.toUpperCase());
        answerMap.put("return_msg", WeixinConfig.RETURN_SUCCESS_MSG);
        return WXPayUtil.mapToXml(answerMap);
    }
}
