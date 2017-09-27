package cn.medcn.csp.controller.api;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.csp.utils.SignatureUtil;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.service.ChargeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
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
import java.util.Locale;

/**
 * Created by lixuan on 2017/9/12.
 */
@Controller
@RequestMapping("/api/charge/")
public class ChargeController extends BaseController {


    @Autowired
    protected ChargeService chargeService;


    @Value("${private.key}")
    protected String privateKey;

    @Value("${ping.public.key}")
    protected String publicKeyString;

    /**
     * 购买流量，需要传递amount(金额),channel(支付渠道)
     */
    @RequestMapping("/toCharge")
    @ResponseBody
    public String toCharge(Integer amount,String channel,HttpServletRequest request)  {
        Pingpp.apiKey = "sk_test_nz1yT0O8mjT4yf1WjPvbrDm1";
        String appId = "app_LiH0mPanX9OGDS04";
        String orderNo = StringUtils.nowStr();
        String ip = request.getRemoteAddr();
        ip = "10.0.0.234";
//        Pingpp.privateKey = privateKey;
        Charge charge = null;

        try {
            //生成Charge对象
            charge = chargeService.createCharge(orderNo,appId, amount, channel, ip);
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        //创建订单
        chargeService.createOrder(SecurityUtils.get().getId(),orderNo,amount,channel);
            return success(charge.toString());

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
        PublicKey publicKey= null;
        try {
            publicKey = SignatureUtil.getPubKey(publicKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //验证签名
        Boolean isTrue = null;
        try {
            isTrue = SignatureUtil.verifyData(body,signature,publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(isTrue){
            Event event = Webhooks.eventParse(body);
            //支付成功事件
            if ("charge.succeeded".equals(event.getType())) {
                JSONObject object = JSON.parseObject(event.getObject());
                String orderNo = (String)object.get("order_no");
                //查找订单
                FluxOrder condition = new FluxOrder();
                condition.setTradeId(orderNo);
                FluxOrder result = chargeService.selectOne(condition);
                if(result != null){
                    //更新订单状态，修改用户流量值
                    chargeService.updateOrderAndUserFlux(result);
                    response.setStatus(200);
                }else{
                    //没有找到订单
                    response.setStatus(500);
                }
            }
        }else{
            //签名错误
            response.setStatus(500);
        }
    }


}







