package cn.medcn.official.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.*;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.model.Patient;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.EmailTempService;
import cn.medcn.user.service.PatientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    protected AppUserService appUserService;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Autowired
    protected SystemRegionService systemRegionService;

    @Autowired
    private EmailTempService tempService;

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
            // 获取邮箱模板
            EmailTemplate template = tempService.getTemplate("zh_CN",EmailTemplate.Type.REGISTER.getLabelId());
            try {
                return patientUserService.sendCode(account,template);
            } catch (SystemException e){
                return error(e.getMessage());
            }
        }
    }

    /**
     * 注册
     * @param patient
     * @return
     */
    @RequestMapping(value="/do_register", method = RequestMethod.POST)
    @ResponseBody
    public String register(Patient patient) {
        String data = validateUserInfo(patient);
        if( data != null){
            return data;
        }
        String account = patient.getAccount();
        if (RegexUtils.checkMobile(account)) {
            patient.setMobile(account);
        } else {
            patient.setEmail(account);
        }
        patient.setPassword(MD5Utils.MD5Encode(patient.getPassword()));
        patient.setId(StringUtils.nowStr());
        Integer info = patientUserService.insertSelective(patient);
        if (info == null){
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

    private String setMsg(String id,String msg){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("msg",msg);
        return APIUtils.error(map);
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
        if(!RegexUtils.checkMobile(account) && !RegexUtils.checkEmail(account)){
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
            patientUserService.checkCaptchaIsOrNotValid(account, patient.getCaptcha());
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return null;
    }

}
