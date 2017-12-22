package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.dto.CspOrderPlatFromDTO;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * by create HuangHuibin 2017/11/3
 */
@Controller
@RequestMapping(value="/csp/stati")
public class CspStatisticsController extends BaseController {

    @Autowired
    protected CspPackageOrderService cspPackageOrderService;

    @RequestMapping(value = "/statistics")
    public String statistic(Model model){
        //获取资金总额
        List<Map<String,Object>> total = cspPackageOrderService.totalMoney();
        model.addAttribute("totalCn",total.get(0).get("money"));
        model.addAttribute("totalUs",total.get(1).get("money"));
        //获取每日总额订单（人民币、美元）
        List<Map<String,Object>> capital = cspPackageOrderService.orderCapitalStati();
        //获取每日各渠道资金总额
        List<CspOrderPlatFromDTO> list = cspPackageOrderService.getCapitalByDay();
        return "/statistics/moneyStati";
    }

    @RequestMapping(value = "/money")
    public String money(){
        return "/statistics/moneyStati";
    }

}
