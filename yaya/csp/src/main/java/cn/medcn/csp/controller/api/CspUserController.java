package cn.medcn.csp.controller.api;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.medcn.article.model.AppVideo;
import cn.medcn.article.service.CspAppVideoService;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.PasswordErrorException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.utils.*;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import com.google.common.collect.Sets;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Liuchangling on 2017/9/27.
 */
@Controller
@RequestMapping(value = "/api/user")
public class CspUserController extends BaseController {
    private static Log log = LogFactory.getLog(CspUserController.class);

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Autowired
    protected JPushService jPushService;

    @Autowired
    protected CspAppVideoService appVideoService;

    @Autowired
    protected EmailTempService tempService;

    @Value("${app.file.upload.base}")
    protected String uploadBase;

    @Value("${app.file.base}")
    protected String fileBase;


    /**
     * 注册csp账号
     *
     * @param userInfo
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(CspUserInfo userInfo) {
        if (userInfo == null) {
            return error(local("user.param.empty"));
        }

        String email = userInfo.getEmail();
        String password = userInfo.getPassword();
        String nickName = userInfo.getNickName();

        if (StringUtils.isEmpty(email)) {
            return error(local("user.username.notnull"));
        }
        if (!StringUtils.isEmail(email)) {
            return error(local("user.email.format"));
        }
        if (StringUtils.isEmpty(password)) {
            return error(local("user.password.notnull"));
        }
        if (StringUtils.isEmpty(nickName)) {
            return error(local("user.linkman.notnull"));
        }

        EmailTemplate template = tempService.getTemplate(LocalUtils.getLocalStr(),EmailTemplate.Type.REGISTER.getLabelId());

        try {

            return cspUserService.register(userInfo,template);

        } catch (SystemException e){
            return error(e.getMessage());
        }

    }

    /**
     * 缓存用户信息
     *
     * @param user
     * @return
     */
    protected Principal cachePrincipal(CspUserInfo user) {
        if (CheckUtils.isEmpty(user.getToken())) {
            user.setToken(UUIDUtil.getUUID());
            cspUserService.updateByPrimaryKey(user);
        }

        Principal principal = Principal.build(user);
        redisCacheUtils.setCacheObject(Constants.TOKEN + "_" + user.getToken(), principal, Constants.TOKEN_EXPIRE_TIME);
        return principal;
    }


