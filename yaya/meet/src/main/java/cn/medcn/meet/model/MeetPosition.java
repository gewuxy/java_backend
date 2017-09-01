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
@Table(name = "t_meet_position")
public class MeetPosition implements Serializable {

    @Id
    private Integer id;

    private String meetId;

    private Integer moduleId;

    private Double positionLng;

    private Double positionLat;

    private String positionName;
    /**签到开始时间*/
    private Date startTime;
    /**签到结束时间*/
    private Date endTime;
}
