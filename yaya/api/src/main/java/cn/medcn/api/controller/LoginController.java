package cn.medcn.api.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.medcn.api.dto.Principal;
import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.utils.*;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.sys.model.SystemProperties;
import cn.medcn.sys.service.SysPropertiesService;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.model.AppRole;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.AppUserDetail;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.dto.OAuthDTO;
import cn.medcn.weixin.model.WXUserInfo;
import cn.medcn.weixin.service.WXOauthService;
import cn.medcn.weixin.service.WXUserInfoService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by lixuan on 2017/4/19.
 */
@Controller
@RequestMapping(value = "/api")
public class LoginController extends BaseController{

    @Autowired
    private RedisCacheUtils<String> redisCacheUtils;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private JPushService jPushService;

    @Value("${app.file.base}")
    private String fileBase;

    @Value("${csp.secret}")
    protected String cspSecret;

    @Autowired
    private WXOauthService wxOauthService;

    @Autowired
    private WXUserInfoService wxUserInfoService;

    @Autowired
    private SysPropertiesService sysPropertiesService;


    @RequestMapping(value = "/cas", method = RequestMethod.GET)
    @ResponseBody
    public String userLoginCAS(String username, String password, String nonce, String time_stamp, String signature) {
        if (CheckUtils.isEmpty(signature)){
            return error("signature can not be null");
        }

        if (!signature.equals(MD5Utils.signature(cspSecret, nonce, time_stamp, MD5Utils.ENCRYPT_MODE_MD5))){
            return error("signature is illegal");
        }

        AppUser user = appUserService.findAppUserByLoginName(username);
        if (user == null) {
            return error(SpringUtils.getMessage("user.notexisted"));
        }
        if (!user.getPassword().equals(MD5Utils.MD5Encode(password))) {
            return error(SpringUtils.getMessage("user.password.error"));
        }
        if (user.getAuthed() == null || !user.getAuthed()) {
            return error(SpringUtils.getMessage("user.unauthed"));
        }

        return success(user);
    }


    /**
     * 如果用户已绑定微信，返回用户基本信息；如果没绑定，返回openid
     * @param code
     * @param request
     * @return
     */
    @RequestMapping("/check_wx_bind")
    @ResponseBody
    public String weChatLogin(String code,HttpServletRequest request) throws SystemException {
        if(StringUtils.isEmpty(code)){
            return error("code不能为空");
        }
        OAuthDTO oAuthDTO = wxOauthService.getOpenIdAndTokenByCode(code);
        if(oAuthDTO == null){
            return error("获取openid失败");
        }

        //判断unionid是否已绑定app
        String unionId = oAuthDTO.getUnionid();
        WXUserInfo wxUserInfo = wxUserInfoService.findWXUserInfo(unionId);
        if(wxUserInfo == null){  //未绑定
            //将oAuthDTO存入redis中
            redisCacheUtils.setCacheObject(oAuthDTO.getOpenid(),oAuthDTO, WeixinConfig.TOKEN_EXPIRE_TIME);
            Map<String,String> map = new HashedMap();
            map.put("openid",oAuthDTO.getOpenid());
            return success(map);
        }
        //已绑定，返回用户信息
        AppUser find = new AppUser();
        find.setUnionid(unionId);
        AppUser user = appUserService.selectOne(find);
        AppUserDTO dto = updateUserInfoAndGetDTO(user,request);
        return success(dto);
    }


