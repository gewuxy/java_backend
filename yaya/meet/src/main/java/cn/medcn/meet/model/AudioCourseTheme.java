package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Liuchangling on 2018/1/19.
 * 课件主题 （背景图片 + 背景音乐）
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_audio_course_theme")
public class AudioCourseTheme {
    @Id
    protected Integer id;
    // 课程id
    protected Integer courseId;
    // 背景图片id
    protected Integer imageId;
    // 背景音乐id
    protected Integer musicId;

}
