package cn.medcn.csp.admin.controllor;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.StringUtils;
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
     * @param type 货币类型，0表示人民币，1表示美元
     * @return
     */
    @RequestMapping("/home")
    public String homeStats(Model model, Pageable pageable,Integer type,String startTime,String endTime){
        pageable.setPageSize(10);
        List<CspPackageOrder> moneyList = cspPackageOrderService.selectAbroadAndHomeMoney();
        float rmbTotal = 0;
        float usdTotal = 0;
        //获取不同货币资金总额
        for(CspPackageOrder order:moneyList){
            if(order.getCurrencyType() == CspPackageOrder.CurrencyType.RMB.ordinal()){
                rmbTotal = order.getTotalMoney();
            }else{
                usdTotal = order.getTotalMoney();
            }
        }

        if(type == null){
            type = CspPackageOrder.CurrencyType.RMB.ordinal();
        }
        //查找订单信息
        pageable.put("type",type);
        //获取对应时间段的交易成功总额
        if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
            Integer successSum = cspPackageOrderService.findOrderSuccessSum(type,startTime,endTime);
            model.addAttribute("successSum",successSum == null ? 0:successSum);
            pageable.put("startTime",startTime);
            pageable.put("endTime",endTime);
        }
        MyPage<CspPackageOrderDTO> myPage = cspPackageOrderService.findOrderListByCurrencyType(pageable);

        model.addAttribute("rmb",rmbTotal);
        model.addAttribute("usd",usdTotal);
        model.addAttribute("page",myPage);
        model.addAttribute("type",type);
        return "/statistics/packageOrderStats";
    }

}
