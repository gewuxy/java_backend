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
    // 课程明细
    protected List<AudioCourseDetail> details;
    // 课程主题背景
    protected AudioCourseTheme courseTheme;

}
