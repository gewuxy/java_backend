package cn.medcn.user.dao;

import cn.medcn.user.model.CspUserInfo;
import com.github.abel533.mapper.Mapper;

/**
 * Created by Liuchangling on 2017/9/26.
 */
public interface CspUserInfoDAO extends Mapper<CspUserInfo> {

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
    CspUserInfo findUserByEmailOrMobile(String username);

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
