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

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static cn.medcn.common.Constants.*;

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
    public String register(CspUserInfo userInfo) {
        if (userInfo == null) {
            return APIUtils.error(local("user.param.empty"));
        }
        String username = userInfo.getEmail();

        // 检查用户邮箱是否已经注册过
        CspUserInfo cspUser = cspUserInfoDAO.findByLoginName(username);
        if (cspUser != null) {
            return APIUtils.error(local("user.username.existed"));
        }

        userInfo.setId(StringUtils.nowStr());
        String password = userInfo.getPassword();
        userInfo.setPassword(MD5Utils.md5(password));
        userInfo.setRegisterTime(new Date());
        cspUserInfoDAO.insert(userInfo);
        return APIUtils.success(local("user.success.register"));
    }

    /**
     * 发送手机验证码
     * @param mobile
     * @param type 发送短信验证码模板内容区分 0=登录 1=绑定
     */
    @Override
    public String sendCaptcha(String mobile, Integer type) throws SystemException {
        //10分钟内最多允许获取3次验证码
        Captcha captcha = (Captcha)redisCacheUtils.getCacheObject(mobile);
        if(captcha == null){ //第一次获取
            // 发送短信
            String msgId = sendCaptchaByType(mobile, type);

            Captcha firstCaptcha = new Captcha();
            firstCaptcha.setFirstTime(new Date());
            firstCaptcha.setCount(Constants.NUMBER_ZERO);
            firstCaptcha.setMsgId(msgId);
            redisCacheUtils.setCacheObject(mobile,firstCaptcha,Constants.CAPTCHA_CACHE_EXPIRE_TIME); //15分钟有效期

        }else {
            Long between = System.currentTimeMillis() - captcha.getFirstTime().getTime();
            if(captcha.getCount() == 2 && between < TimeUnit.MINUTES.toMillis(10)){
                return APIUtils.error("获取验证码次数频繁，请稍后");
            }
            // 发送短信
            String msgId = sendCaptchaByType(mobile, type);

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
                msgId = jSmsService.send(mobile, Constants.CSP_LOGIN_TEMPLATE_ID);
            } else {
                msgId = jSmsService.send(mobile, Constants.CSP_BIND_TEMPLATE_ID);
            }

        } catch (Exception e) {
            throw new SystemException(local("sms.error.send"));
        }

        return msgId;
    }

    @Override
    public void checkCaptchaIsOrNotValid(String mobile, String captcha) throws SystemException {
        // 从缓存获取此号码的短信记录
        Captcha result = (Captcha) redisCacheUtils.getCacheObject(Constants.CSP_MOBILE_CACHE_PREFIX_KEY + mobile);
        try {

            Boolean bool = jSmsService.verify(result.getMsgId(),captcha);
            if (!bool) {
                throw new SystemException(local("sms.error.captcha"));
            }

        } catch (Exception e) {
            throw new SystemException(local("sms.invalid.captcha"));
        }
    }

    /**
     * 添加第三方平台用户及绑定用户信息
     * @param userDTO
     * @return
     */
    public CspUserInfo saveThirdPartyUserInfo(CspUserInfoDTO userDTO) {
        // 将第三方用户信息 保存到csp用户表
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
    public void sendMail(String email, String userId) throws SystemException {
        //TODO
        CspUserInfo info = findByLoginName(email);
        if (info != null) { //当前邮箱已被绑定
            throw new SystemException(local("user.exist.email"));
        }
        CspUserInfo user = selectByPrimaryKey(userId);
        if (!StringUtils.isEmpty(user.getEmail())) {  //当前账号已绑定邮箱
            throw new SystemException(local("user.has.email"));
        }
        String code = StringUtils.uniqueStr();
        redisCacheUtils.setCacheObject(Constants.EMAIL_LINK_PREFIX_KEY + code, email + "," + userId, (int) TimeUnit.DAYS.toSeconds(1));
        String url = cspBase + "/api/user/bindEmail?code=" + code;
        try {
            emailHelper.sendMail(email, "绑定邮箱", url, "bindEmail");
        } catch (JDOMException e) {
            e.printStackTrace();
            throw new SystemException(local("email.address.error"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(local("email.error.send"));
        }
    }


    /**
     * 绑定手机号
     * @param mobile
     * @param captcha
     * @param userId
     */
    @Override
    public void doBindMobile(String mobile, String captcha, String userId) throws SystemException {
        CspUserInfo result = findByLoginName(mobile);
        //该手机号已被绑定
        if(result != null){
            throw new SystemException(local("mobile.was.bound"));
        }
        CspUserInfo info = selectByPrimaryKey(userId);
        //用户已绑定手机号
        if(!StringUtils.isEmpty(info.getMobile())){
            throw new SystemException(local("user.has.mobile"));
        }
        info.setMobile(mobile);
        updateByPrimaryKey(info);

    }

    /**
     * 解绑邮箱或手机
     * @param type
     * @param userId
     * @return
     */
    @Override
    public void doUnbindEmailOrMobile(Integer type, String userId) throws SystemException {
        if(type != Type.EMAIL && type != Type.MOBILE){
            throw new SystemException(local("error.param"));
        }
        CspUserInfo info = selectByPrimaryKey(userId);
        if(type == Type.EMAIL){
            //用户没有绑定邮箱
            if(StringUtils.isEmpty(info.getEmail())){
                throw new SystemException(local("user.not.exist.email"));
            }
                info.setEmail("");
        }else{
            //用户没有绑定手机
            if(StringUtils.isEmpty(info.getMobile())){
                throw new SystemException(local("user.not.exist.mobile"));
            }
                info.setMobile("");
        }
            updateByPrimaryKeySelective(info);

    }

    /**
     * 绑定第三方账号
     * @param info
     * @param userId
     * @return
     */
    @Override
    public void doBindThirdAccount(BindInfo info, String userId) throws SystemException {
        BindInfo condition = new BindInfo();
        condition.setUniqueId(info.getUniqueId());
        condition.setThirdPartyId(info.getThirdPartyId());
        condition = bindInfoDAO.selectOne(condition);
        if(condition != null){  //该第三方账号已被使用
            throw new SystemException(local("user.exist.account"));
        }

        BindInfo condition2 = new BindInfo();
        condition2.setUserId(userId);
        condition2.setThirdPartyId(info.getThirdPartyId());
        condition2 = bindInfoDAO.selectOne(condition2);
        if(condition2 != null){  //当前的账号已绑定其他第三方账号
            throw new SystemException(local("user.exist.ThirdAccount"));
        }

        //插入到数据库
        info.setUserId(userId);
        info.setBindDate(new Date());
        bindInfoDAO.insert(info);
    }

    /**
     * 解绑第三方账号
     * @param info
     * @param userId
     * @return
     */
    @Override
    public void doUnbindThirdAccount(BindInfo info, String userId) throws SystemException {
        BindInfo condition = new BindInfo();
        condition.setUserId(userId);
        condition.setThirdPartyId(info.getThirdPartyId());
        condition = bindInfoDAO.selectOne(condition);
        if(condition == null){  //用户没绑定此第三方账号，不能解绑
            throw new SystemException(local("user.notExist.ThirdAccount"));
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
                throw new SystemException(local("user.only.one.account"));
            }
        }

        bindInfoDAO.delete(condition);
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




//    /**
//     * 将第三方平台和YaYa医师平台获取的头像保存到csp
//     * @param avatar
//     * @return
//     */
//    private String saveAvatarFromThirdPlatform(String avatar) {
//        //将头像保存到csp
//        String relativePath = FilePath.PORTRAIT.path + File.separator;
//        String suffix = FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix;
//        String fileName = StringUtils.nowStr() + suffix;
//        File file = new File(avatar);
//        FileUtils.saveFile(uploadBase + relativePath, fileName, file);
//        return relativePath + fileName;
//    }
}
