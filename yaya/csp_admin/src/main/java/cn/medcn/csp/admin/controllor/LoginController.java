package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * by create HuangHuibin 2017/11/6
 */
@Controller
public class LoginController extends BaseController{

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(){
        return "/login";
    }

    @RequestMapping(value="/index")
    public String inedx(){
        return "/index";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(String account, String password, HttpServletRequest request, HttpServletResponse response, Model model){
        if(StringUtils.isEmpty(account)){
            model.addAttribute(messageKey, "用户名不能为空");
            return "/login";
        }
        if(StringUtils.isEmpty(password)){
            model.addAttribute(messageKey, "密码不能为空");
            return "/login";
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            token.setHost(request.getRemoteHost());
            SecurityUtils.getSubject().login(token);
        }catch (AuthenticationException e){
            model.addAttribute(messageKey, e.getMessage());
            e.printStackTrace();
            return "/login";
        }
        return "redirect:/index";
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(value="/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

}
