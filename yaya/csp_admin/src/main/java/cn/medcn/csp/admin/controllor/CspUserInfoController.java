package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**Csp 后台首页
 *
 * @Author：jianliang
 * @Date: Create in 15:34 2017/12/19
 */
@Controller
@RequestMapping(value = "/csp/userInfo")
public class CspUserInfoController extends BaseController {

    @Autowired
    private CspUserService cspUserService;

    @Autowired
    private CspPackageOrderService cspPackageOrderService;

    @Autowired
    private CspUserPackageService cspUserPackageService;

    @RequestMapping(value = "/list")
    public String userInfoList(Model model){
        //昨日新增用户
        int newUserCount = cspUserService.selectNewUser();
        model.addAttribute("newUserCount",newUserCount);
         //昨日进账
        Double newMoney = cspPackageOrderService.selectNewMoney();
        model.addAttribute("newMoney",newMoney);
        //总用户
        int allUserCount = cspUserService.selectAllUserCount();
        model.addAttribute("allUserCount",allUserCount);

        //高级版和专业版用户
        int premiumEditionCount = cspUserPackageService.selectPremiumEdition();
        int professionalEditionCount = cspUserPackageService.selectProfessionalEdition();
        //基础版本
        int standardEdition = allUserCount - premiumEditionCount - professionalEditionCount;
        model.addAttribute("standardEdition",standardEdition);
        model.addAttribute("premiumEditionCount",premiumEditionCount);
        model.addAttribute("professionalEditionCount",professionalEditionCount);

        return "cspIndex/cspIndexList";
    }
}
