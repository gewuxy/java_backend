package cn.medcn.csp.controller.api;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.medcn.article.model.AppVideo;
import cn.medcn.article.service.CspAppVideoService;
import cn.medcn.common.Constants;
import cn.medcn.common.excptions.PasswordErrorException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.PushService;
import cn.medcn.common.utils.*;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.service.AudioService;
import cn.medcn.sys.service.SysNotifyService;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.*;
import cn.medcn.user.service.CspPackageService;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import cn.medcn.weixin.util.EmojiFilterUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.text.ParseException;
import java.util.*;

import static cn.medcn.common.Constants.*;
import static cn.medcn.weixin.config.MiniProgramConfig.*;


/**
 * Created by Liuchangling on 2017/9/27.
 */
@Controller
@RequestMapping(value = "/api/user")
public class CspUserController extends CspBaseController {

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected PushService cspPushService;

    @Autowired
    protected CspAppVideoService appVideoService;

    @Autowired
    protected EmailTempService tempService;


    @Value("${app.file.upload.base}")
    protected String uploadBase;


    @Value("${app.file.base}")
    protected String fileBase;

    @Value("${mini.appid}")
    private String appId;

    @Value("${mini.secret}")
    private String secret;

    @Autowired
    protected CspPackageService packageService;

    @Autowired
    protected CspUserPackageService userPackageService;

    @Autowired
    protected AudioService audioService;

    @Autowired
    protected SysNotifyService sysNotifyService;

