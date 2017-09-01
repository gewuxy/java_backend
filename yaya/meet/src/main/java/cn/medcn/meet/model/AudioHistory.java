package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/2.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_audio_history")
public class AudioHistory implements Serializable{
    @Id
    private Long id;

    // 课程ID
    private Integer courseId;

    // 会议ID
    private String meetId;

    // 模块ID
    private Integer moduleId;

    // PPT明细ID
    private Integer detailId;

    // 用时
    private Integer usedtime;

    // 开始观看时间
    private Date startTime;

    // 结束观看时间
    private Date endTime;

    // 用户ID
    private Integer userId;

    // 是否已经看完
    private Boolean finished;
}
