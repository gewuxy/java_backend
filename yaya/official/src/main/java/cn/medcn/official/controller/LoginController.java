package cn.medcn.official.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.official.model.OffUserInfo;
import cn.medcn.official.service.OffiUserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * by create HuangHuibin 2017/11/15
 */
@Controller
public class LoginController extends BaseController{

    @Autowired
    private OffiUserInfoService offiUserInfoService;


    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(String account, String password, Boolean rememberMe, HttpServletRequest request, HttpServletResponse response, Model model){
        if(StringUtils.isEmpty(account)){
            model.addAttribute("type",1);
            model.addAttribute(messageKey, "用户名不能为空");
            return "index";
        }
        if(StringUtils.isEmpty(password)){
            model.addAttribute("type",1);
            model.addAttribute(messageKey, "密码不能为空");
            return "index";
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(account, password, rememberMe == null ? false : rememberMe));
        }catch (AuthenticationException e){
            model.addAttribute("type",1);
            model.addAttribute(messageKey,e.getMessage());
            return "index";
        }
        return "redirect:/user/userInfo";
    }
}
