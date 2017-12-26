package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

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

    /**
     * CSP首页海内
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name = "海内版本首页数据")
    public String userInfoList(Model model, Pageable pageable){
        pageable.setPageSize(10);
        //昨日新增用户
        int newUserCount = cspUserService.selectNewUser(CspUserInfo.AbroadType.home.ordinal());
        model.addAttribute("newUserCount",newUserCount);
         //昨日进账
        Double newMoney = cspPackageOrderService.selectNewMoney(CspPackageOrder.CurrencyType.RMB.ordinal());
        model.addAttribute("newMoney",newMoney);
        //总用户
        int allUserCount = cspUserService.selectAllUserCount(CspUserInfo.AbroadType.home.ordinal());
        model.addAttribute("allUserCount",allUserCount);

        //标准版、高级版和专业版用户
        int standardEditionCount = cspUserPackageService.selectEdition(CspPackage.TypeId.STANDARD.getId(), CspUserInfo.AbroadType.home.ordinal());
        int premiumEditionCount = cspUserPackageService.selectEdition(CspPackage.TypeId.PREMIUM.getId(), CspUserInfo.AbroadType.home.ordinal());
        int professionalEditionCount = cspUserPackageService.selectEdition(CspPackage.TypeId.PROFESSIONAL.getId(), CspUserInfo.AbroadType.home.ordinal());
        model.addAttribute("standardEditionCount",standardEditionCount);
        model.addAttribute("premiumEditionCount",premiumEditionCount);
        model.addAttribute("professionalEditionCount",professionalEditionCount);

        MyPage<SystemRegion> page = systemRegionService.selectByPreIds(pageable);
        List<SystemRegion> systemRegions = page.getDataList();
        int provinceCount = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        for ( SystemRegion systemRegion : systemRegions ) {
           String name = systemRegion.getName();
            provinceCount = cspUserService.selectByProvince(name);
            float count = (provinceCount * 1.0F / allUserCount) * 100;
            map.put(name,count);
        }
        model.addAttribute("map",map);
        model.addAttribute("page",page);
        // 前端根据pages 分页 固定页数
        Integer pages = (systemRegions.size()-1)/15 + 1;;
        model.addAttribute("pages", pages);
        return "cspIndex/cspIndexList";
    }


    @RequestMapping(value = "/list_us")
    @Log(name = "海外版首页数据")
    public String userInfoListUs(Model model,Pageable pageable){
        //昨日新增用户
        int newUserCount = cspUserService.selectNewUser(CspUserInfo.AbroadType.abroad.ordinal());
        model.addAttribute("newUserCount",newUserCount);
        //昨日进账
        Double newMoney = cspPackageOrderService.selectNewMoney(CspPackageOrder.CurrencyType.USD.ordinal());
        model.addAttribute("newMoney",newMoney);
        //总用户
        int allUserCount = cspUserService.selectAllUserCount(CspUserInfo.AbroadType.abroad.ordinal());
        model.addAttribute("allUserCount",allUserCount);

        //标准版、高级版和专业版用户
        int standardEditionCount = cspUserPackageService.selectEdition(CspPackage.TypeId.STANDARD.getId(),CspUserInfo.AbroadType.abroad.ordinal());
        int premiumEditionCount = cspUserPackageService.selectEdition(CspPackage.TypeId.PREMIUM.getId(), CspUserInfo.AbroadType.abroad.ordinal());
        int professionalEditionCount = cspUserPackageService.selectEdition(CspPackage.TypeId.PROFESSIONAL.getId(), CspUserInfo.AbroadType.abroad.ordinal());
        model.addAttribute("standardEditionCount",standardEditionCount);
        model.addAttribute("premiumEditionCount",premiumEditionCount);
        model.addAttribute("professionalEditionCount",professionalEditionCount);

        //新增进账信息
        MyPage<CspUserInfoDTO> page = cspUserService.findNewDayMoney(pageable);

        model.addAttribute("page",page);
        return "cspIndex/cspIndexListUs";
    }
}
