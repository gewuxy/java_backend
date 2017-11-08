package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.csp.admin.service.CspSysUserService;
import cn.medcn.csp.admin.model.CspSysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * by create HuangHuibin 2017/11/3
 */

@Controller
@RequestMapping(value="/csp/user")
public class CspUserController extends BaseController {

   @Autowired
   private CspSysUserService cspSysUserService;

    @RequestMapping(value = "/list")
    public String cspSysUserSearch(Pageable pageable, String account, Model model) {
        if (!StringUtils.isEmpty(account)) {
            pageable.getParams().put("account", account);
            model.addAttribute("account",account);
        }
        MyPage<CspSysUser> page = cspSysUserService.findCspSysUser(pageable);
        model.addAttribute("page", page);
        return "/system/userList";
    }


    @RequestMapping(value = "/register")
    public String addUserInfo(CspSysUser user, Model model,RedirectAttributes redirectAttributes) {
        user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
        cspSysUserService.insert(user);
        addFlashMessage(redirectAttributes, "添加成功");
        return "redirect:/csp/sys/user/list";
    }

}
