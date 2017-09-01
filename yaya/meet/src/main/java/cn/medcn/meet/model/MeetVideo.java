package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_video")
public class MeetVideo implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String meetId;

    private Integer moduleId;

    private Integer courseId;

    @Transient
    private VideoCourse course;
}
