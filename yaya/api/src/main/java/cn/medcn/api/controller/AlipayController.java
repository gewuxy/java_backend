package cn.medcn.api.controller;

import cn.medcn.api.dto.Principal;
import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.user.model.RechargeOrder;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.RechargeOrderService;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**支付宝支付
 * Created by LiuLP on 2017/4/26.
 */
@Controller
@RequestMapping("api/alipay")
public class AlipayController {

    private static Log log = LogFactory.getLog(AlipayController.class);

    @Autowired
    private AppUserService appUserService;

    @Value("${alipay.gatWay}")
    String gatWay;

    @Value("${alipay.appId}")
    String appId;

    @Value("${alipay.privateKey}")
    String privateKey;

    @Value("${alipay.publicKey}")
    String alipayPublicKey;

    @Value("${alipay.sellerEmail}")
    String seller;

    @Value("${alipay.notifyUrl}")
    String  notifyUrl;

    @Autowired
    protected RechargeOrderService rechargeOrderService;

    /**
     * 象数充值，生成签名参数给前台调起支付
     * @param body
     * @param subject
     * @return
     */
    @RequestMapping(value = "/recharge",method = RequestMethod.POST)
    @ResponseBody
    public String recharge(String body,String subject,Float totalAmount)  {
        if(StringUtils.isEmpty(subject)){
            return APIUtils.error(SpringUtils.getMessage("alipay.subject.notnull"));
        }
        if(totalAmount == null || totalAmount <= 0){
            return APIUtils.error(SpringUtils.getMessage("alipay.price.error"));
        }
        AlipayClient alipayClient = new DefaultAlipayClient(gatWay,appId,privateKey, Constants.FORMAT,Constants.CHARSET,alipayPublicKey,Constants.SIGN_TYPE);

        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        String outTradeNo = UUIDUtil.getNowStringID();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(outTradeNo);
        model.setTimeoutExpress(Constants.TIMEOUT);
        model.setTotalAmount(totalAmount+"");
        model.setProductCode(Constants.PRODUCTCODE);
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            LogUtils.info(log,response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            //生成订单
            RechargeOrder rechargeOrder = getRechargeOrder(totalAmount, outTradeNo);
            appUserService.executeRechargeOrder(rechargeOrder);
            return APIUtils.success(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            LogUtils.error(log,e.getMessage());
            return APIUtils.error(e.getMessage());
        }

    }




    /**
     * 接收支付宝回调
     * @return
     */
    @RequestMapping("/notify")
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = getParamMap(request);
            LogUtils.info(log,"支付宝返回的数据:"+JSONObject.toJSONString(params));
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayPublicKey, Constants.CHARSET, Constants.SIGN_TYPE);
            String outTradeNo = request.getParameter("out_trade_no").trim(); // 本次交易订单号
            if (signVerified) {
                LogUtils.info(log,"签名正确");
                String totalAmount = request.getParameter("total_amount").trim(); // 本地交易总金额
                String app_id = request.getParameter("app_id").trim(); // APPID
                String tradeNo = request.getParameter("trade_no").trim(); // 支付宝交易号
                String tradeStatus = request.getParameter("trade_status").trim(); // 交易状态
                Float total = Float.valueOf(totalAmount);
                //查找有无此订单
                RechargeOrder rechargeOrder = new RechargeOrder();
                rechargeOrder.setPrice(total);
                rechargeOrder.setOrderNo(outTradeNo);
                RechargeOrder order = appUserService.findRechargeOrder(rechargeOrder);

                if("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)){
                    //买家付款成功
                    if (order != null && appId.equals(app_id)) {
                        response.getWriter().write("success");
                        order.setStatus(1);
                        order.setUpdateTime(new Date());
                        order.setTradeNo(tradeNo);
                        int count = appUserService.updateRechargeOrder(order);
                        if (count == 3) {
                            LogUtils.info(log,"订单支付成功");
                        } else {
                            LogUtils.error(log,"更新订单操作不完全");
                        }

                    }else{
                        response.getWriter().write("failure");
                        LogUtils.info(log,"支付成功，但是order或seller,appId有误");
                        LogUtils.info(log,"订单是否为空:"+(order == null));
                        LogUtils.info(log,"appId是否正确"+appId.equals(app_id));
                    }
                }else{
                    response.getWriter().write("failure");
                    LogUtils.info(log,"买家付款失败,"+"交易状态:"+tradeStatus);
                }
            }else{
                response.getWriter().write("failure");
                LogUtils.error(log,"签名错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.error(log,e.getMessage());
        }
    }


    private Map<String, String> getParamMap(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    private RechargeOrder getRechargeOrder(Float totalAmount, String outTradeNo) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        return rechargeOrderService.generateOrder(principal.getId(), outTradeNo, totalAmount);
    }
}
