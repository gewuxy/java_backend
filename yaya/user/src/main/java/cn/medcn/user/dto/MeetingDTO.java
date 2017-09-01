package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LiuLP on 2017/5/8
 */
@Data
@NoArgsConstructor
public class MeetingDTO implements Serializable{

    private String id;

    //主办方名称
    private String organizer;

    private String headimg;

    private String meetName;

    //会议科室类型
    private String meetType;

    private Date startTime;

    private Date endTime;

    //会议状态  0表示草稿 1表示未开始 2表示进行中 3已结束 4已关闭'
    private Integer state;



}
