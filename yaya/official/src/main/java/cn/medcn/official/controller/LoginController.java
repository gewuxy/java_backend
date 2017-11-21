package cn.medcn.official.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.RegexUtils;
import cn.medcn.official.model.OffUserInfo;
import cn.medcn.official.service.OffiUserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * by create HuangHuibin 2017/11/15
 */
@Controller
public class LoginController extends BaseController{

    /**
     * 登录
     * @param account
     * @param password
     * @param rememberMe
     * @return
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(String account, String password, Boolean rememberMe){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isEmpty(account)){
            return APIUtils.error("用户名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return APIUtils.error("密码不能为空");
        }
        if(!RegexUtils.checkMobile(account) && !RegexUtils.checkEmail(account)){
            return APIUtils.error("请输入正确的手机或者邮箱");
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(account, password, rememberMe == null ? false : rememberMe));
        }catch (AuthenticationException e){
            return APIUtils.error(e.getMessage());
        }
        return success();
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(value="/logout")
    public String logout(){
        return "redirect:/";
    }
}
