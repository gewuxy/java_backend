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
    // 录播状态 0表示未开始 1表示录播中 2表示录播中断或者直播的星评阶段 3表示录播结束
    protected Integer playState;


    public enum PlayState{
        init,//未开始
        playing,//进行中
        pause,//录播中断或者直播的星评阶段
        over;//结束
    }

}
