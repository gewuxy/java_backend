package cn.medcn.csp.admin.controllor;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.user.dto.CspNewlyStaticDTO;
import cn.medcn.user.dto.CspOrderPlatFromDTO;
import cn.medcn.user.service.CspPackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String echartsData(Integer abroad,String startTime,String endTime,Integer grain){
        //获取每日总额订单（人民币、美元）
        List<Map<String,Object>> capital = cspPackageOrderService.orderCapitalStati(grain,abroad,getDate(startTime,grain,Constants.NUMBER_ZERO),getDate(endTime,grain,Constants.NUMBER_ONE));
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
    public String tableData(Pageable pageable,Integer abroad,String startTime,String endTime,Integer grain){
        pageable.setPageSize(3);
        pageable.put("abroad",abroad);
        pageable.put("grain",grain);
        pageable.put("startTime",getDate(startTime,grain,Constants.NUMBER_ZERO));
        pageable.put("endTime",getDate(endTime,grain,Constants.NUMBER_ONE));
        MyPage<CspOrderPlatFromDTO> list = cspPackageOrderService.getCapitalByDay(pageable);
        return success(list);
    }

    /**
     *
     * @param time
     * @param grain  时间粒度
     * @param type   粒度的开始或者截止时间 0：开始 1：截止
     * @return
     * @throws ParseException
     */
    public Date getDate(String time,Integer grain,Integer type){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
            if(grain == CspNewlyStaticDTO.Grain.WEEK.ordinal()){
                date =  type == Constants.NUMBER_ZERO ? CalendarUtils.getWeekFirstDay(date) : CalendarUtils.getWeekLastDay(date);
            }else if(grain == CspNewlyStaticDTO.Grain.MONTH.ordinal()){
                date =  type == Constants.NUMBER_ZERO ? CalendarUtils.getMonthFirstDay(date) : CalendarUtils.getMonthLastDay(date);
            }else if(grain == CspNewlyStaticDTO.Grain.QUARTER.ordinal()){
                date =  type == Constants.NUMBER_ZERO ? CalendarUtils.getQuarterFirstDate(date) : CalendarUtils.getQuarterLastDate(date);
            }else if(grain == CspNewlyStaticDTO.Grain.YEAR.ordinal()){
                date =  type == Constants.NUMBER_ZERO ?  CalendarUtils.getCurrYearFirstDay(date) : CalendarUtils.getCurrYearLastDay(date);
            }else{
                //按天为粒度
                return date;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return date;
    }
}
