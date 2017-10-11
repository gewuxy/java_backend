package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by LiuLP on 2017/10/11/011.
 */
@Controller
@RequestMapping(value = "/web/user")
public class CspUserController extends BaseController{

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;


    @Value("${app.file.upload.base}")
    protected String uploadBase;

    @Value("${app.file.base}")
    protected String fileBase;


    /**
     * 修改用户头像
     * @param avatar
     * @return
     */
    @RequestMapping("/updateAvatar")
    @ResponseBody
    public String updateAvatar(String avatar){
        String userId = SecurityUtils.get().getId();
        CspUserInfo user = cspUserService.selectByPrimaryKey(userId);
        user.setAvatar(avatar);
        cspUserService.updateByPrimaryKeySelective(user);
        return success();
    }


    /**
     * 重置密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping("/resetPwd")
    public String resetPwd(String oldPwd,String newPwd) {

        if(StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
            return error(local("user.empty.password"));
        }
        String userId = SecurityUtils.get().getId();
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
        info.setId(SecurityUtils.get().getId());
        cspUserService.updateByPrimaryKeySelective(info);
        return success();
    }



}
