package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.user.model.AppMenu;
import cn.medcn.user.service.AppMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

/**
 * Created by lixuan on 2017/5/8.
 */
@Controller
@RequestMapping(value="/custom/menu")
public class AppMenuController extends BaseController {

    @Autowired
    private AppMenuService appMenuService;


    @RequestMapping(value="/list")
    public String list(Model model){
        List<AppMenu> list = appMenuService.findAll();
        //Collections.sort(list);
        model.addAttribute("list", list);
        return "/custom/menuList";
    }

    @RequestMapping(value="/edit")
    public String edit(Integer id, Model model){
        if(id != null){
            AppMenu menu  = appMenuService.selectByPrimaryKey(id);
            model.addAttribute("menu", menu);
            if(menu.getPreid() != null && menu.getPreid() != 0){
                AppMenu parent = appMenuService.selectByPrimaryKey(menu.getPreid());
                model.addAttribute("parent", parent);
            }
        }
        return "/custom/menuForm";
    }


    @RequestMapping(value="/save")
    public String save(AppMenu menu, RedirectAttributes redirectAttributes){
        if(menu.getId() == null){
            appMenuService.insert(menu);
        }else{
            appMenuService.updateByPrimaryKeySelective(menu);
        }
        addFlashMessage(redirectAttributes, (menu.getId() == null?"增加":"修改")+"用户菜单成功");
        return "redirect:/custom/menu/list";
    }
}
