package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_audio_course")
public class AudioCourse implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String title;
    /**资源类型*/
    private String category;
    /**是否发布*/
    private Boolean published;

    private Date createTime;
    /**所有者ID*/
    private Integer owner;
    /**是否共享*/
    private Boolean shared;
    /**共享类型 0表示免费 1表示收费 2表示奖励*/
    private Integer shareType;
    /**转载需要的象数*/
    private Integer credits;
    /**原始资料ID 转载来的资源才有的属性 非转载资源为0*/
    private Integer primitiveId;

    @Transient
    private List<AudioCourseDetail> details;
}
