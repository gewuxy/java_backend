package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LiuLP on 2017/5/16/016.
 */

@NoArgsConstructor
@Data
public class DoctorDTO {

    private Integer id;

    private Integer groupId;
    //头像
    private String headimg;

    private String linkman;

    //科室类型
    private String type;

    //医院
    private String hospital;

    //完成的会议数量
    private Integer finishedNum;

    //学习时长
    private Double learnTime;

    //分组名称
    private String groupName;

    private String province;

    private String city;

    private String zone;


    private String mobile;

    //邮箱
    private String username;

    //医生职称
    private String title;

    //学历
    private String degree;

    //视频学习时长
    private Integer videoTime;


    //考试平均得分
    private Integer average;

    //参加会议数量
    private Integer attendCount;

    private String time;

    // 是否绑定微信ID
    private String unionid;


    public String getTime() {
        return formatSecond(getLearnTime());
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
