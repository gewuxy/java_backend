package cn.medcn.csp.controller.web;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.*;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.Principal;
import cn.medcn.oauth.dto.OAuthUser;
import cn.medcn.oauth.service.OauthService;
import cn.medcn.sys.service.SysNotifyService;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static cn.medcn.common.Constants.*;

/**
 * Created by lixuan on 2017/10/16.
 */
@Controller
@RequestMapping(value = "/mgr")
public class LoginController extends CspBaseController {

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected OauthService oauthService;

    @Autowired
    protected SysNotifyService sysNotifyService;

    @Autowired
    protected EmailTempService tempService;

    protected static final int COOKIE_MAX_AGE = (int)TimeUnit.DAYS.toSeconds(LOGIN_COOKIE_MAX_AGE);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Integer thirdPartyId){

        if (thirdPartyId == null || thirdPartyId == 0) {
            // 登录主界面
            return localeView("/login/login");

        } else if (thirdPartyId > BindInfo.Type.YaYa.getTypeId() ){
            // 第三方id=6或7（手机或邮箱登录）
            return localeView("/login/login_"+thirdPartyId);

        } else {
            // 第三方登录
            String url = oauthService.jumpThirdPartyAuthorizePage(thirdPartyId);
            return "redirect:" + url;
        }

    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, Integer thirdPartyId,
                        String mobile, String captcha, Model model,
                        HttpServletResponse response, HttpServletRequest request){

        // 默认为邮箱密码登录
        if (thirdPartyId == null) {
            thirdPartyId = BindInfo.Type.EMAIL.getTypeId();
        }

        if (thirdPartyId == BindInfo.Type.EMAIL.getTypeId()) {
            // 邮箱登录
            return loginByEmail(username, password, model, response);

        } else if (thirdPartyId == BindInfo.Type.MOBILE.getTypeId()) {
            // 手机登录
            return loginByMobile(mobile, captcha, model, response, request);

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
    protected String loginByEmail(String username, String password,
                                  Model model, HttpServletResponse response){
        String errorForwardUrl = localeView("/login/login_" + BindInfo.Type.EMAIL.getTypeId());
        if (CheckUtils.isEmpty(username)) {
            model.addAttribute("error", local("user.empty.username"));
            model.addAttribute("email", username);
            return errorForwardUrl;
        }

        if (CheckUtils.isEmpty(password)) {
            model.addAttribute("error", local("user.empty.password"));
            model.addAttribute("email", username);
            return errorForwardUrl;
        }

        // 检查用户是否 是海外用户
        CspUserInfo user = cspUserService.findByLoginName(username);
        if ((user != null && user.getAbroad())
                && LocalUtils.getLocalStr().equals(DEFAULT_LOCAL)) {
            // 国外账号 国内登录
            model.addAttribute("error", local("en.user.web.login.error"));
            model.addAttribute("email", username);
            return errorForwardUrl;
        }
        if ((user != null && !user.getAbroad())
                && !LocalUtils.getLocalStr().equals(DEFAULT_LOCAL)) {
            // 国内账号 国外登录
            model.addAttribute("error", local("cn.user.web.login.error"));
            model.addAttribute("email", username);
            return errorForwardUrl;

        }

        return emailLogin(username, password, errorForwardUrl, model, response);

    }

    private String emailLogin(String username, String password, String errorForwardUrl, Model model, HttpServletResponse response) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);

            // 将当前用户添加到cookie缓存 保存7天
            Principal principal = getWebPrincipal();
            CookieUtils.setCookie(response, LOGIN_USER_ID_KEY, principal.getId() , COOKIE_MAX_AGE);
            CookieUtils.setCookie(response, LOGIN_USER_KEY, principal.getEmail() , COOKIE_MAX_AGE);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("email", username);
            return errorForwardUrl;
        }

        return "redirect:/mgr/meet/list";
    }


    /**
     * 根据手机号码 检查用户是否存在，如果不存在 需注册新用户
     * @param mobile
     */
    public void checkUserByMobile(String mobile) {
        // 根据手机号码检查用户是否存在
        CspUserInfo userInfo = cspUserService.findByLoginName(mobile);
        if (userInfo == null) {
            // 手机号码不存在，注册新用户
            userInfo = new CspUserInfo();
            userInfo.setId(StringUtils.nowStr());
            userInfo.setMobile(mobile);
            userInfo.setRegisterTime(new Date());
            userInfo.setActive(true);
            userInfo.setAbroad(LocalUtils.isAbroad());
            userInfo.setFlux(0); // 用户流量
            cspUserService.insert(userInfo);
        }
    }

    /**
     * 手机登录
     * @param mobile
     * @param captcha
     * @param model
     * @return
     */
    protected String loginByMobile(String mobile, String captcha, Model model, HttpServletResponse response, HttpServletRequest request )   {
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
            // 检查手机号码是否注册
            checkUserByMobile(mobile);

            // 先登录成功之后再检查验证码 是否有效（避免如果登录出现异常时 浪费短信验证码）
            UsernamePasswordToken token = new UsernamePasswordToken(mobile, "");
            token.setHost("mobile");
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            // 检查验证码是否有效
            checkCaptchaIsOrNotValid(mobile, captcha);

            Principal principal = getWebPrincipal();
            // 如果该用户没有昵称 需设置昵称才能登录
            if (principal != null && principal.getNickName() == null) {
                model.addAttribute("id", principal.getId());
                return localeView("/login/to_set_nickname");
            }

            // 如果用户有昵称 直接登录 缓存登录用户账号
            CookieUtils.setCookie(response, LOGIN_USER_ID_KEY, principal.getId() , COOKIE_MAX_AGE);
            CookieUtils.setCookie(response, LOGIN_USER_KEY, principal.getMobile() , COOKIE_MAX_AGE);

        } catch (AuthenticationException e) {

            model.addAttribute("error", e.getMessage());
            model.addAttribute("mobile", mobile);
            return errorForwardUrl;

        } catch (SystemException ex) {
           // 登录异常，需退出登录
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            // 清除缓存
            CookieUtils.clearCookie(response, LOGIN_USER_ID_KEY);
            CookieUtils.clearCookie(response, LOGIN_USER_KEY);

            model.addAttribute("error", ex.getMessage());
            model.addAttribute("mobile", mobile);
            return errorForwardUrl;
        }
        return "redirect:/mgr/meet/list";
    }


    /**
     * 手机登录 如果没有昵称，要先设置昵称 才能登录
     * @param id
     * @param nickName
     * @param model
     * @return
     */
    @RequestMapping(value = "/login/addNickName", method = RequestMethod.POST)
    public String loginSetNickName(String id, String nickName, Model model, HttpServletResponse response) {
        if (StringUtils.isEmpty(nickName)) {
            model.addAttribute("error", local("user.empty.mobile"));
            model.addAttribute("nickName", nickName);
            return localeView("/login/to_set_nickname");
        }
        // 设置用户昵称
        CspUserInfo userInfo = cspUserService.selectByPrimaryKey(id);
        userInfo.setNickName(nickName);
        cspUserService.updateByPrimaryKeySelective(userInfo);

        // 将当前用户添加到cookie缓存 保存7天
        Principal principal = getWebPrincipal();
        CookieUtils.setCookie(response, LOGIN_USER_ID_KEY, principal.getId() , COOKIE_MAX_AGE);
        CookieUtils.setCookie(response, LOGIN_USER_KEY, principal.getMobile() , COOKIE_MAX_AGE);

        return "redirect:/mgr/meet/list";
    }



    /**
     * 第三方登录,绑定 回调地址
     * @param code
     * @param thirdPartyId
     * @return
     */
    @RequestMapping(value = "/oauth/callback")
    public String callback(String code, Integer thirdPartyId, RedirectAttributes redirectAttributes,
                           HttpServletResponse response, Model model) throws SystemException {

        Principal principal =  getWebPrincipal();

        if (StringUtils.isNotEmpty(code)) {
            //获取第三方用户信息
            OAuthUser oAuthUser = oauthService.getOauthUser(code, thirdPartyId);

            if (oAuthUser != null) {
                String uniqueId = oAuthUser.getUid();
                if (StringUtils.isNotEmpty(uniqueId)) {

                    // 根据第三方用户唯一id 查询用户是否存在
                    CspUserInfo userInfo = cspUserService.findBindUserByUniqueId(uniqueId);

                    if(principal != null){
                        //第三方绑定操作
                        doThirdPartWebBind(oAuthUser,thirdPartyId,redirectAttributes);
                        return "redirect:/mgr/user/toAccount";
                    }else{
                        //第三方登录操作
                        return doThirdPartWebLogin(thirdPartyId, oAuthUser, userInfo, response, model);
                    }
                }
            }
        }

        return "";

    }

    /**
     * 推特/facebook 回调
     * @param user
     * @return
     */
    @RequestMapping(value="/jsCallback",method = RequestMethod.POST)
    public String twitterCallback(OAuthUser user, HttpServletRequest request, HttpServletResponse response,
                                  RedirectAttributes redirectAttributes, Model model) throws SystemException {
        if(user == null){
            throw new SystemException("can't get userInfo");
        }

        Principal principal =  getWebPrincipal();

        //登录回调
        if(principal == null){
            // 根据第三方用户唯一id 查询用户是否存在
            CspUserInfo userInfo = cspUserService.findBindUserByUniqueId(user.getUid());
            return doThirdPartWebLogin(user.getPlatformId(), user, userInfo, response, model);

        }else{  //绑定回调

            //将OauthUser转为BindInfo对象
            BindInfo info = new BindInfo();
            info.setThirdPartyId(user.getPlatformId());
            info.setUniqueId(user.getUid());
            info.setId(StringUtils.nowStr());
            info.setBindDate(new Date());
            info.setAvatar(user.getIconUrl());
            info.setNickName(user.getNickname());
            try {
                cspUserService.doBindThirdAccount(info,principal.getId());
            } catch (SystemException e) {
                addFlashMessage(redirectAttributes,e.getMessage());
                return "redirect:/mgr/user/toAccount";
            }

            return "redirect:/mgr/user/toAccount";
        }
    }

    /**
     * 绑定第三方账号
     * @param oAuthUser
     * @param thirdPartId
     * @param redirectAttributes
     * @return
     */
    private void doThirdPartWebBind(OAuthUser oAuthUser,Integer thirdPartId,RedirectAttributes redirectAttributes) {
            String userId = getWebPrincipal().getId();
            BindInfo info = OAuthUser.buildToBindInfo(oAuthUser);
            info.setThirdPartyId(thirdPartId);
            info.setId(StringUtils.nowStr());

        try {
            cspUserService.doBindThirdAccount(info,userId);
            addFlashMessage(redirectAttributes,local("user.bind.success"));
        } catch (SystemException e) {
           addFlashMessage(redirectAttributes,e.getMessage());
        }

    }


    /**
     * 第三方登录需要做的操作
     * @param thirdPartyId
     * @param oAuthUser
     * @param userInfo
     */
    private String doThirdPartWebLogin(Integer thirdPartyId, OAuthUser oAuthUser, CspUserInfo userInfo,
                                     HttpServletResponse response, Model model) {
        // 用户不存在，去添加绑定账号及添加csp用户账号
        if (userInfo == null) {
            CspUserInfoDTO dto = OAuthUser.buildToCspUserInfoDTO(oAuthUser);
            dto.setThirdPartyId(thirdPartyId);
            // 获取当前语言
            if (!LocalUtils.getLocalStr().equals(DEFAULT_LOCAL)) { // 海外
                dto.setAbroad(true);
            }
            //去添加绑定账号
            userInfo = cspUserService.saveThirdPartyUserInfo(dto);
        }

        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setHost("thirdParty");
        token.setUsername(userInfo.getId());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);

            // 将当前用户添加到cookie缓存 保存7天
            Principal principal = getWebPrincipal();
            CookieUtils.setCookie(response, LOGIN_USER_ID_KEY, principal.getId() , COOKIE_MAX_AGE);
            String nickName = URLEncoder.encode(principal.getNickName(),"UTF-8");
            CookieUtils.setCookie(response, LOGIN_USER_KEY, nickName , COOKIE_MAX_AGE);

        } catch (AuthenticationException e) {
            model.addAttribute("error", e.getMessage());
            return localeView("/login/login");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "redirect:/mgr/meet/list";
    }


    /**
     * 跳转注册页面
     * @return
     */
    @RequestMapping(value = "/to/register")
    public String toRegister() {
        return localeView("/register/register");
    }

    /**
     * 邮箱注册
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public String register(CspUserInfo userInfo) {
        EmailTemplate template = tempService.getTemplate(LocalUtils.getLocalStr(),EmailTemplate.Type.REGISTER.getLabelId(),EmailTemplate.UseType.CSP.getLabelId());
       try {
            return cspUserService.register(userInfo,template);
        } catch (SystemException e) {
            return error(local(e.getMessage()));
        }
    }

    /**
     * 忘记密码
     * @return
     */
    @RequestMapping(value = "/to/reset/password")
    public String toResetPassWd() {
        return localeView("/register/to_reset_passwd");
    }


    /**
     * 退出登录 需清除cookie缓存的账号
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // 清除缓存中的用户账号和id
//        String id = CookieUtils.getCookieValue(request, LOGIN_USER_ID_KEY);
//        if (StringUtils.isNotEmpty(id)) {
//            CookieUtils.setCookie(response, LOGIN_USER_ID_KEY, "", 0);
//        }
//        String userName = CookieUtils.getCookieValue(request, LOGIN_USER_KEY);
//        if (StringUtils.isNotEmpty(userName)) {
//            CookieUtils.setCookie(response, LOGIN_USER_KEY, "", 0);
//        }

        // 清除缓存
        CookieUtils.clearCookie(response, LOGIN_USER_ID_KEY);
        CookieUtils.clearCookie(response, LOGIN_USER_KEY);
        return "redirect:/mgr/logout";
    }

}