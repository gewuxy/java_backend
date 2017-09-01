package cn.medcn.jcms.controller.manage;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.user.model.RechargeOrder;
import cn.medcn.user.service.AppUserService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuLP on 2017/5/17/017.
 */
@Controller
@RequestMapping("/web/alipay")
public class WebAlipayController {

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

    @Value("${alipay.returnUrl}")
    String returnUrl;


    @RequestMapping(value = "/recharge",method = RequestMethod.POST)
    @ResponseBody
    public String recharge(Float totalAmount, HttpServletResponse response) throws JsonProcessingException, SystemException {
        String subject = "象数充值";
        String body = "网页充值象数";
        if(totalAmount <= 0){
            return APIUtils.error(SpringUtils.getMessage("alipay.price.error"));
        }
        AlipayClient alipayClient = new DefaultAlipayClient(gatWay,appId,privateKey, Constants.FORMAT,Constants.CHARSET,alipayPublicKey,Constants.SIGN_TYPE);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
        Map<String,String> map = new HashMap<>();
        String outTradeNo = UUIDUtil.getNowStringID();
        map.put("out_trade_no", outTradeNo);
        map.put("product_code",Constants.WEB_ALIPAY_PRODUCTCODE);
        map.put("total_amount",""+totalAmount);
        map.put("subject",subject);
        map.put("body",body);
        map.put("timeout_express",Constants.TIMEOUT);
        ObjectMapper mapper = new ObjectMapper();
        String bizString = mapper.writeValueAsString(map);
        alipayRequest.setBizContent(bizString);//填充业务参数
        String form = null;
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
           throw new SystemException("生成表单失败");
        }
        response.setContentType("text/html;charset=" + Constants.CHARSET);
        try {
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException("生成支付页面失败");
        }
        //生成订单
        RechargeOrder rechargeOrder = getRechargeOrder(totalAmount, outTradeNo);
        appUserService.executeRechargeOrder(rechargeOrder);
        return APIUtils.success();
    }


    /**
     * 支付宝同步返回地址
     * @return
     */
    @RequestMapping("/return")
    public String returnUrl(HttpServletRequest request, Model model){
        String amount = request.getParameter("total_amount").trim();
        model.addAttribute("amount",amount);
        return "/manage/recharge_success";
    }


    private RechargeOrder getRechargeOrder(Float totalAmount, String outTradeNo) {
        RechargeOrder rechargeOrder = new RechargeOrder();
        rechargeOrder.setUserId(SubjectUtils.getCurrentUserid());
        rechargeOrder.setCreateTime(new Date());
        rechargeOrder.setOrderNo(outTradeNo);
        rechargeOrder.setStatus(0);
        rechargeOrder.setPrice(totalAmount);
        return rechargeOrder;
    }


}
