package cn.medcn.meet.dao;

import cn.medcn.meet.dto.CourseThemeDTO;
import cn.medcn.meet.model.AudioCourseTheme;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Liuchangling on 2018/1/19.
 */
public interface AudioCourseThemeDAO extends Mapper<AudioCourseTheme> {
    /**
     * 查看课程信息及背景主题信息
     * @param courseId
     * @return
     */
    AudioCourseTheme findCourseThemeByCourseId(@Param("courseId") Integer courseId);
}
