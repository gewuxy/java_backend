package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LiuLP on 2017/5/19
 */
@Data
@NoArgsConstructor
public class MeetFavoriteDTO implements Serializable{

    private String id;

    private String meetName;

    //会议科室类型
    private String meetType;

    private Date startTime;

    private Date endTime;

    //会议状态  0表示草稿 1表示未开始 2表示进行中 3已结束 4已关闭'
    private Integer state;

    //会议的发布者id
    private Integer ownerId;

    //会议发布者的昵称
    private String organizer;

    //会议发布者的头像
    private String headimg;
}