    /**
     * 注册csp账号
     *
     * @param userInfo
     * @param request
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(CspUserInfo userInfo, HttpServletRequest request) {
        if (userInfo == null) {
            return error(local("user.param.empty"));
        }

        // 获取是否海外注册
        Boolean abroad = LocalUtils.isAbroad();
        userInfo.setAbroad(abroad);

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

        //去除昵称中的非法字符，表情
        userInfo.setNickName(EmojiFilterUtil.filterName(userInfo.getNickName()));
        // 获取邮箱模板
        EmailTemplate template = tempService.getTemplate(LocalUtils.getLocalStr(), EmailTemplate.Type.REGISTER.getLabelId(), EmailTemplate.UseType.CSP.getLabelId());
        try {
            //添加用户的注册设备
            userInfo.setRegisterDevice(CspUserInfo.RegisterDevice.APP.ordinal());

            userInfo.setLastLoginIp(request.getRemoteAddr());

            return cspUserService.register(userInfo, template);

        } catch (SystemException e) {
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

        // 缓存用户套餐id
        CspPackage cspPackage = packageService.findUserPackageById(user.getId());
        if (cspPackage != null) {
            // 缓存用户套餐过期信息
            packageExpireRemind(cspPackage, principal);

        }

        redisCacheUtils.setCacheObject(Constants.TOKEN + "_" + user.getToken(), principal, Constants.TOKEN_EXPIRE_TIME);
        return principal;
    }

    /**
     * 缓存用户过期信息
     *
     * @param cspPackage
     * @return
     */
    protected String packageExpireRemind(CspPackage cspPackage, Principal principal) {
        String remind = null;

        try {
            if (cspPackage != null) {
                // 缓存用户套餐信息
                principal.setPackageId(cspPackage.getId());
                int diffDays = 0;
                if (cspPackage.getPackageEnd() != null) {
                    diffDays = CalendarUtils.daysBetween(new Date(), cspPackage.getPackageEnd());
                }

                // 还有5天倒计时到期时提醒
                if (diffDays <= EXPIRE_DAYS && diffDays > NUMBER_ZERO) {
                    remind = local("days.expire.remind", new Object[]{diffDays});

                    // 设置即将到期时间
                    cspPackage.setExpireDays(diffDays);

                } else if (diffDays == 0) { // 已经过期提醒
                    //  已经使用的会议数 -3 = 隐藏的会议数
                    int usedMeetCount = cspPackage.getUsedMeetCount();
                    if (usedMeetCount > NUMBER_THREE) {
                        // 只显示3个会议 其他的隐藏
                        int hiddenMeetCount = usedMeetCount - NUMBER_THREE;
                        remind = local("expire.remind.info", new Object[]{hiddenMeetCount});

                        // 过期隐藏的会议数
                        cspPackage.setHiddenMeetCount(hiddenMeetCount);
                    }

                    // 会议上锁
                    audioService.doModifyAudioCourseByPackageId(cspPackage.getUserId(), cspPackage.getId());
                }

                if (StringUtils.isNotEmpty(remind)) {
                    cspPackage.setExpireRemind(remind);
                }
                principal.setCspPackage(cspPackage);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            remind = local("data.error");
        }
        return remind;
    }

    /**
     * 邮箱+密码、手机+验证码登录 、第三方账号登录
     * {@link BindInfo.Type}
     * 登录检查用户是否存在csp账号，如果存在，登录成功返回用户信息；
     * 反之，根据客户端传过来的第三方信息，保存到数据库，再返回登录成功及用户信息
     *
     * @param email        邮箱
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

        //去除昵称中的非法字符
        userInfoDTO.setNickName(EmojiFilterUtil.filterName(userInfoDTO.getNickName()));

        // 获取app header中是否海外登录
        Boolean abroad = LocalUtils.isAbroad();
        userInfoDTO.setAbroad(abroad);

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

            Boolean frozen = userInfo.getFrozenState();
            // 账号已经被冻结
            if (frozen != null && frozen) {
                return accountFrozenError();
            }

            // 检查当前登录的用户 是否海外用户
            abroad = userInfo.getAbroad();
            if (abroad == null) {
                abroad = false;
            }
            if (abroad && !LocalUtils.isAbroad()) {
                // 海外账号 在国内登录
                return error(local("en.user.login.error"));
            }
            if (!abroad && LocalUtils.isAbroad()) {
                // 国内账号 在海外登录
                return error(local("cn.user.login.error"));
            }

            // 更新用户登录信息
            userInfo.setLastLoginIp(request.getRemoteAddr());
            userInfo.setLastLoginTime(new Date());
            cspUserService.updateByPrimaryKey(userInfo);

            CspUserPackage cspUserPackage = cspUserPackageService.selectByPrimaryKey(userInfo.getId());
            if (cspUserPackage == null){
                // YaYa账号登录
                if (userInfoDTO.getThirdPartyId().equals(BindInfo.Type.YaYa.getTypeId())) {
                    // 设置YaYa账号套餐版本信息
                    cspUserService.yayaBindUpdate(userInfo.getId());
                    // 判断是否新用户
                    if (userInfo.getState()) {
                        userInfo.setState(true);
                        cspUserService.updateByPrimaryKey(userInfo);
                    }
                } else if (userInfo.getState() && !userInfoDTO.getThirdPartyId().equals(BindInfo.Type.YaYa.getTypeId())) {
                    modifyOldUser(userInfo);
                } else {
                    //app端用户默认给标准版
                    cspUserPackageService.addStanardInfo(userInfo.getId());
                }
            }

            // 缓存用户信息
            Principal principal = cachePrincipal(userInfo);

            // 返回给前端的用户数据
            CspUserInfoDTO dto = buildCspUserInfoDTO(principal, userInfo);

            return success(dto);

        } catch (SystemException e) {
            return error(e.getMessage());

        } catch (PasswordErrorException pe) {
            return error(APIUtils.ERROR_PASSWORD, pe.getMessage());
        }
    }


    /**
     * 封装返回给前端的用户数据
     *
     * @param principal
     * @param userInfo
     * @return
     */
    private CspUserInfoDTO buildCspUserInfoDTO(Principal principal, CspUserInfo userInfo) {
        // 用户信息
        CspUserInfoDTO dto = CspUserInfoDTO.buildToCspUserInfoDTO(userInfo);
        if (needAvatarPrefix(dto.getAvatar())) {
            dto.setAvatar(fileBase + dto.getAvatar());
        }

        // 查询当前用户绑定的第三方平台列表
        List<BindInfo> bindInfoList = cspUserService.findBindListByUserId(userInfo.getId());
        if (!CheckUtils.isEmpty(bindInfoList)) {
            dto.setBindInfoList(bindInfoList);
        }

        // 返回用户套餐 及 套餐过期提醒
        if (principal.getCspPackage() != null) {
            dto.setCspPackage(principal.getCspPackage());
        }

        return dto;
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN);
        if (StringUtils.isNotEmpty(token)) {
            String cacheKey = Constants.TOKEN + "_" + token;
            redisCacheUtils.delete(cacheKey);
        }
        return success();
    }


