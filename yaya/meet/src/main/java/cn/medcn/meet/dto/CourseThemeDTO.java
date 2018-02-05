package cn.medcn.meet.dto;

import cn.medcn.meet.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liuchangling on 2018/1/19.
 */
@Data
@NoArgsConstructor
public class CourseThemeDTO implements Serializable {
    // 课程
    protected AudioCourse audioCourse;

    // 课程主题背景
    protected AudioCourseTheme courseTheme;

    //课件总时长
    protected Integer duration;

}
