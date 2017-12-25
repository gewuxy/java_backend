package cn.medcn.user.dto;

import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.user.model.ReportRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
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
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static List<Date> buildDate(Integer grain, Date startTime, Date endTime) throws ParseException {

        List<Date> list = null;
        if(grain == CspNewlyStaticDTO.Grain.DAY.ordinal()){
            list = CalendarUtils.getAllDateList(startTime,endTime);
        }else if(grain == CspNewlyStaticDTO.Grain.WEEK.ordinal()){
            list = CalendarUtils.getWeekFirstDateList(startTime,endTime);
        }else if(grain == CspNewlyStaticDTO.Grain.MONTH.ordinal()){
            list = CalendarUtils.getMonthFirstDateList(startTime,endTime);
        }else if(grain == CspNewlyStaticDTO.Grain.QUARTER.ordinal()){
            list = CalendarUtils.getQuarterFirstDateList(startTime,endTime);
        }else if(grain == CspNewlyStaticDTO.Grain.YEAR.ordinal()){
            list = CalendarUtils.getYearFirstDateList(startTime,endTime);
        }else {
            return null;
        }

        return list;
    }



    /**
     * 格式化日期
     * @param list
     * @return
     * @throws ParseException
     */
    public  static String[] getDateCount(List<Date> list) throws ParseException {

        if(list != null){
            String[] dateCount = new String[list.size()];
            Calendar cal = Calendar.getInstance();
            for(int i=0;i<list.size();i++){
                cal.setTime(list.get(i));
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String monthString = month < 10 ?  "0"+month : month+"";
                String dayString = day < 10 ?  "0"+day : day+"";
                dateCount[i] = dayString + "-" + monthString;

            }
            return dateCount;
        }
        return null;

    }

}
