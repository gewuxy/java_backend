package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/25.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_attend")
public class MeetAttend implements Serializable {
    @Id
    private Integer id;
    /**会议ID*/
    private String meetId;
    /**用户ID*/
    private Integer userId;
    /**开始时间*/
    private Date startTime;
    /**结束时间*/
    private Date endTime;
    /**用时*/
    private Integer usetime;
    /**会议模块ID*/
    private Integer moduleId;
}
