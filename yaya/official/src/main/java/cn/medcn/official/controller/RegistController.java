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
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.model.AppRole;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * by create HuangHuibin 2017/11/15
 */
@Controller
@RequestMapping(value="/regist")
public class RegistController extends BaseController{

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
     * 生成短信验证码给前端
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

    private String sendEmail(String account){
        String code = UUIDUtil.getUUID();
        account += code;
        redisCacheUtils.setCacheObject(Constants.EMAIL_LINK_PREFIX_KEY+code, account, (int) TimeUnit.DAYS.toSeconds(Constants.NUMBER_ONE));//缓存24小时
        MailBean mailBean = new MailBean();
        mailBean.setSubject("注册");
        try {
            emailHelper.sendMailByType(mailBean, account, account,0);
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

    @RequestMapping(value="/do_register", method = RequestMethod.POST)
    @ResponseBody
    public String register(String mobile,String captcha,String password,String province,String city) {
        String data = checkData(mobile,captcha,password);
        if( data != null){
            return data;
        }
        OffUserInfo user = new OffUserInfo();
        user.setMobile(mobile);
        if(offiUserInfoService.selectOne(user) != null){
            return setMsg("mobile","该手机号码已注册");
        }
        Captcha captcha1 = (Captcha)redisCacheUtils.getCacheObject(mobile);
        try {
            if(!jSmsService.verify(captcha1.getMsgId(),captcha)){
                return setMsg("captcha","验证码不正确");
            }
        } catch (Exception e) {
            return setMsg("captcha","验证码已被校验，请重新获取验证码");
        }
        user.setPassword(MD5Utils.MD5Encode(password));
        user.setAccount(mobile);
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


    private String checkData(String phone,String captcha,String password){
        if(StringUtils.isEmpty(phone)){
            return setMsg("registaccount","手机号码不能为空");
        }
        if(!RegexUtils.checkMobile(phone)){
            return setMsg("registaccount","手机号码格式不对");
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


}
