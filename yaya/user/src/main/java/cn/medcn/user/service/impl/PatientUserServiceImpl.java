package cn.medcn.user.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.SMSService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.RegexUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.user.dao.BindInfoDAO;
import cn.medcn.user.dao.PatientUserDAO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.dto.PatientDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.Patient;
import cn.medcn.user.service.PatientUserService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Created by Liuchangling on 2017/11/23.
 * 合理用药 用户
 */
@Service
public class PatientUserServiceImpl extends BaseServiceImpl<Patient> implements PatientUserService{
    @Autowired
    protected PatientUserDAO patientUserDAO;

    @Autowired
    protected SMSService medSmsService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Override
    public Mapper<Patient> getBaseMapper() {
        return patientUserDAO;
    }

    @Autowired
    protected BindInfoDAO bindInfoDAO;

    /**
     * 检查验证码合法性
     * @param account
     * @param captcha
     */
    public void checkCaptchaIsOrNotValid(String account, String captcha) throws SystemException{
        // 从缓存获取验证码信息
        Captcha result = (Captcha) redisCacheUtils.getCacheObject(account);
        try {
            Boolean bool = true;
            if(RegexUtils.checkMobile(account)){
                 bool = medSmsService.verify(result.getMsgId(),captcha);
            }else{
                bool = result.getMsgId().equals(captcha);
            }
            if (!bool) {
                throw new SystemException("sms.error.captcha");
            }
        } catch (Exception e) {
            throw new SystemException("sms.invalid.captcha");
        }
    }

    @Override
    public Patient findBindUserByUniqueId(String uniqueId) {
        return patientUserDAO.findBindUserByUniqueId(uniqueId);
    }

    @Override
    public Patient saveThirdPartyUserInfo(PatientDTO dto) {
        // 将第三方用户信息 保存到用户表
        Patient userInfo = Patient.buildToUserInfo(dto);
        patientUserDAO.insertSelective(userInfo);
        // 添加绑定第三方平台用户信息
        BindInfo bind = BindInfo.buildToBindInfo(dto);
        bindInfoDAO.insert(bind);
        return userInfo;
    }

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
        info.setId(UUIDUtil.getNowStringID());
        info.setUserId(userId);
        info.setBindDate(new Date());
        bindInfoDAO.insert(info);
    }
}
