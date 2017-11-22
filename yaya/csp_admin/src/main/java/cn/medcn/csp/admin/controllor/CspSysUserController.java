package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.csp.admin.log.Log;
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
    public String resetView(){
        return "/sys/resetPwd";
    }

    @RequestMapping(value = "/user/addAdmin")
    public String addView() {
        return "/sys/addAdminForm";
    }

    /**
     * 获取管理员列表
     * @param pageable
     * @param account
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/list")
    @Log(name="查看管理员列表")
    public String cspSysUserSearch(Pageable pageable, String account, Model model) {
        if (!StringUtils.isEmpty(account)) {
            pageable.getParams().put("account", account);
            model.addAttribute("account",account);
        }
        MyPage<CspSysUser> page = cspSysUserService.findCspSysUser(pageable);
        model.addAttribute("page", page);
        return "/sys/userList";
    }

    /**
     * 获取用户信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/info")
    @Log(name="查看用户信息")
    public String searchUserInfo(Model model) {
        Integer userId = SubjectUtils.getCurrentUserid();
        CspSysUser user = cspSysUserService.selectByPrimaryKey(userId);
        model.addAttribute("user", user);
        return "/sys/userInfo";
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/user/resetPwd")
    @Log(name="重置密码")
    public String resetPwd(String oldPassword, String newPassword,Model model) throws SystemException {
        Integer userId = SubjectUtils.getCurrentUserid();
        CspSysUser user = cspSysUserService.selectByPrimaryKey(userId);
        if (!user.getPassword().equals(MD5Utils.MD5Encode(oldPassword))) {
            model.addAttribute("err","旧密码错误");
            return "/sys/resetPwd";
        }
        user.setPassword(MD5Utils.MD5Encode(newPassword));
        cspSysUserService.updateByPrimaryKeySelective(user);
        return "redirect:/logout";
    }

    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping(value = "/user/update")
    @Log(name="修改用户信息")
    public String updateUserInfo(CspSysUser user) {
        cspSysUserService.updateByPrimaryKeySelective(user);
        return "redirect:/csp/sys/user/info";
    }

    /**
     * 添加管理员
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/add")
    @Log(name="添加管理员")
    public String addUserInfo(CspSysUser user, Model model,RedirectAttributes redirectAttributes) {
        String account = SubjectUtils.getCurrentUsername();
        if(account.equals(user.getAccount())){
            model.addAttribute("err","此用户名已存在");
            return "/sys/addAdminForm";
        }
        user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
        cspSysUserService.insert(user);
        addFlashMessage(redirectAttributes, "添加成功");
        return "redirect:/csp/sys/user/list";
    }

}
