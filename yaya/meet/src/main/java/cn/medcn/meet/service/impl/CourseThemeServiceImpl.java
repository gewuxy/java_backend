package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.AudioCourseDAO;
import cn.medcn.meet.dao.AudioCourseDetailDAO;
import cn.medcn.meet.dao.AudioCourseThemeDAO;
import cn.medcn.meet.dto.CourseThemeDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.model.AudioCourseTheme;
import cn.medcn.meet.service.CourseThemeService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liuchangling on 2018/1/19.
 * 课程主题
 */
@Service
public class CourseThemeServiceImpl extends BaseServiceImpl<AudioCourseTheme> implements CourseThemeService {
    @Autowired
    protected AudioCourseThemeDAO courseThemeDAO;

    @Autowired
    protected AudioCourseDAO audioCourseDAO;

    @Autowired
    protected AudioCourseDetailDAO audioCourseDetailDAO;

    @Override
    public Mapper<AudioCourseTheme> getBaseMapper() {
        return courseThemeDAO;
    }

    /**
     * 预览课程明细及主题信息
     * @param courseId
     * @return
     */
    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'audio_course_'+#courseId")
    public CourseThemeDTO findCourseTheme(Integer courseId) {
        AudioCourse audioCourse = audioCourseDAO.selectByPrimaryKey(courseId);
        CourseThemeDTO themeDTO = null;
        if (audioCourse != null) {
            themeDTO = new CourseThemeDTO();
            themeDTO.setAudioCourse(audioCourse);

            // 查询课程明细
            List<AudioCourseDetail> details = audioCourseDetailDAO.findDetailsByCourseId(courseId);
            themeDTO.setDetails(details);

            // 查询课程主题
            AudioCourseTheme courseTheme = courseThemeDAO.findCourseThemeByCourseId(courseId);
            if (courseTheme != null) {
                themeDTO.setCourseTheme(courseTheme);
            }
        }
        return themeDTO;
    }
}
