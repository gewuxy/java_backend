package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @Transient
    // 图片名称
    protected String imgName;
    // 图片大小
    @Transient
    protected Integer imgSize;
    // 图片地址
    @Transient
    protected String imgUrl;

    // 背景音乐
    @Transient
    protected String name;
    // 音乐时长
    @Transient
    protected Integer duration;
    // 音乐大小
    @Transient
    protected Integer size;
    // 音乐地址
    @Transient
    protected String url;


    public enum ImageMusic{
        IMAGE,
        MUSIC;
    }


}
