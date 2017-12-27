package cn.medcn.user.dto;

import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.user.model.ReportRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.SystemException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * csp新增用户统计
 * Created by LiuLP on 2017/12/19/019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CspNewlyStaticDTO {

    private String[] dateCount;

    private List<ReportRegister> list;


    //时间粒度，日，周，月,季，年
    public enum Grain{
        DAY,
        WEEK,
        MONTH,
        QUARTER,
        YEAR;
    }


    /**
     * 根据时间粒度和起始时间获取对应的时间段
     * @param grain
     * @return
     * @throws ParseException
     */
    public static String[] buildDate(Integer grain,  List<ReportRegister> list) throws ParseException, cn.medcn.common.excptions.SystemException {
        String[] dateCount = new String[list.size()];
        DateFormat format = new SimpleDateFormat("MM-dd");
        Date date = null;
        for(int i = 0; i < list.size(); i++){
            Date registerTime = list.get(i).getRegisterTime();
            if(grain == CspNewlyStaticDTO.Grain.DAY.ordinal()){
                date = registerTime;
            }else if(grain == CspNewlyStaticDTO.Grain.WEEK.ordinal()){
                date = CalendarUtils.getWeekFirstDay(registerTime);
            }else if(grain == CspNewlyStaticDTO.Grain.MONTH.ordinal()){
                date = CalendarUtils.getMonthFirstDay(registerTime);
            }else if(grain == CspNewlyStaticDTO.Grain.QUARTER.ordinal()){
                date = CalendarUtils.getQuarterFirstDate(registerTime);
            }else if(grain == CspNewlyStaticDTO.Grain.YEAR.ordinal()){
                date = CalendarUtils.getCurrYearFirstDay(registerTime);
            }else {
                throw new cn.medcn.common.excptions.SystemException("时间粒度错误");
            }
            dateCount[i] = format.format(date);
        }


        return dateCount;
    }





}
