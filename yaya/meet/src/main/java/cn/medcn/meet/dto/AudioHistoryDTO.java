package cn.medcn.meet.dto;

import cn.medcn.common.utils.CalendarUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by Liuchangling on 2017/6/7.
 * 观看ppt人数统计
 */
@Data
@NoArgsConstructor
public class AudioHistoryDTO implements Serializable{

    // ppt序号
    private Integer pptSort;

    // 完整观看人数
    private Integer userCount;

    // 统计标记 （全部=0 ，本月=1，本周=2，自定义=3 ）
    private Integer tagNo;

    // 开始时间
    private String startTime;

    // 结束时间
    private String endTime;

    // 开始时间
    public String getStartTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stTime = null ;
        if (tagNo == 0){
            stTime = format.format(CalendarUtils.getCurrYearFirstDay());
        } else if (tagNo == 1){
            stTime = format.format(CalendarUtils.getMonthFirstDay());
        } else if (tagNo == 2){
            stTime = format.format(CalendarUtils.getWeekFirstDay());
        }
        return stTime;
    }

    // 结束时间
    public String getEndTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String edTime = null ;
        if (tagNo == 0){
            edTime = format.format(CalendarUtils.getYearCurrentDay());
        } else if (tagNo == 1){
            edTime = format.format(CalendarUtils.getMonthLastDay());
        } else if (tagNo == 2){
            edTime = format.format(CalendarUtils.getWeekLastDay());
        }
        return edTime;
    }
}
