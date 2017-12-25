package cn.medcn.csp.controller.web;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspPackageInfo;
import cn.medcn.user.service.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户套餐选购
 * by create HuangHuibin 2017/12/12
 */
@Controller
@RequestMapping("/mgr/pay")
public class PackageController extends CspBaseController {

    @Autowired
    protected CspPackageService cspPackageService;

    @Autowired
    protected CspPackageOrderService cspPackageOrderService;

    @Autowired
    protected CspUserPackageService cspUserPackageService;

    @Autowired
    protected CspUserPackageHistoryService cspUserPackageHistoryService;

    @Autowired
    protected CspPackageInfoService cspPackageInfoService;

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
     *
     * @param version
     * @param limitTimes
     * @param currency
     * @return
     */
    @RequestMapping(value = "/money")
    @ResponseBody
    public String getSumMoney(Integer version, Integer limitTimes, Integer currency) {
        Map<String, Object> map = cspPackageService.getOrderParams(version + 1, limitTimes, currency);
        return success(map.get("money"));
    }

    /**
     * 打开套餐选购页面
     *
     * @return
     */
    @RequestMapping(value = "/mark")
    public String mark() {
        return localeView("/include/member_mark");
    }

    /**
     * 获取套餐信息
     *
     * @return
     */
    @RequestMapping(value = "/package")
    @ResponseBody
    public String packageList() {
        List<CspPackage> packages = cspPackageService.findCspPackage();
        List<CspPackageInfo> infos = cspPackageInfoService.select(new CspPackageInfo());
        Map<String, Object> map = new HashMap<>();
        map.put("packages", packages);
        map.put("package", getWebPrincipal().getPackageId());
        map.put("infos", infos);
        return success(map);
    }

    /**
     * 更新用户套餐提示信息
     *
     * @return
     */
    @RequestMapping(value = "/update/msg")
    @ResponseBody
    public String updatePackageMsg() {
        String userId = getWebPrincipal().getId();
        updatePackageMsg(userId,null,Constants.NUMBER_ZERO);
        return success();
    }

    /**
     * 套餐支付
     *
     * @param packageId
     * @param currency
     * @param payType
     * @param limitTime
     * @return
     */
    @RequestMapping(value = "/pay")
    public String allPay(Integer packageId, Integer currency, String payType, Integer limitTime, Model model) throws SystemException {
        packageId++;
        //校验参数信息
        String validata = checkParams(packageId, currency, payType, limitTime);
        if (validata != null) {
            return validata;
        }
        Map<String, Object> results = cspPackageService.getOrderParams(packageId, limitTime, currency);
        Float money = (Float) results.get("money");
        money = appPro == 0 ? 0.01f : money;
        Integer num = (Integer) results.get("num");
        Integer packageType = (Integer) results.get("packageType");
        if (currency == 0) {  //人民币P++支付
            return rnbPay(packageId, currency, payType, money, num,packageType, model);
        } else {  //美元（目前只是paypal支付）
            return usdPay(packageId, currency, payType, money, num,packageType);
        }
    }


    /**
     * 新用户选择标准版
     *
     * @param packageId
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/standard")
    @ResponseBody
    public String standardEdition(Integer packageId){
        String userId = getWebPrincipal().getId();
        //标准版不需要支付添加用户套餐信息
        cspUserPackageService.addStanardInfo(userId);
        //添加用户历史版本信息
        cspUserPackageHistoryService.addUserHistoryInfo(userId, getWebPrincipal().getCspPackage() != null ? getWebPrincipal().getPackageId() : null, packageId, Constants.NUMBER_ONE);
        //更新用户信息缓存
        updatePackagePrincipal(userId);
        //添加提示
        updatePackageMsg(userId,packageId,Constants.NUMBER_TWO);
        return success();
    }

    /**
     * 人民币支付
     *
     * @param packageId
     * @param currency
     * @param payType
     * @param money
     * @param num
     * @return
     */
    public String rnbPay(Integer packageId, Integer currency, String payType, float money, Integer num,Integer packageType, Model model) {
        String path = this.getClass().getClassLoader().getResource("privateKey.pem").getPath();
        Pingpp.apiKey = apiKey;
        String orderNo = CspConstants.PACKAGE_ORDER_FLAG + packageId + currency + StringUtils.nowStr();
        String ip = "10.0.0.96";
        //String ip = request.getRemoteAddr();
        Pingpp.privateKeyPath = path;
        Charge charge = null;
        try {
            //生成Charge对象
            charge = chargeService.createCharge(orderNo, money, payType, ip, local("package.charge.buy"), appId);
        } catch (Exception e) {
            e.printStackTrace();
            return error(local("package.charge.fail"));
        }
        //创建订单
        cspPackageOrderService.createOrder(getWebPrincipal().getId(), orderNo, currency, packageId, num, money, payType,packageType);
        model.addAttribute("charge", charge.toString());
        //微信扫码支付
        if ("wx_pub_qr".equals(payType)) {
            return localeView("/userCenter/wxPay");
        }
        return localeView("/userCenter/newPage");
    }

    /**
     * 美元支付
     *
     * @param packageId
     * @param currency
     * @param payType
     * @param money
     * @param num
     * @return
     * @throws SystemException
     */
    public String usdPay(Integer packageId, Integer currency, String payType, float money, Integer num,Integer packageType) throws SystemException {
        //正式线mode为live，测试线mode为sandbox
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
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
        if (url != null) {
            //创建订单
            String orderNo = CspConstants.PACKAGE_ORDER_FLAG + packageId + currency + responsePayment.getId();
            //创建订单
            cspPackageOrderService.createOrder(getWebPrincipal().getId(), orderNo, currency, packageId, num, money, payType,packageType);
            return "redirect:" + url;
        }
        return error();
    }

    /**
     * 校验参数
     *
     * @param packageId
     * @param currency
     * @param payType
     * @param limitTime
     * @return
     */
    public String checkParams(Integer packageId, Integer currency, String payType, Integer limitTime) {
        if (packageId == null) {
            return error(local("error.param"));
        }
        if (currency == null) {
            return error(local("error.param"));
        }
        if (StringUtils.isEmpty(payType)) {
            return error(local("error.param"));
        }
        if (limitTime == null) {
            return error(local("error.param"));
        }
        return null;
    }

}
