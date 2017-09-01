package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_audio")
public class MeetAudio implements Serializable {

    @Id
    private Integer id;

    private String meetId;

    private Integer moduleId;

    private Integer courseId;

    @Transient
    private AudioCourse course;
}
