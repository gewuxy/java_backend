package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.email.EmailHelper;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import cn.medcn.user.dao.BindInfoDAO;
import cn.medcn.user.dao.CspUserInfoDAO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import com.github.abel533.mapper.Mapper;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static cn.medcn.common.Constants.BIND_TEMPLATE_ID;
import static cn.medcn.common.Constants.LOGIN_TEMPLATE_ID;

/**
 * Created by Liuchangling on 2017/9/26.
 */
@Service
public class CspUserServiceImpl extends BaseServiceImpl<CspUserInfo> implements CspUserService {


    @Value("${csp.file.upload.base}")
    protected String uploadBase;

    @Value("${app.csp.base}")
    protected String cspBase;

    @Autowired
    protected CspUserInfoDAO cspUserInfoDAO;

    @Autowired
    protected BindInfoDAO bindInfoDAO;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Autowired
    protected JSmsService jSmsService;

    @Autowired
    protected EmailHelper emailHelper;

    @Autowired
    protected JPushService jPushService;

    @Override
    public Mapper<CspUserInfo> getBaseMapper() {
        return cspUserInfoDAO;
    }


    @Override
    public CspUserInfo findBindUserByUniqueId(String uniqueId) {
        return cspUserInfoDAO.findBindUserByUniqueId(uniqueId);
    }

    @Override
    public CspUserInfo findByLoginName(String username) {
        return cspUserInfoDAO.findByLoginName(username);
    }

