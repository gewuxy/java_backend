package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.csp.admin.utils.SubjectUtils;
import cn.medcn.sys.model.SystemRole;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SystemRoleService;
import cn.medcn.sys.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by huanghuibin on 2017/5/3.
 */
@Controller
@RequestMapping(value="/sys/user")
public class UserController extends BaseController {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemRoleService systemRoleService;

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
    @Log(name="查看当前用户信息")
    public String info(Model model) throws SystemException{
        Integer userid = SubjectUtils.getCurrentUserid();
        SystemUser user = systemUserService.findUserHasRole(userid);
        model.addAttribute("user", user);
        return "/sys/userInfo";
    }

    @RequestMapping(value = "/update")
    @Log(name="修改当前用户信息")
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
    public String resetPwd(String oldPassword, String newPassword,Model model){
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

    @RequestMapping(value = "/add")
    @Log(name="跳转添加或编辑系统用户页面")
    public String add(Integer id,Model model){
        List<SystemRole> roleList = systemRoleService.select(new SystemRole());
        model.addAttribute("roleList",roleList);
        if(id != null){
            SystemUser user = systemUserService.selectByPrimaryKey(id);
            model.addAttribute("user",user);
        }
        return "/sys/addAdminForm";
    }

    @RequestMapping(value = "/edit")
    @Log(name="添加或编辑系统用户操作")
    public String edit(SystemUser user ,Model model,RedirectAttributes redirectAttributes){
        String msg = "";
        if(user.getId() != null){  //编辑
            user.setPassword(null);
            user.setUserName(null);
            systemUserService.updateByPrimaryKeySelective(user);
            msg = "编辑成功";
        }else{  //新增
            SystemUser searchUser = new SystemUser();
            searchUser.setUserName(user.getUserName());
            SystemUser isExit = systemUserService.selectOne(searchUser);
            if(isExit != null){
                model.addAttribute("err","当前帐号已存在");
                List<SystemRole> roleList = systemRoleService.select(new SystemRole());
                model.addAttribute("roleList",roleList);
                return "sys/addAdminForm";
            }
            user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
            user.setActive(true);
            systemUserService.insertSelective(user);
            msg = "新增成功";
        }
        addFlashMessage(redirectAttributes, msg);
        return "redirect:/sys/user/list";
    }
}
