package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
 import cn.medcn.common.utils.CheckUtils;
 import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.realm.ThirdPartyToken;
import cn.medcn.oauth.OAuthConstants;
import cn.medcn.oauth.decorator.OAuthServiceDecorator;
import cn.medcn.oauth.decorator.WeChatServiceDecorator;
import cn.medcn.oauth.decorator.WeiBoServiceDecorator;
import cn.medcn.oauth.decorator.YaYaServiceDecorator;
import cn.medcn.oauth.dto.OAuthUser;
import cn.medcn.oauth.provider.OAuthDecoratorProvider;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        } else if (thirdPartyId > BindInfo.Type.YaYa.getTypeId() ){
            return localeView("/login/login_"+thirdPartyId);
        } else {
            // 第三方登录
            return "redirect:" + jumpThirdPartyAuthorizePage(thirdPartyId);
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
        return "" ;
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

    /**
     * 跳转第三方授权登录页面
     * @param thirdPartyId
     * @return
     */
    protected String jumpThirdPartyAuthorizePage(Integer thirdPartyId) {
        OAuthServiceDecorator decorator = OAuthDecoratorProvider.getDecorator(thirdPartyId,
                OAuthConstants.get("WeiBo.oauth.callback"));

        String authorizeUrl = decorator.getAuthorizeUrl();

        return authorizeUrl;
    }

    /**
     * 第三方登录 回调地址
     * @param code
     * @param thirdPartyId
     * @return
     */
    @RequestMapping(value = "/oauth/callback")
    public String callback(String code, Integer thirdPartyId) {
        if (StringUtils.isNotEmpty(code)) {
            Token accessToken = null;
            OAuthServiceDecorator decorator = OAuthDecoratorProvider.getDecorator(thirdPartyId, "");
            accessToken = decorator.getAccessToken(accessToken, new Verifier(code));
            OAuthUser oAuthUser = decorator.getOAuthUser(accessToken);

            CspUserInfo userInfo = null;
            if (oAuthUser != null) {
                String uniqueId = oAuthUser.getUid();

                if (StringUtils.isNotEmpty(uniqueId)) {
                    // 根据第三方用户唯一id 查询用户是否存在
                    userInfo = cspUserService.findBindUserByUniqueId(uniqueId);

                   // 用户不存在，去添加绑定账号及添加csp用户账号
                    if (userInfo == null) {
                        CspUserInfoDTO dto = OAuthUser.buildToCspUserInfoDTO(oAuthUser);
                        dto.setToken(accessToken.getToken());
                        dto.setThirdPartyId(thirdPartyId);
                        //去添加绑定账号
                        userInfo = cspUserService.saveThirdPartyUserInfo(dto);
                    }
                }

                ThirdPartyToken token = new ThirdPartyToken(userInfo.getId());
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
            }

        }

        return "redirect:/mgr/meet/list";
    }
}