    /**
     * 执行登录和绑定微信号功能
     * @param username
     * @param password
     * @param openid 当openid不为空时，执行绑定微信号功能
     * @param masterId 定制版需要传递的单位号ID
     * @param request
     * @return
     * @throws SystemException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(String username, String password, String openid, Integer masterId, HttpServletRequest request) throws SystemException, UnsupportedEncodingException {
        if (StringUtils.isEmpty(username)) {
            return error(SpringUtils.getMessage("user.username.notnull"));
        }
        if (StringUtils.isEmpty(password)) {
            return error(SpringUtils.getMessage("user.password.notnull"));
        }

        AppUser user = appUserService.findAppUserByLoginName(username);
        if (user == null) {
            return error(SpringUtils.getMessage("user.notexisted"));
        }
        if(user.getPubFlag() != null && user.getPubFlag() == true){
            return error("单位号不支持登录此系统");
        }
        if (!user.getPassword().equals(MD5Utils.MD5Encode(password))) {//-2代表密码错误，前端在此接口需要根据密码错误的次数弹出修改密码提示
            return APIUtils.error(APIUtils.ERROR_PASSWORD,SpringUtils.getMessage("user.password.error"));
        }
        if (user.getAuthed() == null || !user.getAuthed()) {
            return error(SpringUtils.getMessage("user.unauthed"));
        }

        user.setLastLoginTime(new Date());
        user.setLastLoginIp(request.getRemoteAddr());



        //绑定微信，检查该openid是否已被绑定，用户账号是否已绑定微信
        if(!StringUtils.isEmpty(openid)){
            if(!StringUtils.isEmpty(user.getUnionid())){ //账号已绑定微信，获取绑定的微信信息
                WXUserInfo info = wxUserInfoService.selectByPrimaryKey(user.getUnionid());
                return error("该YaYa账号已绑定微信账号:" + info.getNickname());
            }
            //检查该openid是否已被绑定
            OAuthDTO oAuthDTO = redisCacheUtils.getCacheObject(openid);
            if(oAuthDTO == null){
                return APIUtils.error("获取unionid失败");
            }
            redisCacheUtils.delete(openid);
            AppUser findUser = new AppUser();
            findUser.setUnionid(oAuthDTO.getUnionid());
            findUser = appUserService.selectOne(findUser);
            if(findUser != null){   //已绑定
                String name = findUser.getUsername();
                return APIUtils.error("该微信已绑定YaYa账号:"+ (StringUtils.isEmpty(name)? findUser.getMobile():name));
            }
            //unionid没有绑定，获取微信用户信息
            WXUserInfo wxUserInfo = null;
            try {
                wxUserInfo = wxOauthService.getUserInfoByOpenIdAndToken(openid,oAuthDTO.getAccess_token());
            } catch (SystemException e) {
                return error(e.getMessage());
            }
            user.setUnionid(oAuthDTO.getUnionid());
            user.setWxUserInfo(wxUserInfo);
        }
        //如果有masterId 则给用户增加指定的单位号的关注
        if (masterId != null && masterId != 0){
            appUserService.executeAttention(user.getId(), masterId);
        }
        AppUserDTO dto = updateUserInfoAndGetDTO(user,request);
        return success(dto);
    }



    /**
     * 更新用户信息并生成AppUserDTO
     * @param user
     * @param request
     * @return
     */
    private AppUserDTO updateUserInfoAndGetDTO(AppUser user,HttpServletRequest request){
        Principal principal = getLegalToken(user);
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(request.getRemoteAddr());
        //如果有微信用户信息，插入微信用户信息；否则只更新用户信息
        String wxNickname = appUserService.updateUserInfo(user);
        AppUserDetail userDetail = appUserService.findUserDetail(principal.getId(), AppRole.AppRoleType.DOCTOR.getId());
        user.setUserDetail(userDetail);
        AppUserDTO dto = AppUserDTO.buildFromDoctor(user);

        //设置医院级别属性
        dto = setHosLevelProperties(dto);

        dto.setWxNickname(wxNickname);
        Credits credits = creditsService.doFindMyCredits(user.getId());
        dto.setCredits(credits.getCredit());
        if(!StringUtils.isEmpty(dto.getHeadimg())){
            dto.setHeadimg(fileBase+dto.getHeadimg());
        }
        return dto;
    }


    @RequestMapping(value = "/bindJpush")
    @ResponseBody
    public String bindJpush(String registionId, HttpServletRequest request){
        String osType = request.getHeader(Constants.APP_OS_TYPE_KEY);
        Principal principal = SecurityUtils.getCurrentUserInfo();
        String alias = jPushService.generateAlias(principal.getId());
        Set<String> tags = Sets.newHashSet();
        try {
            if(!StringUtils.isEmpty(osType)){
                tags.add(osType);
            }
            jPushService.bindAliasAndTags(registionId, alias, tags);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            return APIUtils.error("极光推送连接异常");
        } catch (APIRequestException e) {
            e.printStackTrace();
            return APIUtils.error("极光推送请求异常");
        }
        return APIUtils.success();
    }

    //设置医院级别属性
    private AppUserDTO setHosLevelProperties(AppUserDTO dto){
        //获取等级图标
        String hosLevel = dto.getHosLevel();
        if(!StringUtils.isEmpty(hosLevel)) {
            SystemProperties properties = new SystemProperties();
            properties.setPropValue(hosLevel);
            properties = sysPropertiesService.selectOne(properties);
            if (properties != null) {
                if (!StringUtils.isEmpty(properties.getPicture())) {
                    properties.setPicture(fileBase + properties.getPicture());
                }
                dto.setSystemProperties(properties);
            }
        }
        return dto;

    }

    private Principal getLegalToken(AppUser user) {
        Principal principal = null;
        if (!StringUtils.isEmpty(user.getToken())) {
            //清除token 踢出之前的认证信息
            disablePrincipal(user.getToken());
        }
        String token = UUIDUtil.getUUID();
        user.setToken(token);
        principal = cachePrincipal(user, token);
        return principal;
    }



    private Principal cachePrincipal(AppUser user, String token) {
        Principal principal = createPrincipal(user, token);
        redisCacheUtils.setCacheObject(Constants.TOKEN + "_" + token, principal, Constants.TOKEN_EXPIRE_TIME);
        return principal;
    }


    private void disablePrincipal(String token) {
        redisCacheUtils.delete(Constants.TOKEN + "_" + token);
    }


    private Principal createPrincipal(AppUser user, String token) {
        Principal principal = new Principal();
        principal.setMobile(user.getMobile());
        principal.setUsername(user.getUsername());
        principal.setId(user.getId());
        principal.setHeadimg(user.getHeadimg());
        principal.setToken(token);
        principal.setNickname(user.getNickname());
        return principal;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN);
        String cacheKey = Constants.TOKEN+"_"+token;
        Principal principal = redisCacheUtils.getCacheObject(cacheKey);
        if (!StringUtils.isEmpty(token)) {
            disablePrincipal(token);
        }
        //处理极光别名
        try {
            if(principal != null){
                jPushService.cleanJpushByAlias(jPushService.generateAlias(principal.getUsername()));
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return success();
    }
}
