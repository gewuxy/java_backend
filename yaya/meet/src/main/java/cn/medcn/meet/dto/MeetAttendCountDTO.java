package cn.medcn.meet.dto;

import cn.medcn.common.utils.CalendarUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Liuchangling on 2017/5/23.
 */
@Data
@NoArgsConstructor
public class MeetAttendCountDTO implements Serializable {

    // 统计方式 （全部=0 ，本月=1，本周=2，自定义=3 ）
    private Integer dateType;

    // 关注日期
    private String attendDate;

    // 关注人数
    private Integer attendCount;

    // 开始时间
    private String startTime;

    // 结束时间
    private String endTime;

    // 下标名称
    private String attendIndexName;

    public interface DateTypeNumber {
        Integer ALL = 0; // 全部
        Integer MONTH = 1; // 本月
        Integer WEEK = 2; // 本周
        Integer CUSTOM_TIME = 3; // 自定义时间
    }

    public String getAttendIndexName() {
        String indexName = null;
        if (dateType == DateTypeNumber.ALL
                || dateType == DateTypeNumber.CUSTOM_TIME) {
            indexName = attendDate;
        } else if (dateType == DateTypeNumber.MONTH) {
            indexName = attendDate.substring(attendDate.lastIndexOf("-") + 1);
        } else if (dateType == DateTypeNumber.WEEK) {
            indexName = CalendarUtils.getWeekNameByDay(Integer.parseInt(attendDate.substring(attendDate.lastIndexOf("-") + 1)));
        }
        return indexName;
    }

    // 开始时间
    public String getStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stTime = null;
        if (dateType == DateTypeNumber.ALL) {
            stTime = format.format(CalendarUtils.getCurrYearFirstDay());
        } else if (dateType == DateTypeNumber.MONTH) {
            stTime = format.format(CalendarUtils.getMonthFirstDay());
        } else if (dateType == DateTypeNumber.WEEK) {
            stTime = format.format(CalendarUtils.getWeekFirstDay());
        }
        return stTime;
    }

    // 结束时间
    public String getEndTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String edTime = null;
        if (dateType == DateTypeNumber.ALL) {
            edTime = format.format(CalendarUtils.getYearCurrentDay());
        } else if (dateType == DateTypeNumber.MONTH) {
            edTime = format.format(CalendarUtils.getMonthLastDay());
        } else if (dateType == DateTypeNumber.WEEK) {
            edTime = format.format(CalendarUtils.getWeekLastDay());
        }
        return edTime;
    }
}
