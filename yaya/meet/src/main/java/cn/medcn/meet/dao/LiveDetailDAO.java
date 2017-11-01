package cn.medcn.meet.dao;

import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.model.LiveDetail;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/10/31.
 */
public interface LiveDetailDAO extends Mapper<LiveDetail> {

    List<AudioCourseDetail> findByCourseId(@Param("courseId") Integer courseId);

    Integer findMaxLiveDetailSort(@Param("courseId") Integer courseId);
}
