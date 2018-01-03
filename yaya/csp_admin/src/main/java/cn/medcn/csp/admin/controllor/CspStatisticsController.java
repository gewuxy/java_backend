package cn.medcn.csp.admin.controllor;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.csp.admin.log.Log;
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
@RequestMapping(value="/csp/stats")
public class CspStatisticsController extends BaseController {

    @Autowired
    protected CspPackageOrderService cspPackageOrderService;

    /**
     * 资金统计入口
     * @param model
     * @param type
     * @return
     */
    @RequestMapping(value = "/money")
    @Log(name = "资金统计")
    public String statistic(Model model,Integer type){
        if(type == null) type = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        Date endDate = CalendarUtils.calendarDay(-1);
        String endTime = format.format(endDate);
        model.addAttribute("endTime",endTime);
        Date startDate = CalendarUtils.calendarDay(-7);
        String startTime = format.format(startDate);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        //获取资金总额
        List<Map<String,Object>> total = cspPackageOrderService.totalMoney();
        model.addAttribute("rmb",total.get(0).get("money"));
        model.addAttribute("usd",total.get(1).get("money"));
        model.addAttribute("abroad",0);
        if(type == 0){ // 资金统计
            return "/statistics/moneyStats";
        }else if(type == 1){ // 转化率
            return "/statistics/transfStats";
        }else if(type == 2){ //套餐分布
            return "/statistics/distStats";
        }else{   //续费率
            return "/statistics/renewStats";
        }
    }

    /**
     * 加载图表数据和对应总金额
     * @param abroad
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/echarts/money")
    @ResponseBody
    @Log(name = "获取对应粒度的金额总数")
    public String echartsData(Integer abroad,String startTime,String endTime,Integer grain){
        Map<String,Object> map = new HashMap<>();
        //获取每日总额订单（人民币、美元）
        List<Map<String,Object>> capital = cspPackageOrderService.orderCapitalStati(grain,abroad,getDate(startTime,grain,Constants.NUMBER_ZERO),getDate(endTime,grain,Constants.NUMBER_ONE));
        map.put("capital",capital);
        //当前区间总额
        CspOrderPlatFromDTO totalMomey = cspPackageOrderService.getTotalCapital(grain,abroad,getDate(startTime,grain,Constants.NUMBER_ZERO),getDate(endTime,grain,Constants.NUMBER_ONE));
        if(totalMomey != null){
            map.put("total",totalMomey.getTotalMoney());
        }else{
            map.put("total",0);
        }
        return success(map);
    }

    /**
     * 加载对应图表的各渠道的表格数据
     * @param pageable
     * @param abroad
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/table")
    @ResponseBody
    @Log(name = "获取各渠道金额")
    public String tableData(Pageable pageable,Integer abroad,String startTime,String endTime,Integer grain){
        pageable.setPageSize(12);
        pageable.put("abroad",abroad);
        pageable.put("grain",grain);
        pageable.put("startTime",getDate(startTime,grain,Constants.NUMBER_ZERO));
        pageable.put("endTime",getDate(endTime,grain,Constants.NUMBER_ONE));
        MyPage<Map<String,Object>> list = cspPackageOrderService.getCapitalByDay(pageable);
        return success(list);
    }

    @RequestMapping(value = "/echarts/transf")
    @ResponseBody
    @Log(name = "获取转化率")
    public String transf(String startTime,String endTime){
        List<Map<String,Object>> transformation = cspPackageOrderService.transfStats(getDate(startTime,Constants.NUMBER_ZERO,Constants.NUMBER_ZERO),getDate(endTime,Constants.NUMBER_ZERO,Constants.NUMBER_ONE));
        return success(transformation);
    }

    @RequestMapping(value = "/echarts/renew")
    @ResponseBody
    @Log(name = "获取续费率")
    public String renew(String startTime,String endTime){
        List<Map<String,Object>> renew = cspPackageOrderService.renewStats(getDate(startTime,Constants.NUMBER_ZERO,Constants.NUMBER_ZERO),getDate(endTime,Constants.NUMBER_ZERO,Constants.NUMBER_ONE));
        return success(renew);
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
