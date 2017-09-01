package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/18.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_video_history")
public class VideoHistory implements Serializable{
    @Id
    private Integer id;

    private Integer userId;

    private Integer detailId;

    private String meetId;

    private Integer moduleId;

    private Integer courseId;

    private Boolean finished;

    private Integer usedtime;

    private Date startTime;

    private Date endTime;
}
