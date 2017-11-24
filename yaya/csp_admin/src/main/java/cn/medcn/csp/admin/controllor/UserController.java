package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.csp.admin.utils.SubjectUtils;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by huanghuibin on 2017/5/3.
 */
@Controller
@RequestMapping(value="/sys/user")
public class UserController extends BaseController {

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 获取系统用户列表
     * @param pageable
     * @param username
     * @param model
     * @return
     */
    @RequestMapping(value="/list")
    @Log(name="查询系统用户列表")
    public String list(Pageable pageable, String username, Model model){
        if (!StringUtils.isEmpty(username)) {
            pageable.getParams().put("username", username);
            model.addAttribute("username",username);
        }
        MyPage<SystemUser> page = systemUserService.findUserByPage(pageable);
        model.addAttribute("page", page);
        return "/sys/userList";
    }

    @RequestMapping(value="/info")
    @Log(name="查看用户信息")
    public String info(Model model) throws SystemException{
        Integer userid = SubjectUtils.getCurrentUserid();
        SystemUser user = systemUserService.findUserHasRole(userid);
        model.addAttribute("user", user);
        return "/sys/userInfo";
    }

    @RequestMapping(value = "/update")
    @Log(name="修改用户信息")
    public String updateUserInfo(SystemUser user) {
        systemUserService.updateByPrimaryKeySelective(user);
        return "redirect:/sys/user/info";
    }

    @RequestMapping(value = "/pwd")
    public String resetView(){
        return "/sys/resetPwd";
    }


    @RequestMapping(value = "/resetPwd")
    @Log(name="重置密码")
    public String resetPwd(String oldPassword, String newPassword,Model model) throws SystemException {
        Integer userId = SubjectUtils.getCurrentUserid();
        SystemUser user = systemUserService.selectByPrimaryKey(userId);
        if (!user.getPassword().equals(MD5Utils.MD5Encode(oldPassword))) {
            model.addAttribute("err","旧密码错误");
            return "/sys/resetPwd";
        }
        user.setPassword(MD5Utils.MD5Encode(newPassword));
        systemUserService.updateByPrimaryKeySelective(user);
        return "redirect:/logout";
    }

    //    @RequestMapping(value = "/rrruser/add")
//    @Log(name="添加管理员")
//    public String addUserInfo(CspSysUser user, Model model,RedirectAttributes redirectAttributes) {
//        String account = SubjectUtils.getCurrentUsername();
//        if(account.equals(user.getAccount())){
//            model.addAttribute("err","此用户名已存在");
//            return "/sys/addAdminForm";
//        }
//        user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
//        cspSysUserService.insert(user);
//        addFlashMessage(redirectAttributes, "添加成功");
//        return "redirect:/csp/sys/user/list";
//    }
}
