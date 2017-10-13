package cn.medcn.csp.controller.api;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.csp.utils.SignatureUtil;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.service.ChargeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixuan on 2017/9/12.
 */
@Controller
@RequestMapping("/api/charge/")
public class ChargeController extends BaseController {


    @Autowired
    protected ChargeService chargeService;



    /**
     * 购买流量，需要传递flux(流量值),channel(支付渠道)
     */
    @RequestMapping("/toCharge")
    @ResponseBody
    public String toCharge(Integer flux, String channel, HttpServletRequest request) throws Exception {
        String path = this.getClass().getClassLoader().getResource("privateKey.pem").getPath();
        Pingpp.apiKey = "sk_test_nz1yT0O8mjT4yf1WjPvbrDm1";
        String appId = "app_LiH0mPanX9OGDS04";
        String orderNo = StringUtils.nowStr();
        String userId = SecurityUtils.get().getId();
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
        return success(charge);

    }

    @RequestMapping("/test")
    public String testCharge() {
        return "/charge/toCharge";
    }

    /**
     * 异步回调
     */
    @RequestMapping("/callback")
    @ResponseBody
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取签名
        String signature = SignatureUtil.getSignature(request);
        // 获得 http body 内容
        String body = null;
        try {
            body = SignatureUtil.getData(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取ping++公钥
        PublicKey publicKey = null;
        try {
            String path = this.getClass().getClassLoader().getResource("publicKey.pem").getPath();
            publicKey = SignatureUtil.getPubKey(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //验证签名
        Boolean isTrue = null;
        try {
            isTrue = SignatureUtil.verifyData(body, signature, publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (isTrue) {
            JSONObject event = JSON.parseObject(body);
            //支付成功事件
            if ("charge.succeeded".equals(event.get("type"))) {
                JSONObject data = JSON.parseObject(event.get("data").toString());
                JSONObject object = JSON.parseObject(data.get("object").toString());
                String orderNo = (String) object.get("order_no");
                //查找订单
                FluxOrder condition = new FluxOrder();
                condition.setTradeId(orderNo);
                FluxOrder result = chargeService.selectOne(condition);
                if (result != null) {
                    //更新订单状态，修改用户流量值
                    chargeService.updateOrderAndUserFlux(result);
                    response.setStatus(200);
                } else {
                    //没有找到订单
                    response.setStatus(500);
                }
            }
        } else {
            //签名错误
            response.setStatus(500);
        }
    }


    /**
     * paypal支付创建订单
     * @param flux 流量值
     * @return
     */
    @RequestMapping("/createOrder")
    @ResponseBody
    public String createOrder(Integer flux){
        String userId = SecurityUtils.get().getId();
        String orderId = chargeService.createPaypalOrder(userId,flux);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderId);
        return success(map);
    }


    /**
     * paypal支付回调
     * @param paymentId
     * @param orderId
     * @return
     */
    @RequestMapping("/paypalCallback")
    @ResponseBody
    public String paypalResult(String paymentId,String orderId){

        String str = SignatureUtil.getPaymentDetails(paymentId);
        JSONObject detail = JSONObject.parseObject(str);
        //校验订单是否完成
        if("approved".equals(detail.getString("state"))){
            JSONObject transactions = detail.getJSONArray("transactions").getJSONObject(0);
            JSONObject amount = transactions.getJSONObject("amount");
            //校验交易货币类型
            String currency = "USD";
            if( !currency.equals(amount.getString("currency")) ){
                return error();
            }
            FluxOrder order = chargeService.selectByPrimaryKey(orderId);
            //没有相关订单
            if(order == null ){
                return error();
            }
            //更新订单状态，修改用户流量值
            chargeService.updateOrderAndUserFlux(order);
            return success();
        }
        return error();
    }

}







