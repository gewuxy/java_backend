package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.email.CSPEmailHelper;
import cn.medcn.common.email.MailBean;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.SMSService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.*;
import cn.medcn.user.dao.PatientUserDAO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.model.Patient;
import cn.medcn.user.service.PatientUserService;
import com.github.abel533.mapper.Mapper;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/11/23.
 * 合理用药 用户
 */
@Service
public class PatientUserServiceImpl extends BaseServiceImpl<Patient> implements PatientUserService{

    @Value("${mail_username}")
    private String sender;

    @Value("${mail_password}")
    private String password;

    @Value("${mail_server_host}")
    private String serverHost;

    @Autowired
    protected PatientUserDAO patientUserDAO;

    @Autowired
    protected SMSService medSmsService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Autowired
    protected CSPEmailHelper emailHelper;

    @Autowired
    protected JavaMailSenderImpl emailSender;

    @Override
    public Mapper<Patient> getBaseMapper() {
        return patientUserDAO;
    }

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
                throw new SystemException("短信验证码错误");
            }
        } catch (Exception e) {
            throw new SystemException("短信验证码失败");
        }
    }

    @Override
    public String sendCode(String email, EmailTemplate template) throws SystemException {
        if (email == null) {
            return setMsg("registAccount","邮箱不能为空");
        }
        // 检查用户邮箱是否已经注册过
        Patient patient = new Patient();
        patient.setEmail(email);
        Patient user = patientUserDAO.selectOne(patient);
        if (user != null) {
            if (user.getActive()) {
                return setMsg("registAccount","该邮箱账户已经存在");
            }
        }
        String code = UUIDUtil.getRandom6();
        sendMail(email, code,template);
        return APIUtils.success("验证码发送成功");
    }

    public void sendMail(String email, String code,EmailTemplate template) throws SystemException {
        redisCacheUtils.setCacheObject(email,code,Constants.CAPTCHA_CACHE_EXPIRE_TIME); //15分钟有效期
        // 发送注册激活邮箱邮件
        MailBean bean = new MailBean();
        bean.setFrom(template.getSender());
        bean.setFromName(template.getSenderName());
        bean.setSubject(template.getSubject());
        bean.setLocalStr(template.getLangType());
        bean.setToEmails(new String[]{email});
        try {
            emailSender.setUsername(sender);
            emailSender.setPassword(password);
            emailSender.setHost(serverHost);
            emailHelper.sendMail(code,template.getContent(),emailSender,bean);
        } catch (JDOMException e) {
            e.printStackTrace();
            throw new SystemException("邮箱地址错误");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("发送失败，请重试");
        }
    }

    private String setMsg(String id,String msg){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("msg",msg);
        return APIUtils.error(map);
    }

}
