package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_sign")
public class MeetSign implements Serializable {

    @Id
    private Integer id;
    /**签到人ID*/
    private Integer userId;
    /**会议ID*/
    private String meetId;
    /**对应的会议地址ID*/
    private Integer positionId;
    /**签到经度*/
    private Double signLng;
    /**签到维度*/
    private Double signLat;
    /**签到是否成功*/
    private Boolean signFlag;
    /**签到时间*/
    private Date signTime;

}