    /**
     * 邮箱+密码、手机+验证码登录 、第三方账号登录
     * type 1=微信 2=微博 3=Facebook 4=Twitter 5=YaYa医师 6=手机 7=邮箱
     * 登录检查用户是否存在csp账号，如果存在，登录成功返回用户信息；
     * 反之，根据客户端传过来的第三方信息，保存到数据库，再返回登录成功及用户信息
     * @param email 邮箱
     * @param password 密码
     * @param password     密码
     * @param thirdPartyId 第三方平台id
     * @param mobile       手机
     * @param captcha      验证码
     * @param userInfoDTO  第三方用户信息
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(String email, String password, Integer thirdPartyId,
                        String mobile, String captcha, CspUserInfoDTO userInfoDTO,
                        HttpServletRequest request) {

        if (thirdPartyId == null || thirdPartyId == 0) {
           return error(local("user.empty.ThirdPartyId"));
        }

        CspUserInfo userInfo = null;
        // 第三方平台id
        int type = thirdPartyId.intValue();
        try {
            if (type == BindInfo.Type.EMAIL.getTypeId()) {
                // 邮箱登录
                userInfo = loginByEmail(email, password);

            } else if (type == BindInfo.Type.MOBILE.getTypeId()) {
                // 手机登录
                userInfo = loginByMobile(mobile, captcha);

            } else if (type <= BindInfo.Type.YaYa.getTypeId()) {
                // 第三方账号登录 含YaYa医师登录
                userInfo = loginByThirdParty(userInfoDTO);
            }

            // 当前登录的用户不是海外用户
            if (userInfo.getAbroad() == null) {
                userInfo.setAbroad(false);
            }
            if (userInfo.getAbroad().booleanValue() != LocalUtils.isAbroad()) {
                return error(local("user.login.error"));
            }

            // 更新用户登录信息
            userInfo.setLastLoginIp(request.getRemoteAddr());
            userInfo.setLastLoginTime(new Date());
            cspUserService.updateByPrimaryKey(userInfo);

            // 缓存用户信息
            cachePrincipal(userInfo);

            // 用户信息
            CspUserInfoDTO dto = CspUserInfoDTO.buildToCspUserInfoDTO(userInfo);
            if (dto.getAvatar() != null &&
                    dto.getAvatar().startsWith(Constants.AVATAR_URL_PREFIX) == false) {
                dto.setAvatar(fileBase + dto.getAvatar());
            }

            // 查询当前用户绑定的第三方平台列表
            List<BindInfo> bindInfoList = cspUserService.findBindListByUserId(userInfo.getId());
            if (!CheckUtils.isEmpty(bindInfoList)) {
                dto.setBindInfoList(bindInfoList);
            }

            return success(dto);

        } catch (SystemException e){
            return error(e.getMessage());

        } catch (PasswordErrorException pe) {
            return error(APIUtils.ERROR_PASSWORD, pe.getMessage());
        }

    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public String loginOut(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN);
        if (StringUtils.isNotEmpty(token)) {
            String cacheKey = Constants.TOKEN+"_" + token;
            redisCacheUtils.delete(cacheKey);
        }
        return success();
    }


    /**
     * 登录成功 绑定极光
     * @param registrationId
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindJPush")
    @ResponseBody
    public String bindJpush(String registrationId, HttpServletRequest request){
        String osType = request.getHeader(Constants.APP_OS_TYPE_KEY);

        Principal principal = SecurityUtils.get();
        String alias = jPushService.generateAlias((Object)principal.getId());

        Set<String> tags = Sets.newHashSet();
        try {

            if(!StringUtils.isEmpty(osType)){
                tags.add(osType);
            }

            jPushService.bindAliasAndTags(registrationId, alias, tags);

        } catch (APIConnectionException e) {
            e.printStackTrace();
            return error("极光推送连接异常");
        } catch (APIRequestException e) {
            e.printStackTrace();
            return error("极光推送请求异常");
        }
        return success();
    }


    /**
     * 邮箱登录
     * @param email
     * @param password
     */
    protected CspUserInfo loginByEmail(String email, String password) throws SystemException, PasswordErrorException {
        if (StringUtils.isEmpty(email)) {
            throw new SystemException(local("user.username.notnull"));
        }
        if (!StringUtils.isEmail(email)) {
            throw new SystemException(local("user.email.format"));
        }
        if (StringUtils.isEmpty(password)) {
            throw new SystemException(local("user.password.notnull"));
        }

        // 检查用户是否注册且已经激活
        CspUserInfo userInfo = cspUserService.findByLoginName(email);
        if (userInfo == null) {
            throw new SystemException(local("user.notexisted"));
        }
        // 邮箱未激活
        if (userInfo.getActive() == false) {
            throw new SystemException(local("user.unActive.email"));
        }
        // 用户输入密码是否正确
        if (!MD5Utils.md5(password).equals(userInfo.getPassword())) {
            throw new PasswordErrorException((local("user.password.error")));
        }

        return userInfo;
    }


    /**
     * 手机号码 + 验证码登录
     *
     * @param mobile
     * @param captcha
     */
    protected CspUserInfo loginByMobile(String mobile, String captcha) throws SystemException{
        if (StringUtils.isEmpty(mobile)) {
            throw new SystemException(local("user.empty.mobile"));
        }

        if (!StringUtils.isMobile(mobile)) {
            throw new SystemException(local("user.mobile.format"));
        }

        if (StringUtils.isEmpty(captcha)) {
            throw new SystemException(local("user.empty.captcha"));
        }
        // 检查验证码是否有效
        cspUserService.checkCaptchaIsOrNotValid(mobile, captcha);
        // 根据手机号码检查用户是否存在
        CspUserInfo userInfo = cspUserService.findByLoginName(mobile);
        if (userInfo == null) {
            // 注册新用户
            userInfo = new CspUserInfo();
            userInfo.setId(StringUtils.nowStr());
            userInfo.setMobile(mobile);
            userInfo.setRegisterTime(new Date());
            userInfo.setActive(true);
            userInfo.setAbroad(LocalUtils.isAbroad());
            cspUserService.insert(userInfo);
            userInfo.setFlux(0); // 用户流量
        }

        return userInfo;
    }


    /**
     * 第三方平台登录
     * @param userDTO
     * @return
     * @throws SystemException
     */
    protected CspUserInfo loginByThirdParty(CspUserInfoDTO userDTO) throws SystemException{
        if (userDTO == null) {
            throw new SystemException(local("user.param.empty"));
        }

        String uniqueId = userDTO.getUniqueId();
        if (StringUtils.isEmpty(uniqueId)) {
            throw new SystemException(local("user.param.empty"));
        }
        // 检查用户是否存在
        CspUserInfo userInfo = cspUserService.findBindUserByUniqueId(uniqueId);
        // 用户不存在,则获取第三方用户信息 保存至CSP用户表及绑定用户表
        if (userInfo == null) {
            userInfo = cspUserService.saveThirdPartyUserInfo(userDTO);
        }

        return userInfo;
    }


