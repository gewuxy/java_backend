package cn.medcn.csp.controller.api;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.*;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.security.Principal;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/9/27.
 */
@Controller
@RequestMapping(value = "/api/user")
public class CspUserController extends BaseController{
    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Autowired
    protected AppUserService appUserService;


    /**
     * 注册csp账号
     * @param userInfo
     */
    @RequestMapping("/register")
    @ResponseBody
    public void register(CspUserInfo userInfo) {
        if (userInfo == null) {
            error(local("error.param"));
        } else {
            try {
                cspUserService.register(userInfo);
            } catch (Exception e) {
                new SystemException("user info can not be null");
            }
            success();
        }
    }

    /**
     * 邮箱+密码、手机+验证码登录 、第三方账号登录
     * type 1=微信 2=微博 3=Facebook 4=Twitter 5=YaYa医师 6=手机 7=邮箱
     * 登录检查用户是否存在csp账号，如果存在，登录成功返回用户信息；
     * 反之，根据客户端传过来的第三方信息，保存到数据库，再返回登录成功及用户信息
     * @param email 邮箱
     * @param password 密码
     * @param thirdPartyId 第三方平台id
     * @param mobile   手机
     * @param captcha  验证码
     * @param nickName 昵称
     * @param userInfoDTO 第三方用户信息
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public void login(String email, String password, Integer thirdPartyId,
                        String mobile, String captcha, String nickName,
                        CspUserInfoDTO userInfoDTO, HttpServletRequest request) {

        if (thirdPartyId == null || thirdPartyId == 0) {
           error(local("user.empty.ThirdPartyId"));
        }
        // 第三方平台id
        int type = thirdPartyId.intValue();

        CspUserInfo userInfo = null;

        if (type == BindInfo.Type.EMAIL.getTypeId()) {
            // 邮箱登录
            userInfo = loginByEmail(email, password);

        } else if (type == BindInfo.Type.MOBILE.getTypeId()) {
            // 手机登录
           userInfo = loginByMobile(mobile, captcha, nickName);

        } else if (type <= BindInfo.Type.YaYa.getTypeId()) {
            if (userInfoDTO != null) {
                userInfoDTO.setThirdPartyId(type);
            }
            // 第三方账号登录 含YaYa医师登录
            userInfo = loginByThirdParty(userInfoDTO);
        }

        // 登录成功，返回用户信息
        loginSuccess(userInfo, userInfo.getToken(), request);
    }

    /**
     * 邮箱登录
     * @param email
     * @param password
     */
    protected CspUserInfo loginByEmail(String email, String password) {
        if (StringUtils.isEmpty(email)) {
            error(local("user.empty.email"));
        }
        if (StringUtils.isEmpty(password)) {
            error(local("user.empty.password"));
        }

        CspUserInfo userInfo = cspUserService.findByLoginName(email);
        if (userInfo == null) {
            error(local("user.error.nonentity"));
        }
        // 用户输入密码是否正确
        if (!MD5Utils.md5(password).equals(userInfo.getPassword())) {
            error(local("user.error.password"));
        }

        return userInfo;
    }


    /**
     * 发送手机验证码
     * type 发送短信验证码模板内容区分 0=登录 1=绑定
     */
    @RequestMapping("/sendCaptcha")
    @ResponseBody
    public void sendCaptcha(String mobile, Integer type) {
        try {
            cspUserService.sendCaptcha(mobile, type);
            success();
        } catch (Exception e) {
            error(local(e.getMessage()));
        }
    }


    /**
     * 手机号码 + 验证码登录
     * @param mobile
     * @param captcha
     */
    protected CspUserInfo loginByMobile(String mobile, String captcha, String nickName) {
       if (StringUtils.isEmpty(mobile)) {
           error(local("user.empty.mobile"));
       }
       if (StringUtils.isEmpty(captcha)) {
           error(local("user.empty.captcha"));
       }

        // 检查验证码是否有效
        cspUserService.checkCaptchaIsOrNotValid(mobile, captcha);

        // 根据手机号码检查用户是否存在
        CspUserInfo userInfo = cspUserService.findByLoginName(mobile);
        if (userInfo == null) {
            error(local("user.error.nonentity"));
        }

        userInfo.setNickName(nickName);

       return userInfo;

    }


    /**
     * 第三方登录 根据uniqueId检查是否有注册过csp账号，如果注册过，登录成功返回用户信息；
     * 反之，根据客户端传过来的第三方信息，保存到数据库，再登录返回用户信息
     * type 1代表微信,2代表微博,3代表facebook,4代表twitter 5代表YaYa
     */
    protected CspUserInfo loginByThirdParty(CspUserInfoDTO userDTO) {
        if (userDTO == null) {
            error(local("error.param"));
        }

        String uniqueId = userDTO.getUniqueId();
        // 检查用户是否存在
        CspUserInfo userInfo = cspUserService.findBindUserByUniqueId(uniqueId);

        // 用户不存在,则获取第三方用户信息 保存至CSP用户表及绑定用户表
        if (userInfo == null) {
            userInfo = cspUserService.saveThirdPartyUserInfo(userDTO);
        }

        return userInfo;
    }

    /**
     * 登录成功 返回用户信息
     * @param userInfo
     */
    protected void loginSuccess(CspUserInfo userInfo, String token, HttpServletRequest request) {
        // 缓存用户信息
        cachePrincipal(userInfo, token);
        // 更新用户登录时间及ip
        userInfo.setLastLoginTime(new Date());
        userInfo.setLastLoginIp(request.getRemoteAddr());
        cspUserService.updateByPrimaryKey(userInfo);
        success(userInfo);
    }

    /**
     * 缓存用户信息
     * @param user
     * @param token
     * @return
     */
    protected Principal cachePrincipal(CspUserInfo user, String token) {
        Principal principal = createPrincipal(user, token);
        redisCacheUtils.setCacheObject(CspConstants.TOKEN_KEY + "_" + token, principal, Constants.TOKEN_EXPIRE_TIME);
        return principal;
    }

    private Principal createPrincipal(CspUserInfo user, String token) {
        Principal principal = new Principal();
        principal.setToken(token);
        principal.setId(user.getId());
        principal.setEmail(user.getEmail());
        principal.setMobile(user.getMobile());
        principal.setNickName(user.getNickName());
        principal.setAvatar(user.getAvatar());
        return principal;
    }




}
