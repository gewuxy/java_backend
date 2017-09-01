package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LiuLP on 2017/5/25.
 */
@Data
@NoArgsConstructor
public class ExamHistoryDTO implements Serializable{

    private String id;

    private String paperName;

    //答题用时
    private Double usedtime;

    //答题分数
    private Integer score;

    private String time;



    public String getTime() {
        return formatSecond(getUsedtime());
    }



    public static String formatSecond(Object second){
        String  html="0秒";
        if(second!=null){
            Double s=(Double) second;
            String format;
            Object[] array;
            Integer hours =(int) (s/(60*60));
            Integer minutes = (int) (s/60-hours*60);
            Integer seconds = (int) (s-minutes*60-hours*60*60);
            if(hours>0){
                format = minutes >0 ?"%1$,d小时%2$,d分":"%1$,d小时";
                array=new Object[]{hours,minutes};
            }else if(minutes>0){
                format="%1$,d分";
                array=new Object[]{minutes};
            }else{
                format="%1$,d秒";
                array=new Object[]{seconds};
            }
            html= String.format(format, array);
        }

        return html;
    }








}
