package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CalendarUtils;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Pageable pageable = new Pageable();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        Date endDate = CalendarUtils.calendarDay(-1);
        pageable.put("endTime",endDate);
        String endTime = format.format(endDate);
        model.addAttribute("endTime",endTime);
        Date startDate = CalendarUtils.calendarDay(-7);
        pageable.put("startTime",startDate);
        String startTime = format.format(startDate);
        pageable.setPageSize(10);
        pageable.put("abroad",0);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        //获取资金总额
        List<Map<String,Object>> total = cspPackageOrderService.totalMoney();
        model.addAttribute("rmb",total.get(0).get("money"));
        model.addAttribute("usd",total.get(1).get("money"));
        //获取查询区间的总额
        CspOrderPlatFromDTO totalMomey = cspPackageOrderService.getTotalCapital(pageable);
        model.addAttribute("total",totalMomey.getTotalMoney());
        return "/statistics/moneyStati";
    }

    /**
     * 加载表格数据和table数据
     * @param abroad
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/echarts/data")
    @ResponseBody
    public String echartsData(Integer abroad,String startTime,String endTime){

        //获取每日总额订单（人民币、美元）
        List<Map<String,Object>> capital = cspPackageOrderService.orderCapitalStati(abroad,getDate(startTime),getDate(endTime));
        return success(capital);
    }

    /**
     * 加载
     * @param pageable
     * @param abroad
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/table")
    @ResponseBody
    public String tableData(Pageable pageable,Integer abroad,String startTime,String endTime){
        pageable.setPageSize(3);
        pageable.put("abroad",abroad);
        pageable.put("startTime",getDate(startTime));
        pageable.put("endTime",getDate(endTime));
        MyPage<CspOrderPlatFromDTO> list = cspPackageOrderService.getCapitalByDay(pageable);
        return success(list);
    }

    public Date getDate(String date){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }
}
