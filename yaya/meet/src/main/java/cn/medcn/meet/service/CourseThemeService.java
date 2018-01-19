package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.CourseThemeDTO;
import cn.medcn.meet.model.AudioCourseTheme;

/**
 * Created by Liuchangling on 2018/1/19.
 * 课程主题
 */
public interface CourseThemeService extends BaseService<AudioCourseTheme> {

    CourseThemeDTO findCourseTheme(Integer courseId);
}
