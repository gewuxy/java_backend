package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.ValidateCodeUtils;
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
import java.io.IOException;

/**
 * Created by lixuan on 2017/4/18.
 */
@Controller
public class LoginController extends BaseController{

    @RequestMapping(value="/jcms", method = RequestMethod.GET)
    public String jcms(){
        return "/login";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(){
        return "/login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(String username, String password,String validateCode, Boolean rememberMe,HttpServletRequest request, Model model) {
        if(StringUtils.isEmpty(username)){
            model.addAttribute(messageKey, "用户名不能为空");
            return "/login";
        }
        if(StringUtils.isEmpty(password)){
            model.addAttribute(messageKey, "密码不能为空");
            return "/login";
        }
//        String sessionCode = (String) request.getSession().getAttribute(ValidateCodeUtils.VALIDATE_CODE);
//        if(StringUtils.isEmpty(sessionCode)){
//            model.addAttribute(messageKey, "验证码已过期");
//            return "/login";
//        }
//        if(StringUtils.isEmpty(validateCode)){
//            model.addAttribute(messageKey, "验证码不能为空");
//            return "/login";
//        }
//        if(!validateCode.toLowerCase().equals(sessionCode.toLowerCase())){
//            model.addAttribute(messageKey, "验证码不正确");
//            return "/login";
//        }
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(username, password, rememberMe == null ? false : rememberMe));
        }catch (AuthenticationException e){
            model.addAttribute(messageKey, e.getMessage());
            return "/login";
        }
        return "redirect:/home/";
    }

    @RequestMapping(value = "/validateCode")
    @ResponseBody
    public String validateCode(HttpServletRequest request, HttpServletResponse response){
        try {
            ValidateCodeUtils.createImage(request, response);
        } catch (IOException e) {
            return APIUtils.error("生成验证码错误");
        }
        return null;
    }


}
