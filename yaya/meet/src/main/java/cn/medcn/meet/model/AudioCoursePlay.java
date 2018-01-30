package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/10/18.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_audio_course_play")
public class AudioCoursePlay implements Serializable {

    @Id
    protected String id;
    // 课程id
    protected Integer courseId;
    // 当前播放的页码
    protected Integer playPage;
    /**
     * @see PlayState
     */
    protected Integer playState;


    public enum PlayState{
        init,//未开始
        playing,//进行中
        pause,//中单
        rating,//星评阶段
        over;//结束
    }

}
