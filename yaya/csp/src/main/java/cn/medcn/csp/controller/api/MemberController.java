package cn.medcn.csp.controller.api;

import cn.medcn.common.utils.CalendarUtils;
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

    @Autowired
    protected CspUserPackageService userPackageService;

    // 查看用户当前套餐版本
    @RequestMapping(value = "/package")
    @ResponseBody
    public String userPackage () {
        String userId = SecurityUtils.get().getId();
        CspPackage cspPackage = packageService.findUserPackageById(userId);
        return success(cspPackage);
    }

    /**
     * 套餐过期提醒
     * @return
     */
    @RequestMapping(value = "/expire/remind")
    @ResponseBody
    public String expireRemind() {
        String userId = SecurityUtils.get().getId();
        CspPackage cspPackage = packageService.findUserPackageById(userId);
        if (cspPackage != null && cspPackage.getPackageEnd() != null
                && cspPackage.getId().intValue() > CspPackage.TypeId.STANDARD.getId()) {
            try {
                int diffDays = CalendarUtils.daysBetween(new Date(), cspPackage.getPackageEnd());
                // 还有5天到期时提醒
                if (diffDays == 5) {
                    return success(local("fivedays.expire.remind"));
                } else if (diffDays == 0){ // 已经过期提醒
                    // 已经过期将套餐变更为标准版
                    CspUserPackage userPackage = userPackageService.selectByPrimaryKey(userId);

                    userPackage.setPackageId(CspPackage.TypeId.STANDARD.getId());
                    userPackageService.updateByPrimaryKeySelective(userPackage);

                    return success(local(""));
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return error(local("data.error"));
            }
        }
        return error(local("data.error"));
    }
}

