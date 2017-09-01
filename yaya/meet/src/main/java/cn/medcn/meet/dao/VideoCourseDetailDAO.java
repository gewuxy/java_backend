package cn.medcn.meet.dao;

import cn.medcn.meet.dto.VideoCourseRecordDTO;
import cn.medcn.meet.model.VideoCourseDetail;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/17.
 */
public interface VideoCourseDetailDAO extends Mapper<VideoCourseDetail> {
    /**
     * 查询视频课程下面的跟目录
     * @param courseId
     * @return
     */
    List<VideoCourseDetail> findRootDetail(@Param("courseId")Integer courseId);

    /**
     * 导出 查询视频观看人数记录
     * @param params
     * @return
     */
    List<VideoCourseRecordDTO> findVideoRecordByMeetId(Map<String,Object> params);
}