    @Override
    public void register(CspUserInfo userInfo) throws SystemException{
        if (userInfo == null) {
            throw new SystemException("user info can not be null");
        }
        userInfo.setId(StringUtils.nowStr());
        String password = userInfo.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            userInfo.setPassword(MD5Utils.md5(password));
        }
        cspUserInfoDAO.insert(userInfo);
    }

    /**
     * 发送手机验证码
     * @param mobile
     * @param type 发送短信验证码模板内容区分 0=登录 1=绑定
     */
    @Override
    public String sendCaptcha(String mobile, Integer type) throws SystemException {
        if(!StringUtils.isMobile(mobile)){
            return APIUtils.error(SpringUtils.getMessage("user.error.mobile.format"));
        }
        int typeId = type.intValue();
        int loginTemplate = Captcha.Type.LOGIN.getTypeId().intValue();
        int bindTemplate = Captcha.Type.BIND.getTypeId().intValue();
        if (type != loginTemplate && type != bindTemplate) {
            return APIUtils.error(SpringUtils.getMessage("error.param"));
        }
        //10分钟内最多允许获取3次验证码
        Captcha captcha = (Captcha)redisCacheUtils.getCacheObject(mobile);
        if(captcha == null){ //第一次获取
            // 发送短信
            String msgId = sendCaptchaByType(mobile, typeId);

            Captcha firstCaptcha = new Captcha();
            firstCaptcha.setFirstTime(new Date());
            firstCaptcha.setCount(Constants.NUMBER_ZERO);
            firstCaptcha.setMsgId(msgId);
            redisCacheUtils.setCacheObject(mobile,firstCaptcha,Constants.CAPTCHA_CACHE_EXPIRE_TIME); //15分钟有效期
            return APIUtils.success();

        }else {
            Long between = System.currentTimeMillis() - captcha.getFirstTime().getTime();
            if(captcha.getCount() == 2 && between < TimeUnit.MINUTES.toMillis(10)){
                return APIUtils.error("获取验证码次数频繁，请稍后");
            }
            // 发送短信
            String msgId = sendCaptchaByType(mobile, typeId);

            captcha.setMsgId(msgId);
            captcha.setCount(captcha.getCount() + 1);
            redisCacheUtils.setCacheObject(mobile,captcha,Constants.CAPTCHA_CACHE_EXPIRE_TIME);
        }

        return APIUtils.success();
    }

    /**
     * 根据 短信模板类型  获取不同的短信内容
     * @param mobile
     * @param type 短信模板类型 0=登录 1=绑定
     */
    protected String sendCaptchaByType(String mobile, int type) throws SystemException{
        String msgId = null;
        try {
            if (type == Captcha.Type.LOGIN.getTypeId().intValue()) {
                msgId = jSmsService.send(mobile, LOGIN_TEMPLATE_ID);
            } else {
                msgId = jSmsService.send(mobile, BIND_TEMPLATE_ID);
            }

        } catch (Exception e) {
            throw new SystemException(SpringUtils.getMessage("sms.error.send"));
        }

        return msgId;
    }

    @Override
    public void checkCaptchaIsOrNotValid(String mobile, String captcha) {

    }

    /**
     * 添加第三方平台用户及绑定用户信息
     * @param userDTO
     * @return
     */
    public CspUserInfo saveThirdPartyUserInfo(CspUserInfoDTO userDTO) {
        String userAvatarUrl = userDTO.getAvatar();
        if (StringUtils.isNotEmpty(userAvatarUrl)) {
            userDTO.setAvatar(saveAvatarFromThirdPlatform(userAvatarUrl));
        }

        CspUserInfo userInfo = CspUserInfo.buildToUserInfo(userDTO);
        cspUserInfoDAO.insert(userInfo);

        // 添加绑定第三方平台用户信息
        userDTO.setUid(userInfo.getId());
        BindInfo bindUser = BindInfo.buildToBindInfo(userDTO);
        bindInfoDAO.insert(bindUser);

        return userInfo;
    }

    /**
     * 发送绑定邮件
     * @param email
     * @param userId
     * @return
     */
    @Override
    public String sendMail(String email, String userId)  {
        //TODO
        CspUserInfo info = findByLoginName(email);
        if (info != null) { //当前邮箱已被绑定
            return  "user.exist.email";
        }
        CspUserInfo user = selectByPrimaryKey(userId);
        if (!StringUtils.isEmpty(user.getEmail())) {  //当前账号已绑定邮箱
            return  "user.has.email";
        }
        String code = StringUtils.uniqueStr();
        redisCacheUtils.setCacheObject(Constants.EMAIL_LINK_PREFIX_KEY + code, email + "," + userId, (int) TimeUnit.DAYS.toSeconds(1));
        String url = cspBase + "/api/user/bindEmail?code=" + code;
        try {
            emailHelper.sendMail(email, "绑定邮箱", url, "bindEmail");
        } catch (JDOMException e) {
            e.printStackTrace();
            return "email.address.error";
        } catch (Exception e) {
            e.printStackTrace();
            return "email.error.send";
        }
        return null;
    }


    /**
     * 绑定手机号
     * @param mobile
     * @param captcha
     * @param userId
     */
    @Override
    public String doBindMobile(String mobile, String captcha, String userId) {
        checkCaptchaIsOrNotValid(captcha,mobile);
        CspUserInfo result = findByLoginName(mobile);
        //该手机号已被绑定
        if(result != null){
            return "mobile.was.bound";
        }
        CspUserInfo info = selectByPrimaryKey(userId);
        //用户已绑定手机号
        if(!StringUtils.isEmpty(info.getMobile())){
            return "user.has.mobile";
        }
        info.setMobile(mobile);
        updateByPrimaryKey(info);
        return null;

    }

    /**
     * 解绑邮箱或手机
     * @param type
     * @param userId
     * @return
     */
    @Override
    public String doUnbindEmailOrMobile(Integer type, String userId) {
        if(type != Type.EMAIL && type != Type.MOBILE){
            return "error.param";
        }
        CspUserInfo info = selectByPrimaryKey(userId);
        if(type == Type.EMAIL){
            //用户没有绑定邮箱
            if(StringUtils.isEmpty(info.getEmail())){
                return "user.not.exist.email";
            }
                info.setEmail("");
        }else{
            //用户没有绑定手机
            if(StringUtils.isEmpty(info.getMobile())){
                return "user.not.exist.mobile";
            }
                info.setMobile("");
        }

            updateByPrimaryKeySelective(info);

        return null;
    }

    /**
     * 绑定第三方账号
     * @param info
     * @param userId
     * @return
     */
    @Override
    public String doBindThirdAccount(BindInfo info, String userId) {
        BindInfo condition = new BindInfo();
        condition.setUniqueId(info.getUniqueId());
        condition.setThirdPartyId(info.getThirdPartyId());
        condition = bindInfoDAO.selectOne(condition);
        if(condition != null){  //该第三方账号已被使用
           return "user.exist.account";
        }

        BindInfo condition2 = new BindInfo();
        condition2.setUserId(userId);
        condition2.setThirdPartyId(info.getThirdPartyId());
        condition2 = bindInfoDAO.selectOne(condition2);
        if(condition2 != null){  //当前的账号已绑定其他第三方账号
            return "user.exist.ThirdAccount";
        }

        String path = saveAvatarFromThirdPlatform(info.getAvatar());
        //插入到数据库
        info.setAvatar(path);
        info.setUserId(userId);
        info.setBindDate(new Date());
        bindInfoDAO.insert(info);
        return null;
    }

    /**
     * 解绑第三方账号
     * @param info
     * @param userId
     * @return
     */
    @Override
    public String doUnbindThirdAccount(BindInfo info, String userId) {
        BindInfo condition = new BindInfo();
        condition.setUserId(userId);
        condition.setThirdPartyId(info.getThirdPartyId());
        condition = bindInfoDAO.selectOne(condition);
        if(condition == null){  //用户没绑定此第三方账号，不能解绑
           return "user.notExist.ThirdAccount";
        }

        CspUserInfo user = selectByPrimaryKey(userId);
        String email = user.getEmail();
        String mobile = user.getMobile();
        //没有绑定手机和邮箱
        if(StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)){
            BindInfo condition2 = new BindInfo();
            condition2.setUserId(userId);
            int count = bindInfoDAO.selectCount(condition2);
            if(count <= 1){  //没有绑定其他第三方账号，不能解绑
                return "user.only.one.account";
            }
        }

        bindInfoDAO.delete(condition);
        //删除头像
        deleteAvatar(condition);
        return null;
    }

    @Override
    public void doBindMail(String key, String result) throws SystemException {
        String email = result.substring(0, result.indexOf(","));
        String userId = result.substring(result.indexOf(",") + 1);
        CspUserInfo info = selectByPrimaryKey(userId);
        if (info == null) { //用户不存在
            throw new SystemException(local("user.error.nonentity"));
        }
        info.setEmail(email);
        updateByPrimaryKeySelective(info);
        redisCacheUtils.delete(key);
        //发送推送通知邮箱已绑定
        jPushService.sendChangeMessage(Integer.valueOf(userId),"3",email);
    }

    protected interface Type{
        Integer EMAIL = 0;
        Integer MOBILE = 1;
    }


    /**
     * 删除头像文件
     * @param condition
     */
    private void deleteAvatar(BindInfo condition) {
        //删除头像文件
        String avatar = condition.getAvatar();
        if(StringUtils.isEmpty(avatar)){
            FileUtils.deleteTargetFile(uploadBase + avatar);
        }
    }

    /**
     * 将第三方平台和YaYa医师平台获取的头像保存到csp
     * @param avatar
     * @return
     */
    private String saveAvatarFromThirdPlatform(String avatar) {
        //将头像保存到csp
        String relativePath = FilePath.PORTRAIT.path + File.separator;
        String suffix = FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix;
        String fileName = StringUtils.nowStr() + suffix;
        File file = new File(avatar);
        FileUtils.saveFile(uploadBase + relativePath, fileName, file);
        return relativePath + fileName;
    }
}
