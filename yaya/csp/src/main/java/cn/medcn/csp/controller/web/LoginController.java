package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.user.model.BindInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lixuan on 2017/10/16.
 */
@Controller
@RequestMapping(value = "/mgr")
public class LoginController extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Integer thirdPartyId){
        if (thirdPartyId == null || thirdPartyId == 0) {
            return localeView("/login/login");
        } else {
            return localeView("/login/login_"+thirdPartyId);
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, Integer thirdPartyId, Model model){
        //默认为邮箱密码登录
        if (thirdPartyId == null) {
            thirdPartyId = BindInfo.Type.EMAIL.getTypeId();
        }

        if (thirdPartyId == BindInfo.Type.EMAIL.getTypeId()) {
            return loginByEmail(username, password, model);
        }
        return "";
    }

    /**
     * 邮箱登录
     * @param username
     * @param password
     * @param model
     * @return
     */
    protected String loginByEmail(String username, String password, Model model){
        String errorForwardUrl = localeView("/login/login_" + BindInfo.Type.EMAIL.getTypeId());
        if (CheckUtils.isEmpty(username)) {
            model.addAttribute("error", local("user.empty.username"));
            model.addAttribute("username", username);
            return errorForwardUrl;
        }

        if (CheckUtils.isEmpty(password)) {
            model.addAttribute("error", local("user.empty.password"));
            model.addAttribute("username", username);
            return errorForwardUrl;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username);
            return errorForwardUrl;
        }
        return "redirect:/web/meet/list";
    }

}
