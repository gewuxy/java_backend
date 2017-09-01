package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.jbms.security.Principal;
import cn.medcn.jbms.utils.SubjectUtils;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SystemUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lixuan on 2017/5/3.
 */
@Controller
@RequestMapping(value="/sys/user")
public class UserController extends BaseController {

    @Autowired
    private SystemUserService systemUserService;

    @RequestMapping(value="/list")
    public String list(){
        return "";
    }

    @RequestMapping(value="/info")
    public String info(Model model) throws SystemException{
        Integer userid = SubjectUtils.getCurrentUserid();
        SystemUser user = systemUserService.findUserHasRole(userid);
        model.addAttribute("user", user);
        return "/sys/userInfo";
    }
}
