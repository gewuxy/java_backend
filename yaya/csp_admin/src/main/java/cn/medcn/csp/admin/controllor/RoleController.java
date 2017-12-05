package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.model.SystemRole;
import cn.medcn.sys.model.SystemRoleMenu;
import cn.medcn.sys.service.SysMenuService;
import cn.medcn.sys.service.SystemRoleService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghuibin on 2017/11/23.
 */
@Controller
@RequestMapping(value="/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping(value="/list")
    @Log(name="查看角色列表")
    public String list(Model model){
        List<SystemRole> roleList = systemRoleService.select(new SystemRole());
        model.addAttribute("roleList", roleList);
        return "/sys/roleList";
    }

    @RequestMapping(value="/edit")
    @Log(name="编辑角色")
    public String eidt(Integer id, Model model){
        if(id != null){
            SystemRole role = systemRoleService.selectByPrimaryKey(id);
            model.addAttribute("role", role);
        }
        return "/sys/roleForm";
    }

    @RequestMapping(value="/add")
    @Log(name="添加或更新角色")
    public String eidt(SystemRole role, RedirectAttributes redirectAttributes){
        Integer id = role.getId();
        if(id == null){   // 编辑
            systemRoleService.insertSelective(role);
        }else{    //新增
            systemRoleService.updateByPrimaryKeySelective(role);
        }
        addFlashMessage(redirectAttributes, id == null ? "新增成功":"更新成功");
        return "redirect:/sys/role/list";
    }

    @RequestMapping(value="/delete")
    @Log(name="删除角色")
    public String delete(Integer id, RedirectAttributes redirectAttributes){
        //先删除该角色的所有权限
        SystemRoleMenu condition = new SystemRoleMenu();
        condition.setRoleId(id);
        systemRoleService.deleteMenuRole(condition);
        //删除角色
        systemRoleService.deleteByPrimaryKey(id);
        addFlashMessage(redirectAttributes, "删除成功");
        return "redirect:/sys/role/list";
    }

    @RequestMapping(value="assign", method = RequestMethod.GET)
    @Log(name="跳转权限分配")
    public String assign(Integer id, Model model) throws SystemException{
        if(id == null){
            throw new SystemException("角色不存在");
        }
        SystemRole role = systemRoleService.selectByPrimaryKey(id);
        if(role == null){
            throw new SystemException("角色不存在");
        }
        List<SystemMenu> rootMenuList = sysMenuService.findRootMenus();
        List<SystemMenu> menuList = sysMenuService.findMenusByRole(id);
        Map<Integer,Boolean> roleMap = Maps.newHashMap();
        for(SystemMenu menu:menuList){
            roleMap.put(menu.getId(),true);
        }
        model.addAttribute("roleId", id);
        model.addAttribute("menuList", rootMenuList);
        model.addAttribute("roleMap", roleMap);
        return "/sys/roleAssign";
    }

    @RequestMapping(value="/assign", method = RequestMethod.POST)
    @Log(name="角色权限分配")
    public String assign(Integer[] menuIds, Integer roleId, RedirectAttributes redirectAttributes) throws SystemException{
        if(roleId == null || menuIds==null){
            throw new SystemException("操作失败,数据有误");
        }
        systemRoleService.insertRoleMenu(roleId, menuIds);
        addFlashMessage(redirectAttributes, "分配角色权限成功");
        return "redirect:/sys/role/list";
    }
}
