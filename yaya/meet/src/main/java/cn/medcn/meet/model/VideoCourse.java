package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/5/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_video_course")
public class VideoCourse implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String title;

    private String category;

    private Boolean published;

    private Integer owner;

    private Date createTime;

    @Transient
    private List<VideoCourseDetail> details;
}
