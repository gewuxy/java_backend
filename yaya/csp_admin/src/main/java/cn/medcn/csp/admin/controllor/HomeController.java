package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.csp.admin.security.Principal;
import cn.medcn.csp.admin.utils.SubjectUtils;
import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
@RequestMapping(value="/home")
@Controller
public class HomeController extends BaseController{

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping(value="/")
    public String index(Model model) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        List<SystemMenu> menuList = null;
        if(principal.isAdmin()){
            menuList = sysMenuService.findRootMenus();
        }else{
            menuList = sysMenuService.findMenusByRole(principal.getRoleId());
        }
        model.addAttribute("menuList", menuList);
        return "/index";
    }
}