    /**
     * 登录成功 绑定极光
     *
     * @param registrationId
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindJPush")
    @ResponseBody
    public String bindJpush(String registrationId, HttpServletRequest request) {
        String osType = request.getHeader(Constants.APP_OS_TYPE_KEY);

        Principal principal = SecurityUtils.get();
        String alias = cspPushService.generateAlias(principal.getId());

        Set<String> tags = Sets.newHashSet();
        try {

            if (!StringUtils.isEmpty(osType)) {
                tags.add(osType);
            }

            cspPushService.bindAliasAndTags(registrationId, alias, tags);

        } catch (APIConnectionException e) {
            e.printStackTrace();
            return error(local("jpush.connect.error"));
        } catch (APIRequestException e) {
            e.printStackTrace();
            return error(local("jpush.request.error"));
        }
        return success();
    }


    /**
     * 邮箱登录
     *
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

        // 检查用户是否注册并激活
        CspUserInfo userInfo = cspUserService.findByLoginName(email);
        if (userInfo == null) {
            throw new SystemException(local("user.notexisted"));
        }
        // 邮箱未激活
        if (!userInfo.getActive()) {
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
    protected CspUserInfo loginByMobile(String mobile, String captcha) throws SystemException {
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
        checkCaptchaIsOrNotValid(mobile, captcha);

        // 根据手机号码检查用户是否存在
        CspUserInfo userInfo = cspUserService.findByLoginName(mobile);

        if (userInfo == null) {
            // 注册新用户
            userInfo = new CspUserInfo();
            String userId = StringUtils.nowStr();
            userInfo.setId(userId);
            userInfo.setMobile(mobile);
            userInfo.setRegisterTime(new Date());
            userInfo.setActive(true);
            userInfo.setAbroad(LocalUtils.isAbroad());
            userInfo.setRegisterFrom(BindInfo.Type.MOBILE.getTypeId());
            userInfo.setState(false);
            //添加用户的注册设备
            userInfo.setRegisterDevice(CspUserInfo.RegisterDevice.APP.ordinal());
            cspUserService.insert(userInfo);
            //推送注册成功消息
            sysNotifyService.addNotify(userId,local("user.notify.title"),local("user.notify.content"),local("user.notify.sender"));
            userInfo.setFlux(0); // 用户流量
        }

        return userInfo;
    }


    /**
     * 第三方平台登录
     *
     * @param userDTO
     * @return
     * @throws SystemException
     */
    protected CspUserInfo loginByThirdParty(CspUserInfoDTO userDTO) throws SystemException {
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
            //添加注册用户的设备
            userDTO.setRegisterDevice(CspUserInfo.RegisterDevice.APP.ordinal());
            userInfo = cspUserService.saveThirdPartyUserInfo(userDTO);
            userInfo.setFlux(0);
        }

