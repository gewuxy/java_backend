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

    // 统计标记 （全部=0 ，本月=1，本周=2，自定义=3 ）
    private Integer tagNum;

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

    public interface dateTypeNumber {
        Integer ALL = 0; // 全部
        Integer MONTH = 1; // 本月
        Integer WEEK = 2; // 本周
        Integer CUSTOM_TIME = 3; // 自定义时间
    }

    public String getAttendIndexName() {
        String indexName = null;
        if (tagNum == dateTypeNumber.ALL
                || tagNum == dateTypeNumber.CUSTOM_TIME) {
            indexName = attendDate;
        } else if (tagNum == dateTypeNumber.MONTH) {
            indexName = attendDate.substring(attendDate.lastIndexOf("-") + 1);
        } else if (tagNum == dateTypeNumber.WEEK) {
            indexName = CalendarUtils.getWeekNameByDay(Integer.parseInt(attendDate.substring(attendDate.lastIndexOf("-") + 1)));
        }
        return indexName;
    }

    // 开始时间
    public String getStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stTime = null;
        if (tagNum == dateTypeNumber.ALL) {
            stTime = format.format(CalendarUtils.getCurrYearFirstDay());
        } else if (tagNum == dateTypeNumber.MONTH) {
            stTime = format.format(CalendarUtils.getMonthFirstDay());
        } else if (tagNum == dateTypeNumber.WEEK) {
            stTime = format.format(CalendarUtils.getWeekFirstDay());
        }
        return stTime;
    }

    // 结束时间
    public String getEndTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String edTime = null;
        if (tagNum == dateTypeNumber.ALL) {
            edTime = format.format(CalendarUtils.getYearCurrentDay());
        } else if (tagNum == dateTypeNumber.MONTH) {
            edTime = format.format(CalendarUtils.getMonthLastDay());
        } else if (tagNum == dateTypeNumber.WEEK) {
            edTime = format.format(CalendarUtils.getWeekLastDay());
        }
        return edTime;
    }
}
