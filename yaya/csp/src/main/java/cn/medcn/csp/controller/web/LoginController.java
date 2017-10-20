package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixuan on 2017/10/16.
 */
@Controller
@RequestMapping(value = "/mgr")
public class LoginController extends BaseController {

    @Autowired
    protected CspUserService cspUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Integer thirdPartyId){
        if (thirdPartyId == null || thirdPartyId == 0) {
            return localeView("/login/login");
        } else {
            return localeView("/login/login_"+thirdPartyId);
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, Integer thirdPartyId,
                        String mobile, String captcha, Model model){
        //默认为邮箱密码登录
        if (thirdPartyId == null) {
            thirdPartyId = BindInfo.Type.EMAIL.getTypeId();
        }

        if (thirdPartyId == BindInfo.Type.EMAIL.getTypeId()) {
            // 邮箱登录
            return loginByEmail(username, password, model);

        } else if (thirdPartyId == BindInfo.Type.MOBILE.getTypeId()) {
            // 手机登录
            return loginByMobile(mobile, captcha, model);
        }

        return "";
    }

    /**
     * 邮箱登录
     * @param username
     * @param password
     * @param model
     * @return
     */
    protected String loginByEmail(String username, String password, Model model){
        String errorForwardUrl = localeView("/login/login_" + BindInfo.Type.EMAIL.getTypeId());
        if (CheckUtils.isEmpty(username)) {
            model.addAttribute("error", local("user.empty.username"));
            model.addAttribute("username", username);
            return errorForwardUrl;
        }

        if (CheckUtils.isEmpty(password)) {
            model.addAttribute("error", local("user.empty.password"));
            model.addAttribute("username", username);
            return errorForwardUrl;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username);
            return errorForwardUrl;
        }
        return "redirect:/mgr/meet/list";
    }

    /**
     * 手机登录
     * @param mobile
     * @param captcha
     * @param model
     * @return
     */
    protected String loginByMobile(String mobile, String captcha, Model model)   {
        String errorForwardUrl = localeView("/login/login_" + BindInfo.Type.MOBILE.getTypeId());
        if (StringUtils.isEmpty(mobile)) {
            model.addAttribute("error", local("user.empty.mobile"));
            model.addAttribute("mobile", mobile);
            return errorForwardUrl;
        }

        if (StringUtils.isEmpty(captcha)) {
            model.addAttribute("error", local("user.empty.captcha"));
            model.addAttribute("captcha", captcha);
            return errorForwardUrl;
        }

        try {
            // 检查验证码是否有效
            cspUserService.checkCaptchaIsOrNotValid(mobile, captcha);

            UsernamePasswordToken token = new UsernamePasswordToken(mobile, "");
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("mobile", mobile);
            return errorForwardUrl;
        }
        return "redirect:/mgr/meet/list";
    }

    /**
     * 获取手机验证码
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sendCaptcha")
    @ResponseBody
    public String sendCaptcha(String mobile) {
        if(!StringUtils.isMobile(mobile)){
            return error(local("user.mobile.format"));
        }

        try {
            return cspUserService.sendCaptcha(mobile, Captcha.Type.LOGIN.getTypeId());

        } catch (SystemException e) {
            return error(e.getMessage());
        }
    }
}
