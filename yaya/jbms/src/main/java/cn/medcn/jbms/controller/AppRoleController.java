package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.user.model.AppRole;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lixuan on 2017/5/8.
 */
@Controller
@RequestMapping(value="/custom/role")
public class AppRoleController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value="/list")
    public String list(Model model){
        List<AppRole> list = appUserService.findAppRoles();
        model.addAttribute("list", list);
        return "/custom/roleList";
    }
}
