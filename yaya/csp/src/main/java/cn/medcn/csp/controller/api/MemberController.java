package cn.medcn.csp.controller.api;

import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.service.CspPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Liuchangling on 2017/12/11.
 */
@Controller
@RequestMapping(value = "/api/member")
public class MemberController extends CspBaseController{
    @Autowired
    protected CspPackageService packageService;

    // 查看用户当前套餐版本
    @RequestMapping(value = "/package")
    @ResponseBody
    public String userPackage () {
        String userId = SecurityUtils.get().getId();
        CspPackage cspPackage = packageService.findUserPackageById(userId);
        return success(cspPackage);
    }

    // 还有5天到期时提醒
    @RequestMapping(value = "/expire/remind")
    @ResponseBody
    public String nearExpireRemind() {

        return success();
    }
}

