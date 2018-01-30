package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.*;
import cn.medcn.meet.dto.CourseThemeDTO;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.CourseThemeService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    protected AudioService audioService;

    @Value("${app.file.base}")
    protected String fileBase;


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
            audioCourse.setDetails(details);
            audioService.handleHttpUrl(fileBase,audioCourse);

            // 查询课程主题
            AudioCourseTheme courseTheme = courseThemeDAO.findCourseThemeByCourseId(courseId);
            if (courseTheme != null) {
                AudioCourseTheme.handleUrl(courseTheme,fileBase);
                themeDTO.setCourseTheme(courseTheme);
            }
        }
        return themeDTO;
    }

    @Override
    public List<BackgroundImage> findImageList() {
        List<BackgroundImage> list = courseThemeDAO.findImageList();
        return list;
    }

    @Override
    public List<BackgroundMusic> findMusicList() {
        List<BackgroundMusic> list = courseThemeDAO.findMusicList();
        return list;
    }
}
