package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.AudioCourseThemeDAO;
import cn.medcn.meet.model.AudioCourseTheme;
import cn.medcn.meet.service.CourseThemeService;
import com.github.abel533.mapper.Mapper;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2018/1/19.
 * 课程主题
 */
@Service
public class CourseThemeServiceImpl extends BaseServiceImpl<AudioCourseTheme> implements CourseThemeService {

    protected AudioCourseThemeDAO courseThemeDAO;

    @Override
    public Mapper<AudioCourseTheme> getBaseMapper() {
        return courseThemeDAO;
    }
}
