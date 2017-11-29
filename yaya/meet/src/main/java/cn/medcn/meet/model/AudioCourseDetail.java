package cn.medcn.meet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_audio_course_detail")
public class AudioCourseDetail implements Serializable{
    @Id
    private Integer id;
    /**排序号*/
    private Integer sort;
    /**ppt图片地址*/
    private String imgUrl;
    /**音频地址*/
    private String audioUrl;
    /**视频地址*/
    private String videoUrl;
    /**开始时间点 作为打点切割备用数据*/
    private Integer startpoint;
    /**结束时间点 作为打点切割备用数据*/
    private Integer endpoint;
    /**音频时长*/
    private Integer duration;
    /**所属课程ID*/
    private Integer courseId;

    @Transient
    @JsonIgnore
    protected Boolean temp = false;//标识当前页是不是临时页
}
