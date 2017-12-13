package cn.medcn.csp.controller.web;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.service.*;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

/**
 * by create HuangHuibin 2017/12/12
 */
@Controller
@RequestMapping("/mgr/pay")
public class PackageController extends CspBaseController{

    @Autowired
    protected CspPackageService cspPackageService;

    @Autowired
    protected CspPackageOrderService cspPackageOrderService;

    @Autowired
    protected CspUserPackageService cspUserPackageService;

    @Autowired
    protected CspUserPackageDetailService cspUserPackageDetailService;

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

    @Autowired
    protected ChargeService chargeService;

    /**
     * 获取金额
     * @param version
     * @param limitTimes
     * @param currency
     * @return
     */
    @RequestMapping(value = "/getMoney")
    @ResponseBody
    public String getSumMoney(Integer version,Integer limitTimes,String currency){
        Map<String,Object> map = cspPackageService.getOrderParams(version + 1,limitTimes,currency);
        return success(map.get("money"));
    }

    /**
     * 套餐支付
     * @param packageId
     * @param currency
     * @param payType
     * @param limitTime
     * @param model
     * @return
     */
    @RequestMapping(value="toPay")
    public String allPay(Integer packageId, String currency, String payType, Integer limitTime,Model model){
        String userId = getWebPrincipal().getId();
        packageId ++;
        if(packageId == CspPackage.TypeId.STANDARD.getId()){
            //标准版不需要支付添加用户套餐信息
            cspUserPackageService.addStanardInfo(userId);
            //添加用户历史版本信息
            cspUserPackageDetailService.addUserHistoryInfo(userId,null,packageId, Constants.NUMBER_ONE);
            return "redirect:/mgr/meet/list";
        }
        String validata = checkParams(packageId,currency,payType,limitTime);
        if(validata != null){
            return validata;
        }
        Map<String,Object> results = cspPackageService.getOrderParams(packageId,limitTime,currency);
        Float money = (Float) results.get("money");
        Integer num = (Integer) results.get("num");
        String path = this.getClass().getClassLoader().getResource("privateKey.pem").getPath();
        Pingpp.apiKey = apiKey;
        String orderNo = CspConstants.PACKAGE_ORDER_FLAG + StringUtils.nowStr();
        String ip = "10.0.0.96";
        //String ip = request.getRemoteAddr();
        Pingpp.privateKeyPath = path;
        Charge charge = null;
        try {
            //生成Charge对象
            charge = chargeService.createPackageCharge(orderNo,appId, money,num, payType, ip,appBase);
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
        cspPackageOrderService.createOrder(userId,orderNo,currency,packageId,num,money,payType);
        //添加一条通知信息
        model.addAttribute("charge",charge.toString());
        //微信扫码支付
        if("wx_pub_qr".equals(payType)){
            return localeView("/userCenter/wxPay");
        }
        return localeView("/userCenter/newPage");
    }

    /**
     * 校验参数
     * @param packageId
     * @param currency
     * @param payType
     * @param limitTime
     * @return
     */
    public String checkParams(Integer packageId,String currency,String payType,Integer limitTime){
        if(packageId == null){
            return error(local("error.param"));
        }
        if(StringUtils.isEmpty(currency)){
            return error(local("error.param"));
        }
        if(StringUtils.isEmpty(payType)){
            return error(local("error.param"));
        }
        if(limitTime == null){
            return error(local("error.param"));
        }
        return null;
    }

}
