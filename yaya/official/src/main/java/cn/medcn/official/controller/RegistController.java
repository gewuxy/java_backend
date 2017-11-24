package cn.medcn.official.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.email.EmailHelper;
import cn.medcn.common.email.MailBean;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.utils.*;
import cn.medcn.official.model.OffUserInfo;
import cn.medcn.official.service.OffiUserInfoService;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.model.Patient;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.PatientUserService;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * by create HuangHuibin 2017/11/15
 */
@Controller
@RequestMapping(value="/regist")
public class RegistController extends BaseController{
    @Autowired
    protected PatientUserService patientUserService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private OffiUserInfoService offiUserInfoService;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private JSmsService jSmsService;

    @Autowired
    private SystemRegionService systemRegionService;

    @Autowired
    private EmailHelper emailHelper;

    /**
     * 发送验证码到用户手机或者邮箱
     * @return
     */
    @RequestMapping("/get_captcha")
    @ResponseBody
    public String getCode(String account,int type){
        if(type == 1){
            return appUserService.sendCaptcha(account);
        }else{
            return sendEmail(account);
        }

    }

    /**
     * 发送邮件
     * @param account
     * @return
     */
    private String sendEmail(String account){
        String code = UUIDUtil.getRandom6();  //生成验证码
        Captcha captcha = new Captcha();
        captcha.setFirstTime(new Date());
        captcha.setCount(Constants.NUMBER_ONE);
        captcha.setMsgId(code);
        redisCacheUtils.setCacheObject(account,captcha,Constants.CAPTCHA_CACHE_EXPIRE_TIME); //15分钟有效期
        MailBean mailBean = new MailBean();
        mailBean.setSubject("注册");
        try {
            emailHelper.sendMailByType(mailBean, account, code,2);
        } catch (IOException e) {
            e.printStackTrace();
            return error("邮件发送失败,可能是邮箱地址不可用");
        } catch (MessagingException e) {
            e.printStackTrace();
            return error("邮件发送失败");
        } catch (JDOMException e) {
            e.printStackTrace();
            return error("邮件模板解析错误");
        }
        return success();
    }

    /**
     * 注册
     * @param account
     * @param captcha
     * @param password
     * @param province
     * @param city
     * @return
     */
    @RequestMapping(value="/do_register", method = RequestMethod.POST)
    @ResponseBody
    public String register(String account,String captcha,String password,String province,String city) {
        String data = checkData(account,captcha,password);
        if( data != null){
            return data;
        }
        OffUserInfo userForMobile = new OffUserInfo();
        userForMobile.setMobile(account);
        if(offiUserInfoService.selectOne(userForMobile) != null){
            return setMsg("registAccount","该手机号码已注册");
        }
        OffUserInfo userForEmail = new OffUserInfo();
        userForEmail.setEmail(account);
        if(offiUserInfoService.selectOne(userForEmail) != null){
            return setMsg("registAccount","该邮箱已注册");
        }
        Captcha captcha1 = (Captcha)redisCacheUtils.getCacheObject(account);
        try {
            if(RegexUtils.checkMobile(account)){
                if(!jSmsService.verify(captcha1.getMsgId(),captcha)) return setMsg("captcha","验证码不正确");
            }else{
                if(!captcha1.getMsgId().equals(captcha))  return setMsg("captcha","验证码不正确");
            }
        } catch (Exception e) {
            return setMsg("captcha","验证码已被校验，请重新获取验证码");
        }
        OffUserInfo user = new OffUserInfo();
        if (RegexUtils.checkMobile(account)) {
            user.setMobile(account);
        } else {
            user.setEmail(account);
        }
        user.setPassword(MD5Utils.MD5Encode(password));
        Integer info = offiUserInfoService.insertSelective(user);
        if (info != null){
            return setMsg("mobile","注册失败，请重试");
        }
        return success();
    }

    /**
     * 根据上级名称获取到子级列表
     * @param name
     * @return
     */
    @RequestMapping(value="/searchOption")
    @ResponseBody
    public String searchOption(String name){
        if(name == null){
            return APIUtils.success();
        }
        List<SystemRegion> options = systemRegionService.findRegionByPreName(name);
        return APIUtils.success(options);
    }

    /**
     * 获取到省份列表
     * @return
     */
    @RequestMapping(value="/getProvince")
    @ResponseBody
    public String getProvince(){
        Pageable pageable = new Pageable();
        pageable.setPageSize(50);
        pageable.put("level",1);
        MyPage<SystemRegion> page = systemRegionService.findByPage(pageable);
        return APIUtils.success(page);
    }


    private String checkData(String account,String captcha,String password){
        if(StringUtils.isEmpty(account)){
            return setMsg("registAccount","手机或者邮箱不能为空");
        }
        if(!RegexUtils.checkMobile(account) && !RegexUtils.checkEmail(account)){
            return setMsg("registAccount","手机或者邮箱格式不对");
        }
        if(StringUtils.isEmpty(captcha)){
            return setMsg("captcha","验证码不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return setMsg("registPassword","密码不能为空");
        }
        return null;
    }

    private String setMsg(String id,String msg){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("msg",msg);
        return APIUtils.error(map);
    }



    /**
     * 注册账号
     * @param patient
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public String doRegister(Patient patient){
        String data = validateUserInfo(patient);
        if( data != null){
            return data;
        }

        patient.setPassword(MD5Utils.MD5Encode(patient.getPassword()));
        String account = patient.getAccount();
        // 判断账号是邮箱还是手机
        if(RegexUtils.checkEmail(account)){
            patient.setEmail(account);
        } else if (RegexUtils.checkMobile(account)){
            patient.setMobile(account);
        }
        patientUserService.insert(patient);

        return success();
    }

    /**
     * 验证用户信息
     * @param patient
     * @return
     */
    private String validateUserInfo(Patient patient) {
        String account = patient.getAccount();
        if(StringUtils.isEmpty(account)){
            return setMsg("registAccount","手机或者邮箱不能为空");
        }
        if(!RegexUtils.checkMobile(account) || !RegexUtils.checkEmail(account)){
            return setMsg("registAccount","手机或者邮箱格式不对");
        }
        if(StringUtils.isEmpty(patient.getCaptcha())){
            return setMsg("captcha","验证码不能为空");
        }
        if(StringUtils.isEmpty(patient.getPassword())){
            return setMsg("registPassword","密码不能为空");
        }

        // 检查用户是否已经注册
        Patient mobileUser = new Patient();
        mobileUser.setMobile(account);
        Patient user = patientUserService.selectOne(mobileUser);
        if (user != null) {
            return setMsg("registAccount","该手机号码已注册");
        }

        Patient emailUser = new Patient();
        emailUser.setEmail(account);
        user = patientUserService.selectOne(emailUser);
        if (user != null) {
            return setMsg("registAccount","该邮箱已注册");
        }

        // 检查验证码是否有效
        try {
            patientUserService.checkCaptchaIsOrNotValid(patient.getMobile(), patient.getCaptcha());

        } catch (Exception e) {
            return error(e.getMessage());
        }

        return null;
    }

}