    /**
     * 发送手机验证码
     * type 发送短信验证码模板内容区分 0=登录 1=绑定
     */
    @RequestMapping("/sendCaptcha")
    @ResponseBody
    public String sendCaptcha(String mobile, Integer type) {
        if(!StringUtils.isMobile(mobile)){
            return error(local("user.mobile.format"));
        }

        if (type != Captcha.Type.LOGIN.getTypeId()
                && type != Captcha.Type.BIND.getTypeId()) {
            return APIUtils.error(local("error.param"));
        }

        try {
            return cspUserService.sendCaptcha(mobile, type);

        } catch (SystemException e) {
            return error(e.getMessage());
        }
    }


    /**
     * 修改头像
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    @ResponseBody
    public String updateAvatar(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null) {
            return error(local("upload.error.null"));
        }
        String userId = SecurityUtils.get().getId();
        String url = null;
        try {
            url = cspUserService.updateAvatar(file,userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        Map<String,String> map = new HashMap<>();
        map.put("url",url);
        return success(map);
    }


    /**
     * 更新个人信息中的姓名和简介
     */
    @RequestMapping("/updateInfo")
    @ResponseBody
    public String updateInfo(CspUserInfo info) {
       info.setId(SecurityUtils.get().getId());
       cspUserService.updateByPrimaryKeySelective(info);
       return success();
    }


    /**
     * 修改密码
     * 用户登录时后台会将邮箱地址返回给前端，前端根据有无返回邮箱地址判断是否需要绑定邮箱
     * 请求此接口，说明用户已绑定邮箱
     */
    @RequestMapping("/resetPwd")
    @ResponseBody
    public String resetPwd(String oldPwd,String newPwd) {

        if(StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
            return error(local("user.empty.password"));
        }
        String userId = SecurityUtils.get().getId();
        try {
            cspUserService.resetPwd(userId,oldPwd,newPwd);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     * 发送绑定邮件
     */
    @RequestMapping("/toBind")
    @ResponseBody
    public String toBind(String email,String password) {

        String userId = SecurityUtils.get().getId();
        String localStr = LocalUtils.getLocalStr();
        try {
            cspUserService.sendBindMail(email,password,userId,localStr);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        return success();

    }




    /**
     * 绑定手机号
     * @param mobile
     * @param captcha
     * @return
     */
    @RequestMapping("/bindMobile")
    @ResponseBody
    public String bindMobile(String mobile,String captcha)  {
        if(!StringUtils.isMobile(mobile) || StringUtils.isEmpty(captcha)){
            return error(local("error.param"));
        }
        try {
            //检查验证码合法性
            cspUserService.checkCaptchaIsOrNotValid(mobile,captcha);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        String userId = SecurityUtils.get().getId();
        try {
            cspUserService.doBindMobile(mobile,captcha,userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *解绑手机或邮箱
     * @param type 6代表手机,7代表邮箱
     * @return
     */
    @RequestMapping("/unbind")
    @ResponseBody
    public String unbindEmailOrMobile(Integer type){

        String userId = SecurityUtils.get().getId();
        try {
            cspUserService.doUnbindEmailOrMobile(type,userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();

    }


    /**
     * 绑定或解绑第三方账号
     * third_party_id 1代表微信，2代表微博，3代表facebook,4代表twitter,5代表YaYa医师
     * 解绑只传third_party_id，YaYa医师绑定传YaYa账号，密码,third_party_id
     */
    @RequestMapping("/changeBindStatus")
    @ResponseBody
    public String changeBindStatus(BindInfo info)  {

        String userId = SecurityUtils.get().getId();
        //第三方账号绑定操作
        if (!StringUtils.isEmpty(info.getUniqueId())) {
            try {
                cspUserService.doBindThirdAccount(info,userId);
            } catch (SystemException e) {
                return error(e.getMessage());
            }
        }else {
            //解绑操作
            try {
                cspUserService.doUnbindThirdAccount(info.getThirdPartyId(),userId);
            } catch (SystemException e) {
                return error(e.getMessage());
            }
        }
            return success();
    }


    /**
     * 获取csp登录页面的背景视频
     * @param version 前端传过来的版本号 不等于当前视频的版本号 才会返回视频url，反之不返回数据
     * @return
     */
    @RequestMapping("/login/video")
    @ResponseBody
    public String cspLoginVideo(Integer version) {
        if (version == null) {
            return error(local("user.param.empty"));
        }
        AppVideo video = appVideoService.findCspAppVideo();
        if (video != null && version < video.getVersion()) {
            video.setVideoUrl(fileBase + video.getVideoUrl());
            return success(video);
        }
        return success(new AppVideo());
    }


}
