package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.user.model.AppRole;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by lixuan on 2017/5/5.
 */
@Controller
@RequestMapping(value="/custom/unit")
public class AppUnitController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value="/list")
    public String list(Pageable pageable, Model model,String keyword, Boolean authed){
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        if(authed!=null){
            pageable.getParams().put("authed", authed);
            model.addAttribute("authed", authed?"1":"0");
        }
        pageable.getParams().put("pubFlag", true);
        pageable.getParams().put("roleId", AppRole.AppRoleType.PUB_USER.getId());
        MyPage<AppUser> page = appUserService.pageUsers(pageable);
        model.addAttribute("page", page);
        return "/custom/unitList";
    }


    @RequestMapping(value="/delete")
    public String delete(Integer id, RedirectAttributes redirectAttributes){

        return "";
    }
}
