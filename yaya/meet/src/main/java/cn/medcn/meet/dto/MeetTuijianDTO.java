package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/20.
 */
@Data
@NoArgsConstructor
public class MeetTuijianDTO implements Serializable{

    private String id;
    /**会议名称*/
    private String meetName;
    /**科室类别*/
    private String meetType;
    /**开始时间*/
    private Date startTime;
    /**结束时间*/
    private Date endTime;
    /**主讲者姓名*/
    private String lecturer;
    /**主讲者职位*/
    private String lecturerTile;

    private String lecturerHos;

    private String lecturerDepart;
    /**主讲者头像*/
    private String lecturerImg;
    /**会议状态 0表示草稿 1表示未开始 2表示进行中 3表示已结束*/
    private Integer state;

    private String pubUserHead;

    private Integer pubUserId;
    /**是否收藏*/
    private Integer stored;
}