        return userInfo;
    }


    /**
     * 发送手机验证码
     * type 0=登录 1=绑定 根据短信模板类型 获取不同的短信内容
     *
     * @param mobile
     * @param type
     */
    @RequestMapping("/sendCaptcha")
    @ResponseBody
    public String sendCaptcha(String mobile, Integer type) {
        if (!StringUtils.isMobile(mobile)) {
            return error(local("user.mobile.format"));
        }

        if (type != Captcha.Type.LOGIN.getTypeId()
                && type != Captcha.Type.BIND.getTypeId()) {
            return APIUtils.error(local("error.param"));
        }

        try {
            if (type == Captcha.Type.LOGIN.getTypeId()) {
                return sendMobileCaptcha(mobile, CSP_LOGIN_TEMPLATE_ID);
            } else {
                return sendMobileCaptcha(mobile, CSP_BIND_TEMPLATE_ID);
            }

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
            url = cspUserService.updateAvatar(file, userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        return success(map);
    }


    /**
     * 更新个人信息中的姓名和简介
     */
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody
    public String updateInfo(CspUserInfo info) {

        //修改昵称,昵称为空
        if (info.getInfo() == null && StringUtils.isEmpty(info.getNickName())) {
            return error(local("user.empty.nickname"));
        }
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
    public String resetPwd(String oldPwd, String newPwd) {

        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
            return error(local("user.empty.password"));
        }
        String userId = SecurityUtils.get().getId();
        try {
            cspUserService.resetPwd(userId, oldPwd, newPwd);
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
    public String toBind(String email, String password) {

        String userId = SecurityUtils.get().getId();
        String localStr = LocalUtils.getLocalStr();
        try {
            cspUserService.sendBindMail(email, password, userId, localStr);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        return success();

    }


    /**
     * 绑定手机号
     *
     * @param mobile
     * @param captcha
     * @return
     */
    @RequestMapping("/bindMobile")
    @ResponseBody
    public String bindMobile(String mobile, String captcha) {
        if (!StringUtils.isMobile(mobile) || StringUtils.isEmpty(captcha)) {
            return error(local("error.param"));
        }
        try {
            //检查验证码合法性
            checkCaptchaIsOrNotValid(mobile, captcha);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        String userId = SecurityUtils.get().getId();
        try {
            cspUserService.doBindMobile(mobile, captcha, userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     * 解绑手机或邮箱
     *
     * @param type 6代表手机,7代表邮箱
     * @return
     */
    @RequestMapping("/unbind")
    @ResponseBody
    public String unbindEmailOrMobile(Integer type) {

        String userId = SecurityUtils.get().getId();
        try {
            cspUserService.doUnbindEmailOrMobile(type, userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();

    }


    /**
     * 绑定或解绑第三方账号
     * {@link BindInfo.Type}
     * 解绑只传third_party_id，YaYa医师绑定传YaYa账号，密码,third_party_id
     */
    @RequestMapping("/changeBindStatus")
    @ResponseBody
    public String changeBindStatus(BindInfo info) {
        String userId = SecurityUtils.get().getId();
        //第三方账号绑定操作
        if (!StringUtils.isEmpty(info.getUniqueId())) {
            try {
                cspUserService.doBindThirdAccount(info, userId);
            } catch (SystemException e) {
                return error(e.getMessage());
            }
        } else {
            //解绑操作
            try {
                Integer thirdType = info.getThirdPartyId();
                Integer updatePackageId = cspUserService.doUnbindThirdAccount(thirdType, userId);
                if(thirdType == BindInfo.Type.YaYa.getTypeId()){
                    audioService.doModifyAudioCourseByPackageId(userId,updatePackageId);
                    updatePackagePrincipal(userId);
                }
            } catch (Exception e) {
                return error(e.getMessage());
            }
        }
        return success();
    }


    /**
     * 获取csp登录页面的背景视频
     *
     * @param version 前端传过来的版本号 不等于当前视频的版本号 才会返回视频url，反之不返回数据
     * @return
     */
    @RequestMapping("/login/video")
    @ResponseBody
    public String cspLoginVideo(Integer version) {
        if (version == null) {
            version = 0;
        }
        AppVideo video = appVideoService.findCspAppVideo();
        if (video != null && version < video.getVersion()) {
            video.setVideoUrl(fileBase + video.getVideoUrl());
            return success(video);
        }
        return success(new AppVideo());
    }


    /**
     * 前端定时请求此接口获取用户信息
     *
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public String cspUserInfo() {
        Principal principal = SecurityUtils.get();
        String userId = principal.getId();
        CspUserInfo userInfo = cspUserService.findUserInfoById(userId);

        // 用户信息
        CspUserInfoDTO dto = CspUserInfoDTO.buildToCspUserInfoDTO(userInfo);
        if (needAvatarPrefix(dto.getAvatar())) {
            dto.setAvatar(fileBase + dto.getAvatar());
        }

        // 查询当前用户绑定的第三方平台列表
        List<BindInfo> bindInfoList = cspUserService.findBindListByUserId(userInfo.getId());
        if (!CheckUtils.isEmpty(bindInfoList)) {
            dto.setBindInfoList(bindInfoList);
        }

        // 查询用户套餐及是否过期
        assignPackageAndExpire(dto, principal);

        // 修改缓存数据
        String userToken = Constants.TOKEN + "_" + userInfo.getToken();
        principal = redisCacheUtils.getCacheObject(userToken);
        if (principal != null) {
            principal.setCspPackage(dto.getCspPackage());
            principal.setPackageId(dto.getCspPackage().getId());
        }
        redisCacheUtils.setCacheObject(userToken, principal, Constants.TOKEN_EXPIRE_TIME);

        return success(dto);
    }

    /**
     * 查询用户套餐版本及过期信息
     *
     * @param userInfoDTO
     */
    private void assignPackageAndExpire(CspUserInfoDTO userInfoDTO, Principal principal) {
        // 获取当前用户套餐
        CspPackage cspPackage = packageService.findUserPackageById(userInfoDTO.getUid());
        if (cspPackage != null) {
            // 过期提醒信息
            packageExpireRemind(cspPackage, principal);

            userInfoDTO.setCspPackage(cspPackage);
        }

    }


    /**
     * 小程序接口，获取unionid
     * @param code
     * @return
     */
    @RequestMapping("/mini/unionid")
    @ResponseBody
    public String getUnionId(String code){
        if(StringUtils.isEmpty(code)){
            return error("code不能为空");
        }
        Map<String,Object> map = new HashMap<>();
        map.put(MINI_APPID_KEY,appId);
        map.put(MINI_SECRET_KEY,secret);
        map.put(JS_CODE_KEY,code);
        map.put(GRANT_TYPE_KEY,UNION_ID_GRANT_TYPE_VALUE);
        String result = HttpUtils.get(UNIONID_URL,map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String errCode = jsonObject.getString(ERR_CODE_STR);
        //如果获取数据没有出错，将session_key存到缓存，判断用户是否已注册
        if(StringUtils.isEmpty(errCode)){
            //将session_key存到缓存
            String sessionKey = jsonObject.getString(SESSION_KEY_STR);
            String key = UUIDUtil.getUUID();
            //TODO 存储的时长
            redisCacheUtils.setCacheObject(key,sessionKey, Constants.TOKEN_EXPIRE_TIME);
            //uuid的值作为对外的sessionKey
            jsonObject.put(SESSION_KEY_STR,key);

            //判断是否注册过
            String unionId = jsonObject.getString(UNION_ID_STR);
            if(StringUtils.isNotEmpty(unionId)){
                CspUserInfo info = cspUserService.findBindUserByUniqueId(unionId);
                jsonObject.put("has_user",info == null ? "false": "true");
            }

            return success(jsonObject);
        }
        return success(jsonObject);
    }

    /**
     *微信小程序接口，获取用户信息
     * @param encryptedData
     * @param sessionKey uuid值
     * @param iv
     * @return
     */
    @RequestMapping("/mini/info")
    @ResponseBody
    public String getInfo(String encryptedData,String sessionKey,String iv){
        if(StringUtils.isEmpty(encryptedData)){
            return error("encryptedData不能为空");
        }
        if(StringUtils.isEmpty(sessionKey)){
            return error("sessionKey不能为空");
        }
        if(StringUtils.isEmpty(iv)){
            return error("iv不能为空");
        }

        //获取存储的session_key
        String realSessionKey = redisCacheUtils.getCacheObject(sessionKey);
        if(StringUtils.isEmpty(realSessionKey)){
            return error("sessionKey失效或错误");
        }

        // 被加密的数据
        byte[] dataByte = org.codehaus.xfire.util.Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = org.codehaus.xfire.util.Base64.decode(realSessionKey);
        // 偏移量
        byte[] ivByte = org.codehaus.xfire.util.Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(result);
                return success(jsonObject);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return error("获取失败");
        }
        return null;
    }




}
