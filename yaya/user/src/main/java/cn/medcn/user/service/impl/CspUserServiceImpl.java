package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.*;
import cn.medcn.user.dao.BindInfoDAO;
import cn.medcn.user.dao.CspUserInfoDAO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static cn.medcn.common.Constants.BIND_TEMPLATE_ID;
import static cn.medcn.common.Constants.LOGIN_TEMPLATE_ID;

/**
 * Created by Liuchangling on 2017/9/26.
 */
@Service
public class CspUserServiceImpl extends BaseServiceImpl<CspUserInfo> implements CspUserService {

    @Value("${app.file.base}")
    protected String appFileBase;

    @Autowired
    protected CspUserInfoDAO cspUserInfoDAO;

    @Autowired
    protected BindInfoDAO bindInfoDAO;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Autowired
    protected JSmsService jSmsService;

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
     * 将第三方平台和YaYa医师平台获取的头像保存到csp
     * @param avatar
     * @return
     */
    private String saveAvatarFromThirdPlatform(String avatar) {
        //将头像保存到csp
        String relativePath = FilePath.PORTRAIT.path + File.separator;
        String fileName = StringUtils.nowStr();
        File file = new File(avatar);
        FileUtils.saveFile(appFileBase + relativePath, fileName, file);
        return relativePath + fileName;
    }
}
