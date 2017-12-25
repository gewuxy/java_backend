package cn.medcn.csp.controller.api;

import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspPackageService;
import cn.medcn.user.service.CspUserPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;

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
        cspPackage.setMeetTotalCount(cspPackage.getHiddenMeetCount() + cspPackage.getUsedMeetCount());
        return success(cspPackage);
    }


}

