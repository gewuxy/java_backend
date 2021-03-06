package cn.medcn.csp.controller.api;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.email.MailBean;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.service.PushService;
import cn.medcn.common.utils.*;
import cn.medcn.sys.service.SysNotifyService;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/** 通过邮箱重置密码，绑定邮箱账号
 * Created by LiuLP on 2017/7/27/027.
 */
@Controller
@RequestMapping("/api/email")
public class EmailController extends BaseController{


    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    protected CspUserPackageService cspUserPackageService;
    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected PushService cspPushService;

    @Autowired
    protected EmailTempService tempService;

    @Autowired
    protected SysNotifyService sysNotifyService;

    @Value("${app.yaya.base}")
    private String appBaseUrl;


    /**
     * 点击验证邮箱 激活链接
     * @param code
     * @return
     * @throws SystemException
     */
   @RequestMapping("/active")
    public String certifiedMail(String code,String language, Model model) throws SystemException {
        String key = Constants.EMAIL_LINK_PREFIX_KEY + code;
        String email = (String)redisCacheUtils.getCacheObject(key);
        if (StringUtils.isEmpty(email)) {  //链接超时
            return localeView("/register/activeFail");
        } else {
            CspUserInfo userInfo = cspUserService.findByLoginName(email);
            if (userInfo != null) {
                userInfo.setActive(true);
                cspUserService.updateByPrimaryKey(userInfo);
                redisCacheUtils.delete(key);
                //发送推送消息
                LocalUtils.set(LocalUtils.getByKey(language));
                sysNotifyService.addNotify(userInfo.getId(),local("user.notify.title"),local("user.notify.content"),local("user.notify.sender"));
                if(userInfo.getRegisterDevice() == CspUserInfo.RegisterDevice.APP.ordinal()){
                    cspUserPackageService.addStanardInfo(userInfo.getId());
                }
            }
            model.addAttribute("email", email);
            return localeView("/register/activeOk");
        }

    }


    /**
     * 发送找回密码邮件
     * @param email
     * @return
     */
    @RequestMapping("/findPwd")
    @ResponseBody
    public String findPwd(String email){
        if(!RegexUtils.checkEmail(email)){
            return error(local("user.email.format"));
        }
        CspUserInfo info = cspUserService.findByLoginName(email);
        if(info == null){
            return error(local("user.notexisted"));
        } else {
            // 检查用户如果已经被冻结 弹出被冻结提示，且不会发送找回密码邮件
            if (info.getFrozenState() != null && info.getFrozenState()) {
                return error(APIUtils.ERROR_CODE_FROZEN, local("user.frozen.account"));
            }
        }

        //获取邮件模板对象
        EmailTemplate template = tempService.getTemplate(LocalUtils.getLocalStr(),EmailTemplate.Type.FIND_PWD.getLabelId(),EmailTemplate.UseType.CSP.getLabelId());
        try {
            cspUserService.sendMail(email,null, template);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();

    }

    /**
     * 点击绑定邮箱激活链接
     * @param code
     * @return
     * @throws SystemException
     */
    @RequestMapping("/bindEmail")
    public String bindEmail(String code,Model model) throws SystemException {
        String key = Constants.EMAIL_LINK_PREFIX_KEY + code;
        String result = (String)redisCacheUtils.getCacheObject(key);
        if(!StringUtils.isEmpty(result)){
            String email = result.substring(0, result.indexOf(","));
            String userId = result.substring(result.indexOf(",") + 1);
            cspUserService.doBindMail(email,userId,key);
            //发送推送通知邮箱已绑定
            cspPushService.sendChangeMessage(userId,"3",email);
            model.addAttribute("user",email);
            return localeView("/userCenter/bindOk");
        }else{ //链接超时
            return localeView("/userCenter/linkTimeOut");
        }


    }


    /**
     * 点击找回密码激活链接时
     * @param code
     * @param model
     * @return  跳转到重置密码页面
     */
    @RequestMapping("/toReset")
    public String resetPwd(String code,Model model){
        String key = Constants.EMAIL_LINK_PREFIX_KEY + code;
        String result = (String) redisCacheUtils.getCacheObject(key);
        if(StringUtils.isEmpty(result)) {
            return localeView("/register/linkTimeOut");
        }
        model.addAttribute("code", code);
        return localeView("/register/pwdReset");

    }


    /**
     * 确认重置密码
     * @param code
     * @param password
     * @param model
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/doReset", method = RequestMethod.POST)
    public String doReset(String code, String password, Model model) throws SystemException {
        if(StringUtils.isEmpty(password)){
            model.addAttribute("message", local("user.password.notnull"));
            return localeView("/register/pwdReset");
        }
        String resetPwdKey = Constants.EMAIL_LINK_PREFIX_KEY+code;
        String email = (String) redisCacheUtils.getCacheObject(resetPwdKey);
        if(StringUtils.isEmpty(email)){
            return localeView("/register/linkTimeOut");
        }
        CspUserInfo info = cspUserService.findByLoginName(email);
        if(info != null){
            info.setPassword(MD5Utils.MD5Encode(password));
            cspUserService.updateByPrimaryKey(info);
            redisCacheUtils.delete(resetPwdKey);
        }
        return localeView("/register/pwdOk");
    }
}
