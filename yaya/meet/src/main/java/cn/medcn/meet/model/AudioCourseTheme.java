package cn.medcn.meet.model;

import cn.medcn.common.utils.CheckUtils;
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
    @Transient
    // 推荐列表排序
    protected Integer imgRecomSort;
    @Transient
    // 更多列表排序
    protected Integer imgSort;


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
    @Transient
    // 推荐列表排序
    protected Integer recomSort;
    @Transient
    // 更多列表排序
    protected Integer sort;

    //音乐时长
    @Transient
    protected String timeStr;


    public enum ImageMusic{
        IMAGE,
        MUSIC;
    }

    // 显示类型
    public enum ShowType {
        RECOMList,// 推荐列表
        MOREList;// 更多列表
    }

    /**
     * 拼接完整的主题和背景音乐地址
     * @param theme
     * @param fileBase
     */
    public static void handleUrl(AudioCourseTheme theme, String fileBase){
        if(theme != null){
            if (CheckUtils.isNotEmpty(theme.getUrl())){
                theme.setUrl(fileBase + theme.getUrl());
            }
            if (CheckUtils.isNotEmpty(theme.getImgUrl())){
                theme.setImgUrl(fileBase + theme.getImgUrl());
            }
        }
    }

}
