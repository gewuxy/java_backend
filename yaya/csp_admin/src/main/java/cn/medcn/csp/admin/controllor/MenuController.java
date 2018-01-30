package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.csp.admin.security.Principal;
import cn.medcn.csp.admin.utils.SubjectUtils;
import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
@Controller
@RequestMapping(value="/sys/menu")
public class MenuController extends BaseController{

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping(value="/tree/{preid}")
    public String tree(@PathVariable Integer preid, Model model) throws SystemException {
        if(preid == null){
            preid = 1;
        }
        SystemMenu menu = new SystemMenu();
        menu.setPreid(preid);
        SystemMenu parent = sysMenuService.selectByPrimaryKey(preid);
        List<SystemMenu> menuList =  null;
        Principal principal = SubjectUtils.getCurrentUser();
        if (principal.isAdmin()){
            menuList = sysMenuService.findAllSubMenus(preid);
        }else{
            menuList = sysMenuService.findSubMenusByRole(principal.getRoleId(), preid);
        }
        menuList.add(0, parent);
        model.addAttribute("menuList", menuList);
        model.addAttribute("preid", preid);
        return "/sys/menuTree";
    }


    @RequestMapping(value="/list")
    public String list(Model model) throws SystemException {
        List<SystemMenu> list = sysMenuService.findRootMenus();
        model.addAttribute("list", list);
        return "/sys/menuList";
    }


    @RequestMapping(value="/treeData")
    public String treeData(Integer preid, Model model) throws SystemException {
        List<SystemMenu> menuList = sysMenuService.findRootMenus();
        model.addAttribute("menuList", menuList);
        model.addAttribute("preid", preid);
        return "/sys/treeData";
    }

    @RequiresPermissions({"sys:menu:edit"})
    @RequestMapping(value = "/edit")
    @Log(name="菜单编辑")
    public String edit(Integer id, Integer preid, Model model){
        if(id != null){
            SystemMenu menu = sysMenuService.selectByPrimaryKey(id);
            model.addAttribute("menu", menu);
            if(menu.getPreid() != null && menu.getPreid() != 0){
                preid = menu.getPreid();
            }
        }
        if(preid!=null && preid !=0){
            SystemMenu parent = sysMenuService.selectByPrimaryKey(preid);
            model.addAttribute("parent", parent);
        }
        return "/sys/menuForm";
    }

    @RequiresPermissions({"sys:menu:edit"})
    @RequestMapping(value="/save")
    @Log(name="添加或更新菜单")
    public String save(SystemMenu menu, RedirectAttributes redirectAttributes){
        String action = menu.getId() == null?"添加菜单":"更新菜单";
        if(menu.getHide() == null){
            menu.setHide(false);
        }
        if (menu.getPreid() == null) {
            menu.setPreid(0);
        }
        if(menu.getId() == null){
            sysMenuService.insert(menu);
        }else{
            sysMenuService.updateByPrimaryKeySelective(menu);
        }
        addFlashMessage(redirectAttributes,action+"成功");
        return "redirect:/sys/menu/list";
    }

    @RequiresPermissions({"sys:menu:del"})
    @RequestMapping(value="/delete")
    @Log(name="删除菜单")
    public String delete(Integer id, RedirectAttributes redirectAttributes) throws SystemException{
        if(id == null){
            throw new SystemException("不能删除空菜单");
        }
        sysMenuService.deleteMenu(id);
        addFlashMessage(redirectAttributes, "删除菜单及子菜单成功");
        return "redirect:/sys/menu/list";
    }
}
