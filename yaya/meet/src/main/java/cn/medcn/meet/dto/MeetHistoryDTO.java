package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuLP on 2017/5/25.
 */
@Data
@NoArgsConstructor
public class MeetHistoryDTO implements Serializable{

    private String meetId;
    /**会议名称*/
    private String meetName;

    private String meetType;

    /**开始时间*/
    private Date publishTime;

    //该会议的学习时长
    private Double usedtime;

//    //会议的类型
//    private List<MeetModuleDTO> moduleList;

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

//    public String getMeetType(){
//        String result = "";
//
//        if(getModuleList() != null){
//
//            for(MeetModuleDTO dto : getModuleList()){
//                result += dto.getModuleName()+"、";
//            }
//            int end = result.lastIndexOf("、");
//            return end>0?result.substring(0,end):result;
//        }
//        return result;
//    }



}


