package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.user.dto.CspNewlyStaticDTO;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * csp用户注册统计
 * Created by LiuLP on 2017/12/18/018.
 */
@RequestMapping("/sys/register")
@Controller
public class CspRegisterStaticController extends BaseController{


    @Autowired
    private CspUserService cspUserService;

    /**
     * 获取国内数据
     * @return
     */
    @RequestMapping("/home")
    public String getData(Model model){
            int homeCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.home.ordinal());
            int abroadCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.abroad.ordinal());
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date endDate = CalendarUtils.calendarDay(-1);
            String endTime = format.format(endDate);
            model.addAttribute("endTime",endTime);
            Date startDate = CalendarUtils.calendarDay(-7);
            String startTime = format.format(startDate);
            model.addAttribute("startTime",startTime);
            model.addAttribute("home",homeCount);
            model.addAttribute("abroad",abroadCount);
            return "/registerStatic/homeStatic";
    }


    /**
     * 获取海外数据
     * @return
     */
    @RequestMapping("/abroad")
    public String getAbroadData(){
        int homeCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.home.ordinal());
        int abroadCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.abroad.ordinal());

        return "/registerStatic/abroadStatic";
    }


    /**
     *
     * @param location  海内，国外
     * @param startTime
     * @param endTime
     * @param grain 时间粒度，日，周，月，季，年
     * @return
     */
    @RequestMapping("/newly/static")
    @ResponseBody
    public String newlyStatic(Integer location, Date startTime, Date endTime, Integer grain) throws ParseException {
        if(location == null){
            return error("请指定地区");
        }
        if(grain == null){
            return error("请指定时间粒度");
        }

        String format = "%Y-%m-%d";
        //按周统计
        if(grain == CspNewlyStaticDTO.Grain.WEEK.ordinal()){
            startTime = CalendarUtils.getWeekFirstDay(startTime);
            endTime = CalendarUtils.getWeekLastDay(endTime);
            format = "%Y-%u";
            //按月统计
        }else if(grain == CspNewlyStaticDTO.Grain.MONTH.ordinal()){
            startTime = CalendarUtils.getMonthFirstDay(startTime);
            endTime = CalendarUtils.getMonthLastDay(endTime);
            format = "%Y-%m";
            //按季度统计
        }else if(grain == CspNewlyStaticDTO.Grain.QUARTER.ordinal()){
            startTime = CalendarUtils.getQuarterFirstDate(startTime);
            endTime = CalendarUtils.getQuarterLastDate(endTime);
            format = "%Y";

            //按年统计
        }else if(grain == CspNewlyStaticDTO.Grain.YEAR.ordinal()){
            startTime = CalendarUtils.getCurrYearFirstDay(startTime);
            endTime = CalendarUtils.getCurrYearLastDay(endTime);

        }

        Map<String,Object> map = new HashMap<>();
        map.put("location",location);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("format",format);
        List<CspNewlyStaticDTO> list = cspUserService.findNewlyRegisterList(map);

        return success();
    }

}
