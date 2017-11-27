package cn.medcn.user.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.Patient;

/**
 * Created by Liuchangling on 2017/11/23.
 * 合理用药 用户service
 */
public interface PatientUserService extends BaseService<Patient> {

    // 检查验证码是否有效
    void checkCaptchaIsOrNotValid(String mobile, String captcha) throws SystemException;
}
