package cn.medcn.csp.admin.tasks;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.user.model.ReportPackage;
import cn.medcn.user.service.CspUserPackageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户套餐统计
 * by create HuangHuibin 2018/1/3
 */
public class UserPackageTask {

    @Autowired
    protected CspUserPackageService cspUserPackageService;

    public void execute() throws ParseException {
       List<Map<String,Object>> info = cspUserPackageService.getTodayPackageInfo();
       if(info != null){
           Integer sta = Integer.parseInt(info.get(0).get("num").toString());
           Integer pre = Integer.parseInt(info.get(1).get("num").toString());
           Integer pro = Integer.parseInt(info.get(2).get("num").toString());
           Integer userNum = Integer.parseInt(info.get(0).get("userNum").toString());
           ReportPackage packages = new ReportPackage();
           packages.setStaCount(sta);
           packages.setPreCount(pre);
           packages.setProCount(pro);
           packages.setUserCount(userNum);
           packages.setRegisterTime(new Date());
           //插入每日数据
           cspUserPackageService.insertReportPackage(packages);
           Date date  = new Date();
           if(CalendarUtils.isSameDate(CalendarUtils.getWeekFirstDay(),date)){  //如果是周一
               packages.setId(null);
               packages.setTaskType(Constants.NUMBER_ONE);
               cspUserPackageService.insertReportPackage(packages);
           }
           if(CalendarUtils.isSameDate(CalendarUtils.getMonthFirstDay(),date)){ //如果是月第一天
               packages.setId(null);
               packages.setTaskType(Constants.NUMBER_TWO);
               cspUserPackageService.insertReportPackage(packages);
           }
           if(CalendarUtils.isSameDate(CalendarUtils.getQuarterFirstDate(date),date)){ //如果是季度第一天
               packages.setId(null);
               packages.setTaskType(Constants.NUMBER_THREE);
               cspUserPackageService.insertReportPackage(packages);
           }
           if(CalendarUtils.isSameDate(CalendarUtils.getCurrYearFirstDay(),date)){  // 如果是每年第一天
               packages.setId(null);
               packages.setTaskType(Constants.NUMBER_FOUR);
               cspUserPackageService.insertReportPackage(packages);
           }
       }
    }
}
