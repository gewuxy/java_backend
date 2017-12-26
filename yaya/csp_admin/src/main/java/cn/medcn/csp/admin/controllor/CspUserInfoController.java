package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.dto.AddressDTO;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.AddressUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.dto.UserDataMapDTO;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.NumberFormat;
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
        int newUserCount = cspUserService.selectNewUser();
        model.addAttribute("newUserCount",newUserCount);
         //昨日进账
        Double newMoney = cspPackageOrderService.selectNewMoney();
        model.addAttribute("newMoney",newMoney);
        //总用户
        int allUserCount = cspUserService.selectAllUserCount();
        model.addAttribute("allUserCount",allUserCount);

        //标准版、高级版和专业版用户
        int standardEditionCount = cspUserPackageService.selectStandardEdition();
        int premiumEditionCount = cspUserPackageService.selectPremiumEdition();
        int professionalEditionCount = cspUserPackageService.selectProfessionalEdition();
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
    public String userInfoListUs(Model model){
        //昨日新增用户
        int newUserCount = cspUserService.selectNewUserByUs();
        model.addAttribute("newUserCount",newUserCount);
        //昨日进账
        Double newMoney = cspPackageOrderService.selectNewMoneyByUs();
        model.addAttribute("newMoney",newMoney);
        //总用户
        int allUserCount = cspUserService.selectAllUserCountByUs();
        model.addAttribute("allUserCount",allUserCount);

        //标准版、高级版和专业版用户
        int standardEditionCount = cspUserPackageService.selectStandardEditionByUs();
        int premiumEditionCount = cspUserPackageService.selectPremiumEditionByUs();
        int professionalEditionCount = cspUserPackageService.selectProfessionalEditionByUs();
        model.addAttribute("standardEditionCount",standardEditionCount);
        model.addAttribute("premiumEditionCount",premiumEditionCount);
        model.addAttribute("professionalEditionCount",professionalEditionCount);
        return "cspIndex/cspIndexListUs";
    }
}
