package cn.medcn.meet.dao;

import cn.medcn.meet.dto.CourseThemeDTO;
import cn.medcn.meet.model.AudioCourseTheme;
import cn.medcn.meet.model.BackgroundImage;
import cn.medcn.meet.model.BackgroundMusic;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 主题列表
     * @return
     */
    List<BackgroundImage> findImageList(@Param("showType") Integer showType);

    /**
     * 背景音乐列表
     * @return
     */
    List<BackgroundMusic> findMusicList(@Param("showType") Integer showType);


}
