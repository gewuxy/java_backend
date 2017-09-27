package cn.medcn.user.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import org.jdom.JDOMException;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by Liuchangling on 2017/9/26.
 */
public interface CspUserService extends BaseService<CspUserInfo>{

    /**
     * 根据uniqueId 查询用户是否存在
     * @param uniqueId
     * @return
     */
    CspUserInfo findBindUserByUniqueId(String uniqueId);

    /**
     * 根据邮箱或者手机号码检查csp账号 是否存在
     * @param username
     * @return
     */
    CspUserInfo findByLoginName(String username);

    /**
     * 注册用户
     * @param userInfo
     */
    void register(CspUserInfo userInfo)throws SystemException;

    /**
     * 发送手机验证码
     * @param mobile
     * @param type
     */
    String sendCaptcha(String mobile, Integer type) throws SystemException ;

    /**
     * 检查验证码是否有效
     * @param captcha
     * @param mobile
     */
    void checkCaptchaIsOrNotValid(String mobile, String captcha);

    /**
     * 添加第三方平台用户及绑定用户信息
     * @param userDTO
     * @return
     */
    CspUserInfo saveThirdPartyUserInfo(CspUserInfoDTO userDTO);

    /**
     * 缓存信息和发送绑定邮件
     * @param email
     * @param userId
     */
    void cacheInfoAndSendMail(String email, String userId) throws JDOMException, MessagingException, IOException;

    /**
     * 绑定手机号
     * @param mobile
     * @param captcha
     * @param userId
     */
    String bindMobile(String mobile, String captcha, String userId);

    /**
     * 解绑邮箱或手机
     * @param type
     * @param userId
     * @return
     */
    String unbindEmailOrMobile(Integer type, String userId);

    /**
     * 绑定第三方账号
     * @param info
     * @param userId
     * @return
     */
    String bindThirdAccount(BindInfo info, String userId);

    /**
     * 解绑第三方账号
     * @param info
     * @param userId
     * @return
     */
    String unbindThirdAccount(BindInfo info, String userId);
}
