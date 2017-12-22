package cn.medcn.csp.admin.controllor;

import cn.medcn.user.dto.CspPackageOrderDTO;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.service.CspPackageInfoService;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * csp套餐订单
 * Created by LiuLP on 2017/12/21/021.
 */
@Controller
@RequestMapping("/sys/package/stats")
public class CspPackageStatsController {

    @Autowired
    private CspPackageOrderService cspPackageOrderService;


    /**
     * 海内订单
     * @param model
     * @return
     */
    @RequestMapping("/home")
    public String homeStats(Model model){
        Map<Integer,Float> map = cspPackageOrderService.selectAbroadAndHomeMoney();
        float rmbTotal = map.get(CspPackageOrder.CurrencyType.RMB.ordinal());
        float usdTotal = map.get(CspPackageOrder.CurrencyType.USD.ordinal());
        List<CspPackageOrderDTO> list = cspPackageOrderService.findOrderListByCurrencyType(CspPackageOrder.CurrencyType.RMB.ordinal());
        model.addAttribute("rmb",rmbTotal);
        model.addAttribute("usd",usdTotal);
        model.addAttribute("list",list);
        return "/statistics/packageOrderStats";
    }

}
