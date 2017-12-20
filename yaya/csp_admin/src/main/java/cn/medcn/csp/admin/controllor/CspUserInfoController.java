package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.dto.AddressDTO;
import cn.medcn.common.utils.AddressUtils;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @Autowired
    private SystemRegionService systemRegionService;

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
        List<SystemRegion> systemRegions = systemRegionService.selectByPreId();
        List<CspUserInfo> cspUserInfos = cspUserService.selectByIp();
       /* for ( CspUserInfo cspUserInfo : cspUserInfos ) {
            Integer count = cspUserInfo.getCount();
            String lastLoginIp = cspUserInfo.getLastLoginIp();
            AddressDTO dto = AddressUtils.parseAddress(lastLoginIp);
            String region = dto.getRegion();
            if (region == null || region == ""){
                return error();
            } else {
                int i = count / allUserCount ;
                System.out.println(i);
            }
        }*/

       /* List<CspUserInfo> cspUserInfos = cspUserService.select(new CspUserInfo());
        List<SystemRegion> systemRegions = systemRegionService.selectByPreId();
        for ( CspUserInfo cspUserInfo : cspUserInfos ) {
            int count = 0;
            String lastLoginIp = cspUserInfo.getLastLoginIp();
            AddressDTO dto = AddressUtils.parseAddress(lastLoginIp);
            String region = dto.getRegion();
            for (SystemRegion systemRegion : systemRegions){
                String province = systemRegion.getName();
                if (region == province){

                }
            }

        }*/

        return "cspIndex/cspIndexList";
    }
}
