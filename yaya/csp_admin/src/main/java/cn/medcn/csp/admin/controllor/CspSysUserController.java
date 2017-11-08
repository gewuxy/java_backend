package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.csp.admin.service.CspSysUserService;
import cn.medcn.csp.admin.utils.SubjectUtils;
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
@RequestMapping(value="/csp/sys")
public class CspSysUserController extends BaseController {

   @Autowired
   private CspSysUserService cspSysUserService;

    @RequestMapping(value = "/user/pwd")
    public String resetPwd(){
        return "/system/resetPwd";
    }

    @RequestMapping(value = "/user/addAdmin")
    public String updateUserInfo() {
        return "/system/addAdminForm";
    }

    /**
     * 获取管理员列表
     * @param pageable
     * @param account
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/list")
    public String cspSysUserSearch(Pageable pageable, String account, Model model) {
        if (!StringUtils.isEmpty(account)) {
            pageable.getParams().put("account", account);
            model.addAttribute("account",account);
        }
        MyPage<CspSysUser> page = cspSysUserService.findCspSysUser(pageable);
        model.addAttribute("page", page);
        return "/system/userList";
    }

    /**
     * 获取用户信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/info")
    public String cspSysUserSearch(Model model) {
        Integer userId = SubjectUtils.getCurrentUserid();
        CspSysUser user = cspSysUserService.selectByPrimaryKey(userId);
        model.addAttribute("user", user);
        return "/system/userInfo";
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/user/resetPwd")
    public String resetPwd(String oldPassword, String newPassword,Model model) throws SystemException {
        Integer userId = SubjectUtils.getCurrentUserid();
        CspSysUser user = cspSysUserService.selectByPrimaryKey(userId);
        if (!user.getPassword().equals(MD5Utils.MD5Encode(oldPassword))) {
            model.addAttribute("passwordErrpor","旧密码错误");
            return "/system/resetPwd";
        }
        user.setPassword(MD5Utils.MD5Encode(newPassword));
        cspSysUserService.updateByPrimaryKey(user);
        return "redirect:/logout";
    }

    /**
     * 修改用户信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/update")
    public String updateUserInfo(String email,String mobile, Model model) {
        Integer userId = SubjectUtils.getCurrentUserid();
        CspSysUser user =  new CspSysUser();
        user.setId(userId);
        CspSysUser newOne = cspSysUserService.selectByPrimaryKey(user);
        newOne.setEmail(email);
        newOne.setMobile(mobile);
        cspSysUserService.updateByPrimaryKey(newOne);
        model.addAttribute("user", newOne);
        return "/system/userInfo";
    }

    /**
     * 添加管理员
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/add")
    public String addUserInfo(CspSysUser user, Model model,RedirectAttributes redirectAttributes) {
        user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
        cspSysUserService.insert(user);
        addFlashMessage(redirectAttributes, "添加成功");
        return "redirect:/csp/sys/user/list";
    }

}
