package cn.medcn.official.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.sys.model.SystemAuthorize;
import cn.medcn.sys.service.SysAuthorizeService;
import cn.medcn.user.dto.OAuthUserDTO;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/9/25.
 */
@Controller
@RequestMapping(value = "/oauth")
public class AuthorizeController extends BaseController {

    protected static final String CODE_PREFIX = "auth_code_";

    protected static final String ACCESS_TOKEN_PREFIX = "access_token_";


    @Autowired
    protected SysAuthorizeService sysAuthorizeService;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Autowired
    protected AppUserService appUserService;

    @Value("${app.file.base}")
    protected String appFileBase;


    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public String authorize(Model model, String redirect_uri, String client_id) throws SystemException{
        if (CheckUtils.isEmpty(redirect_uri)) {
            model.addAttribute(messageKey, "illegal redirect_uri");
        }
        if (CheckUtils.isEmpty(client_id)) {
            model.addAttribute(messageKey, "client_id can not be empty");
        }

        SystemAuthorize condition = new SystemAuthorize();
        condition.setAppKey(client_id);
        SystemAuthorize authorize = sysAuthorizeService.selectOne(condition);
        if (authorize == null) {
            model.addAttribute(messageKey, "invalid client id");
        } else {
            model.addAttribute("app_name", authorize.getAppName());
        }

        model.addAttribute("redirect_uri", redirect_uri);
        //跳转到登录授权页
        return "/oauth/login";
    }

    /**
     * APP端授权认证
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/app/authorize", method = RequestMethod.POST)
    @ResponseBody
    public String authorize(String username, String password){
        if (CheckUtils.isEmpty(username) || CheckUtils.isEmpty(password)) {
            return error("用户名或密码不能为空");
        }

        AppUser appUser = appUserService.findAppUserByLoginName(username);
        if (appUser == null) {
            return error("用户不存在");
        }

        if (!MD5Utils.MD5Encode(password).equals(appUser.getPassword())){
            return error("密码错误");
        }

        if (appUser.getPubFlag() == null || !appUser.getPubFlag()) {
            return error("只支持单位号认证");
        }

//        Map<String, String> map = new HashMap<>();
//        map.put("code", createCode(String.valueOf(appUser.getId())));

        //TODO 不是OAUTH2的标准 直接反回了用户信息 可能存在安全隐患

        return success(OAuthUserDTO.build(appFileBase, appUser));
    }

    /**
     * 授权登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(String username, String password, String redirect_uri, Model model) {
        String loginPage = "/oauth/login";
        model.addAttribute("redirect_uri",redirect_uri);
        if (CheckUtils.isEmpty(username) || CheckUtils.isEmpty(password)) {
            model.addAttribute(messageKey, "Username or Password can not be null");
            return loginPage;
        }

        AppUser appUser = appUserService.findAppUserByLoginName(username);
        if (appUser == null) {
            model.addAttribute(messageKey, "User not found");
            return loginPage;
        }

        if (!MD5Utils.MD5Encode(password).equals(appUser.getPassword())){
            model.addAttribute(messageKey, "illegal password ");
            return loginPage;
        }


        StringBuffer buffer = new StringBuffer("redirect:");
        buffer.append(redirect_uri).append("&code=").append(createCode(String.valueOf(appUser.getId())));
        return buffer.toString();
    }


    @RequestMapping(value = "/access_token")
    @ResponseBody
    public String accessToken(String client_id, String client_secret, String code){
        if (CheckUtils.isEmpty(client_id) || CheckUtils.isEmpty(client_secret)) {
            return error("Id or secret can not be null");
        }

        SystemAuthorize cond = new SystemAuthorize();
        cond.setAppKey(client_id);

        SystemAuthorize config = sysAuthorizeService.selectOne(cond);
        if (config == null) {
            return error("illegal key");
        }

        if (!client_secret.equals(config.getAppSecret())) {
            return error("illegal secret");
        }

        String uid = (String) redisCacheUtils.getCacheObject(CODE_PREFIX + code);
        redisCacheUtils.delete(CODE_PREFIX + code);
        Map<String, String> map = new HashMap<>();
        map.put("access_token", createAccessToken(uid));
        map.put("uid", uid);
        return success(map);
    }

    @RequestMapping(value = "/userinfo")
    @ResponseBody
    public String userInfo(String access_token, String uid){
        if (CheckUtils.isEmpty((String) redisCacheUtils.getCacheObject(ACCESS_TOKEN_PREFIX + access_token))){
            return error("illegal access token");
        }
        uid = (String) redisCacheUtils.getCacheObject(ACCESS_TOKEN_PREFIX + access_token);
        AppUser appUser = appUserService.selectByPrimaryKey(Integer.valueOf(uid));
        if (appUser == null) {
            return error(String.format("User with id (%s) not found", uid));
        }

        return success(OAuthUserDTO.build(appFileBase, appUser));
    }


    protected String createCode(String uid){
        String code = UUIDUtil.getUUID();
        redisCacheUtils.setCacheObject(CODE_PREFIX + code, uid, (int) TimeUnit.MINUTES.toSeconds(5));
        return code;
    }

    protected String createAccessToken(String uid){
        String token = UUIDUtil.getUUID();
        redisCacheUtils.setCacheObject(ACCESS_TOKEN_PREFIX + token, uid, (int) TimeUnit.HOURS.toSeconds(2));
        return token;
    }
}
