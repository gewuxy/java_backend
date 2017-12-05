package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.ValidateCodeUtils;
import cn.medcn.csp.admin.log.Log;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
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

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index(){

        return "redirect:/login";
    }


    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(){
        return "/login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    @Log(name="用户登录")
    public String login(String username, String password,String validateCode, Boolean rememberMe, HttpServletRequest request, HttpServletResponse response, Model model){
        if(StringUtils.isEmpty(username)){
            model.addAttribute("message", "用户名不能为空");
            return "/login";
        }
        if(StringUtils.isEmpty(password)){
            model.addAttribute("message", "密码不能为空");
            return "/login";
        }
        String sessionCode = (String) request.getSession().getAttribute(ValidateCodeUtils.VALIDATE_CODE);
        if(sessionCode == null){
            model.addAttribute(messageKey, "验证码不能为空");
            return "/login";
        }
        if(!validateCode.toLowerCase().equals(sessionCode.toLowerCase())){
            model.addAttribute(messageKey, "验证码错误");
            return "/login";
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe == null ? false : rememberMe);
            token.setHost(request.getRemoteHost());
            SecurityUtils.getSubject().login(token);
        }catch (AuthenticationException e){
            model.addAttribute("message", e.getMessage());
            e.printStackTrace();
            return "/login";
        }
        return "redirect:/home/";
    }


    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest request){
        return "redirect:/";
    }


    @RequestMapping(value = "/validateCode")
    @ResponseBody
    public String freshCode(HttpServletRequest request, HttpServletResponse response){
        try {
            ValidateCodeUtils.createImage(request, response);
        } catch (IOException e) {
            return error("生成验证码错误");
        }
        return null;
    }
}
