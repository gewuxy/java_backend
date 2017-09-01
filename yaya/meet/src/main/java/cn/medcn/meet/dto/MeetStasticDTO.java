package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/5/16.
 */
@Data
@NoArgsConstructor
public class MeetStasticDTO implements Serializable{

    // 本月发布的会议数
    private Integer monthMeetCount;

    // 发布的会议总数
    private Integer allMeetCount;

    // 本月参与会议人数
    private Integer monthAttendCount;

    // 参与会议总人数
    private Integer allAttendCount;

    // 本月被转载的会议次数
    private Integer monthReprintCount;

    // 被转载的会议总次数
    private Integer allReprintCount;

    // 本月分享的会议次数
    private Integer monthShareCount;

    // 分享的会议总次数
    private Integer allShareCount;


}
