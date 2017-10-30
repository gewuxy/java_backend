package cn.medcn.csp.controller.api;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.email.EmailHelper;
import cn.medcn.common.email.MailBean;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.RegexUtils;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 通过邮箱重置密码，绑定邮箱账号
 * Created by LiuLP on 2017/7/27/027.
 */
@Controller
@RequestMapping("/api/email")
public class EmailController extends BaseController{


    @Autowired
    private RedisCacheUtils redisCacheUtils;


    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected JPushService jPushService;

    @Value("${app.yaya.base}")
    private String appBaseUrl;


    /**
     * 点击验证邮箱 激活链接
     * @param code
     * @return
     * @throws SystemException
     */
   @RequestMapping("/active")
    public String certifiedMail(String code, Model model) throws SystemException {
        String key = Constants.EMAIL_LINK_PREFIX_KEY + code;
        String email = (String)redisCacheUtils.getCacheObject(key);
        if (StringUtils.isEmpty(email)) {  //链接超时
            return localeView("/register/linkTimeOut");
        } else {
            CspUserInfo userInfo = cspUserService.findByLoginName(email);
            if (userInfo != null) {
                userInfo.setActive(true);
                cspUserService.updateByPrimaryKey(userInfo);
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
        }

        try {
            cspUserService.sendMail(email,null, MailBean.MailTemplate.FIND_PWD.getLabelId());
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
    public String bindEmail(String code) throws SystemException {
        String key = Constants.EMAIL_LINK_PREFIX_KEY + code;
        String result = (String)redisCacheUtils.getCacheObject(key);
        if(!StringUtils.isEmpty(result)){
            String email = result.substring(0, result.indexOf(","));
            String userId = result.substring(result.indexOf(",") + 1);
            cspUserService.doBindMail(email,userId,key);
            //发送推送通知邮箱已绑定
            jPushService.sendChangeMessage(userId,"3",email);
            return localeView("/register/bindOk");
        }else{ //链接超时
            return localeView("/register/linkTimeOut");
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
