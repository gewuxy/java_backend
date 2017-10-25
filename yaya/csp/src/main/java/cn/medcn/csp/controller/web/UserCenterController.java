package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.user.dao.BindInfoDAO;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by LiuLP on 2017/10/11/011.
 */
@Controller
@RequestMapping(value = "/mgr/user")
public class UserCenterController extends CspBaseController{

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;


    @Value("${app.file.upload.base}")
    protected String uploadBase;

    @Value("${app.file.base}")
    protected String fileBase;


    /**
     * 个人中心的用户信息
     * @return
     */
    @RequestMapping("/info")
    public String userInfo(Model model){
        addBaseUserInfo(model);
        return localeView("/userCenter/userInfo");
    }

    /**
     * 左侧的基本用户信息
     * @param model
     */
    private void addBaseUserInfo(Model model) {
        String userId = getWebPrincipal().getId();
        CspUserInfoDTO dto = cspUserService.findCSPUserInfo(userId);
        if (!StringUtils.isEmpty(dto.getAvatar())) {
            dto.setAvatar(fileBase + dto.getAvatar());
        }
        List<BindInfo> bindInfoList = cspUserService.findBindListByUserId(userId);
        dto.setBindInfoList(bindInfoList);
        model.addAttribute("dto", dto);
    }


    /**
     * 跳转到头像设置页面
     * @return
     */
    @RequestMapping("/toAvatar")
    public String toAvatar(Model model){
        addBaseUserInfo(model);
        return localeView("/userCenter/setAvatar");
    }

    /**
     * 修改用户头像
     *
     * @return
     */

    @RequestMapping(value = "/updateAvatar",method = RequestMethod.POST)
    @ResponseBody
    public String updateAvatar(@RequestParam(value = "file", required = false) MultipartFile file){
        if (file == null) {
            return error(local("upload.error.null"));
        }
        String userId = getWebPrincipal().getId();
        String url = null;
        try {
            url = cspUserService.updateAvatar(file,userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        Principal principal = getWebPrincipal();
        principal.setAvatar(url);
        SecurityUtils.set(principal);
        return success(url);
    }


    /**
     * 跳转到修改密码页面
     * @param model
     * @return
     */
    @RequestMapping("toReset")
    public String toResetPwd(Model model){
        addBaseUserInfo(model);
        String userId = getWebPrincipal().getId();
        CspUserInfo info = cspUserService.selectByPrimaryKey(userId);
        if(StringUtils.isEmpty(info.getEmail())){
            //没有绑定邮箱
            model.addAttribute("needBind",true);
        }
        return localeView("/userCenter/pwdReset");

    }

    /**
     * 重置密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping("/resetPwd")
    @ResponseBody
    public String resetPwd(String oldPwd,String newPwd) {

        if(StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
            return error(local("user.empty.password"));
        }
        String userId = getWebPrincipal().getId();
        try {
            cspUserService.resetPwd(userId,oldPwd,newPwd);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 更新个人信息中的姓名和简介
     */
    @RequestMapping("/updateInfo")
    @ResponseBody
    public String updateInfo(CspUserInfo info) {
        info.setId(getWebPrincipal().getId());
        cspUserService.updateByPrimaryKeySelective(info);
        return success();
    }



}
