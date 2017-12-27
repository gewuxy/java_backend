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
    public String statistic(Pageable pageable,Integer abroad,String startTime,String endTime,Model model){
        pageable.setPageSize(10);
        pageable.put("abroad",abroad);
        //获取资金总额
        List<Map<String,Object>> total = cspPackageOrderService.totalMoney();
        model.addAttribute("rmb",total.get(0).get("money"));
        model.addAttribute("usd",total.get(1).get("money"));
        //获取每日总额订单（人民币、美元）
        //获取每日各渠道资金总额
        List<CspOrderPlatFromDTO> list = cspPackageOrderService.getCapitalByDay(pageable);
        model.addAttribute("list",list);
        CspOrderPlatFromDTO totalMomey = cspPackageOrderService.getTotalCapital(pageable);
        model.addAttribute("total",totalMomey.getTotalMoney());
        return "/statistics/moneyStati";
    }

    @RequestMapping(value = "/echarts/data")
    @ResponseBody
    public String echartsData(Integer abroad,String startTime,String endTime){
        //获取每日各渠道资金总额
        List<Map<String,Object>> capital = cspPackageOrderService.orderCapitalStati(abroad,startTime,endTime);
        return success(capital);
    }

    @RequestMapping(value = "/money")
    public String money(){
        return "/statistics/moneyStati";
    }
}
