package cn.medcn.api.controller;

import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.email.EmailHelper;
import cn.medcn.common.email.MailBean;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.utils.*;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** 通过邮箱重置密码，绑定邮箱账号
 * Created by LiuLP on 2017/7/27/027.
 */
@Controller
@RequestMapping("/api/email")
public class EmailController extends BaseController{

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private EmailHelper emailHelper;

    @Autowired
    private JPushService jPushService;

    @Value("${app.yaya.base}")
    private String appBaseUrl;


    /**
     * 发送重置密码邮件
     * @param username
     * @return
     */
    @RequestMapping("/pwd/send_reset_mail")
    @ResponseBody
    public String resetPwd(String username){
        if(!RegexUtils.checkEmail(username)){
            return error("邮箱格式不正确");
        }
        AppUser condition = new AppUser();
        condition.setUsername(username);
        AppUser user = appUserService.selectOne(condition);
            if(user == null){
                return error("该账号未注册");
            }
        Integer type = Constants.NUMBER_ZERO;
        String url = appBaseUrl + "api/email/pwd/link?type=0" +"&code=";
        sendEmail(username,url,type);
        return success();
    }

    /**
     * 发送绑定邮件
     * @param username
     * @param
     * @return
     */
    @RequestMapping("/send_bind_email")
    @ResponseBody
    public String sendMail(String username){
        if(!RegexUtils.checkEmail(username)){
            return error("邮箱格式不正确");
        }
        AppUser condition = new AppUser();
        condition.setUsername(username);
        AppUser user = appUserService.selectOne(condition);
        if(user != null){
            return error("该邮箱已被绑定");
        }
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();

        Integer type = Constants.NUMBER_ONE;
        String url = appBaseUrl + "/api/email/pwd/link?type=1" +"&code=";
        username = username +"," + userId;
        sendEmail(username,url,type);

        return success();
    }


    private String sendEmail(String username,String url,Integer type){
        String code = UUIDUtil.getUUID();
        url += code;
        redisCacheUtils.setCacheObject(Constants.EMAIL_LINK_PREFIX_KEY+code, username, (int)TimeUnit.DAYS.toSeconds(Constants.NUMBER_ONE));//缓存24小时
        MailBean mailBean = new MailBean();
        if( type == Constants.NUMBER_ZERO){
            mailBean.setSubject("密码重置");
        }else if(type == Constants.NUMBER_ONE){
            mailBean.setSubject("邮箱绑定");
        }else{
            return error("type错误");
        }
        if(type == Constants.NUMBER_ONE){
            username = username.substring(0,username.indexOf(","));
        }
        try {
            emailHelper.sendMailByType(mailBean, username, url,type);
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
     *激活链接
     * @param type
     * @param code
     * @param model
     * @return
     */
    @RequestMapping("/pwd/link")
    public String activateLink(Integer type,String code,Model model) throws SystemException {
        String key = Constants.EMAIL_LINK_PREFIX_KEY + code;
        String result = (String) redisCacheUtils.getCacheObject(key);
        if(StringUtils.isEmpty(result)){
            return "/register/linkTimeOut";
        }else if(type == Constants.NUMBER_ZERO){ //密码重置
            model.addAttribute("code", code);
            return "/register/pwdReset";
        }else if(type == Constants.NUMBER_ONE){  //邮箱绑定
            String username = result.substring(0,result.indexOf(","));
            String userId = result.substring(result.indexOf(",")+1);
            AppUser user = appUserService.selectByPrimaryKey(Integer.parseInt(userId));
            if(user == null){
                throw new SystemException("用户不存在");
            }
            user.setUsername(username);
            appUserService.updateByPrimaryKeySelective(user);
            redisCacheUtils.delete(key);

            //发送推送通知邮箱已绑定
            jPushService.sendChangeMessage(user.getId(),"3",username);
            return "/register/bindOk";
        }else{
            throw new SystemException("链接不正确");
        }

    }

    @RequestMapping(value = "/pwd/toReset")
    public String toReset(String code, Model model){
        String resetKey = Constants.EMAIL_LINK_PREFIX_KEY+code;
        String username = (String) redisCacheUtils.getCacheObject(resetKey);
        if(StringUtils.isEmpty(username)){
            return "/register/linkTimeOut";
        }
        model.addAttribute("code", code);
        return "/register/pwdReset";
    }


    @RequestMapping(value = "/pwd/doReset", method = RequestMethod.POST)
    public String doReset(String code, String password, Model model) throws SystemException {
        if(StringUtils.isEmpty(password)){
            model.addAttribute("message", "密码不能为空");
            return "/register/pwdReset";
        }
        String resetPwdKey = Constants.EMAIL_LINK_PREFIX_KEY+code;
        String username = (String) redisCacheUtils.getCacheObject(resetPwdKey);
        if(StringUtils.isEmpty(username)){
            return "/register/linkTimeOut";
        }
        AppUser condition = new AppUser();
        condition.setUsername(username);
        AppUser user = appUserService.selectOne(condition);
        if(user != null){
            user.setPassword(MD5Utils.MD5Encode(password));
            appUserService.updateByPrimaryKeySelective(user);
            redisCacheUtils.delete(resetPwdKey);
        }
        return "/register/pwdOk";
    }
}
