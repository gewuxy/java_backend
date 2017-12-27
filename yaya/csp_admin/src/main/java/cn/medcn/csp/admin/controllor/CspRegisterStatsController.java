package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dto.CspNewlyStaticDTO;
import cn.medcn.user.dto.CspUserRegisterExcel;
import cn.medcn.user.dto.PackageOrderExcel;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.ReportRegister;
import cn.medcn.user.service.CspUserService;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * csp用户注册统计
 * Created by LiuLP on 2017/12/18/018.
 */
@RequestMapping("/sys/register/stats")
@Controller
public class CspRegisterStatsController extends BaseController{


    @Autowired
    private CspUserService cspUserService;

    /**
     * 获取国内数据
     * @return
     */
    @RequestMapping("/home")
    public String getData(Integer location,Model model){
            //昨日新增的用户数量
            int homeCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.home.ordinal());
            int abroadCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.abroad.ordinal());
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd ");
            Date endDate = CalendarUtils.calendarDay(-1);
            String endTime = format.format(endDate);
            model.addAttribute("endTime",endTime);
            Date startDate = CalendarUtils.calendarDay(-7);
            String startTime = format.format(startDate);
            model.addAttribute("startTime",startTime);
            model.addAttribute("home",homeCount);
            model.addAttribute("abroad",abroadCount);
            model.addAttribute("location",location == null ? 0 :location);
            return "/statistics/userRegisterStats";
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
    public String newlyStatic(Integer location, String startTime, String endTime, Integer grain) throws ParseException {
        if(location == null){
            return error("请指定地区");
        }
        if(grain == null){
            return error("请指定时间粒度");
        }
        if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            return error("请传递正确的日期");
        }

        //处理开始时间
        Date startDate = getFirstDayByGrain(startTime,grain);
        //处理结束时间
        Date endDate = getLastDayByGrain(endTime,grain);
        //获取注册用户数据
        List<ReportRegister> list = getNewlyRegisterList(startDate,endDate,location,grain);

        CspNewlyStaticDTO dto = new CspNewlyStaticDTO();
        dto.setList(list);
        //格式化日期
        List<Date> dateList = CspNewlyStaticDTO.buildDate(grain,startDate,endDate);
        dto.setDateCount(CspNewlyStaticDTO.getDateCount(dateList));

        return success(dto);
    }


    /**
     * 导出用户新增统计数据
     */
    @RequestMapping("/export")
    public void export(Integer location, String startTime, String endTime, Integer grain,HttpServletResponse response) throws ParseException, SystemException {

        if(location == null){
            throw new SystemException("请指定地区");
        }
        if(grain == null){
            throw new SystemException("请指定时间粒度");
        }
        if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            throw new SystemException("请传递正确的日期");
        }

        Date startDate = getFirstDayByGrain(startTime,grain);
        Date endDate = getLastDayByGrain(endTime,grain);
        List<ReportRegister> list = getNewlyRegisterList(startDate,endDate,location,grain);
        String fileName = "新增用户.xls";
        List<Date> dateList = CspNewlyStaticDTO.buildDate(grain,startDate,endDate);
        //转换成excel需要的日期格式
        List<String> stringList =  buildExcelDateFormatByGrain(dateList,grain);
        List<Object> dataList = Lists.newArrayList();
        //生成excel元素
        addExcelDataList(list, dataList, stringList);
        Workbook workbook = ExcelUtils.writeExcel(fileName,dataList,CspUserRegisterExcel.class);
        try {

            ExcelUtils.outputWorkBook(fileName,workbook,response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException("文件导出失败");
        }
    }

    /**
     * 生成excel元素
     * @param list
     * @param dataList
     * @param stringList
     * @return
     */
    private void addExcelDataList(List<ReportRegister> list, List<Object> dataList, List<String> stringList) {
        int sum = 0;
        int wxSum = 0;
        int wbSum = 0;
        int fbSum = 0;
        int twSum = 0;
        int emailSum = 0;
        int mobileSum = 0;
        int yaYaSum = 0;

        for(int i = 0 ; i< list.size(); i++){
            CspUserRegisterExcel data = new CspUserRegisterExcel();
            data.setEmail(list.get(i).getEmailCount());
            data.setFacebook(list.get(i).getFacebookCount());
            data.setMobile(list.get(i).getMobileCount());
            data.setTwitter(list.get(i).getTwitterCount());
            data.setWeiBo(list.get(i).getWeiBoCount());
            data.setWeiXin(list.get(i).getWeiXinCount());
            data.setYaYa(list.get(i).getYaYaCount());
            data.setTime(stringList.get(i));
            data.setSum(list.get(i).getEmailCount() + list.get(i).getFacebookCount() + list.get(i).getMobileCount() + list.get(i).getTwitterCount() +
                    list.get(i).getWeiBoCount() + list.get(i).getWeiXinCount() + list.get(i).getYaYaCount());
            dataList.add(data);
            sum += data.getSum();
            wxSum += data.getWeiXin();
            wbSum += data.getWeiBo();
            fbSum += data.getFacebook();
            twSum += data.getTwitter();
            emailSum += data.getEmail();
            mobileSum += data.getMobile();
            yaYaSum += data.getYaYa();
        }
        CspUserRegisterExcel data = new CspUserRegisterExcel();
        data.setTime("合计");
        data.setSum(sum);
        data.setWeiXin(wxSum);
        data.setWeiBo(wbSum);
        data.setFacebook(fbSum);
        data.setTwitter(twSum);
        data.setEmail(emailSum);
        data.setMobile(mobileSum);
        data.setYaYa(yaYaSum);
        dataList.add(data);
    }


    /**
     * 生成excel的日期格式
     * @param list
     * @param grain
     * @return
     */
    private List<String> buildExcelDateFormatByGrain(List<Date> list,Integer grain){
        List<String> stringList = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat monthFormat = new SimpleDateFormat("yyyy年MM月");
        DateFormat yearFormat = new SimpleDateFormat("yyyy年");
        for(Date date : list){
            //按周统计
            if(grain == CspNewlyStaticDTO.Grain.WEEK.ordinal()){
                Date endDate = CalendarUtils.getWeekLastDay(date);
                stringList.add(format.format(date) + "-" + format.format(endDate));

                //按月统计
            }else if(grain == CspNewlyStaticDTO.Grain.MONTH.ordinal()){
                stringList.add(monthFormat.format(date));

                //按季度统计
            }else if(grain == CspNewlyStaticDTO.Grain.QUARTER.ordinal()){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(Calendar.MONTH);//获取月份
                int year = cal.get(Calendar.YEAR); //获取年份
                month = month < 4 ? 1 : month < 7 ? 2 : month < 10 ? 3 : 4;
                stringList.add(year + "年Q" + month);

                //按年统计
            }else if(grain == CspNewlyStaticDTO.Grain.YEAR.ordinal()){
                stringList.add(yearFormat.format(date));

            }else{
                //按天为粒度
                stringList.add(format.format(date));
            }
        }
        return stringList;
    }

    /**
     * 获取注册用户数据
     * @param startDate
     * @param endDate
     * @param location
     * @param grain
     * @return
     */
    private List<ReportRegister> getNewlyRegisterList(Date startDate,Date endDate,Integer location,Integer grain){
        Map<String,Object> map = new HashMap<>();
        map.put("location",location);
        map.put("startTime",startDate);
        map.put("endTime",endDate);
        map.put("grain",grain);
        List<ReportRegister> list = cspUserService.findNewlyRegisterList(map);
        return list;
    }


    /**
     * 根据时间粒度获取第一天
     * @param startTime
     * @param grain
     * @return
     * @throws ParseException
     */
    private Date getFirstDayByGrain(String startTime,Integer grain) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = format.parse(startTime);

        //按周统计
        if(grain == CspNewlyStaticDTO.Grain.WEEK.ordinal()){
            startDate = CalendarUtils.getWeekFirstDay(startDate);
            //按月统计
        }else if(grain == CspNewlyStaticDTO.Grain.MONTH.ordinal()){
            startDate = CalendarUtils.getMonthFirstDay(startDate);
            //按季度统计
        }else if(grain == CspNewlyStaticDTO.Grain.QUARTER.ordinal()){
            startDate = CalendarUtils.getQuarterFirstDate(startDate);

            //按年统计
        }else if(grain == CspNewlyStaticDTO.Grain.YEAR.ordinal()){
            startDate = CalendarUtils.getCurrYearFirstDay(startDate);
        }else{
            //按天为粒度
            return startDate;
        }

        return startDate;

    }

    /**
     * 根据时间粒度获取最后一天
     * @param endTime
     * @param grain
     * @return
     * @throws ParseException
     */
    private  Date getLastDayByGrain(String endTime,Integer grain) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date endDate = format.parse(endTime);

        //按周统计
        if(grain == CspNewlyStaticDTO.Grain.WEEK.ordinal()){
            endDate = CalendarUtils.getWeekLastDay(endDate);
            //按月统计
        }else if(grain == CspNewlyStaticDTO.Grain.MONTH.ordinal()){
            endDate = CalendarUtils.getMonthLastDay(endDate);
            //按季度统计
        }else if(grain == CspNewlyStaticDTO.Grain.QUARTER.ordinal()){
            endDate = CalendarUtils.getQuarterLastDate(endDate);
            //按年统计
        }else if(grain == CspNewlyStaticDTO.Grain.YEAR.ordinal()){
            endDate = CalendarUtils.getCurrYearLastDay(endDate);
        }else{
            //按天为粒度
            return endDate;
        }
        return endDate;
    }
}
