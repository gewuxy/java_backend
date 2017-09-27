package cn.medcn.user.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.CspUserInfoDTO;
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
    Boolean checkCaptchaIsOrNotValid(String mobile, String captcha) throws SystemException;

    /**
     * 添加第三方平台用户及绑定用户信息
     * @param userDTO
     * @return
     */
    CspUserInfo saveThirdPartyUserInfo(CspUserInfoDTO userDTO);
}
