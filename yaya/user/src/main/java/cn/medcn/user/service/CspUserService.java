package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspUserInfo;

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
    void register(CspUserInfo userInfo);

    /**
     * 发送手机验证码
     * @param mobile
     * @param type
     */
    void sendCaptcha(String mobile, Integer type);

    /**
     * 检查验证码是否有效
     * @param captcha
     * @param mobile
     */
    void checkCaptchaIsOrNotValid(String captcha, String mobile);
}
