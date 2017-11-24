package cn.medcn.user.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.SMSService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.user.dao.PatientUserDAO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.model.Patient;
import cn.medcn.user.service.PatientUserService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.medcn.common.Constants.CSP_MOBILE_CACHE_PREFIX_KEY;

/**
 * Created by Liuchangling on 2017/11/23.
 * 合理用药 用户
 */
@Service
public class PatientUserServiceImpl extends BaseServiceImpl<Patient> implements PatientUserService{
    @Autowired
    protected PatientUserDAO patientUserDAO;

  //  protected SMSService medSmsService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Override
    public Mapper<Patient> getBaseMapper() {
        return patientUserDAO;
    }

    /**
     * 检查验证码合法性
     * @param mobile
     * @param captcha
     */
   /* public void checkCaptchaIsOrNotValid(String mobile, String captcha) throws SystemException{
        // 从缓存获取此号码的短信记录
        Captcha result = (Captcha) redisCacheUtils.getCacheObject(CSP_MOBILE_CACHE_PREFIX_KEY + mobile);
        try {

            Boolean bool = medSmsService.verify(result.getMsgId(),captcha);
            if (!bool) {
                throw new SystemException("sms.error.captcha");
            }

        } catch (Exception e) {
            throw new SystemException("sms.invalid.captcha");
        }
    }*/
}
