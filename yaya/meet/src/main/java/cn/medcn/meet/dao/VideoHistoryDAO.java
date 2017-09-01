package cn.medcn.meet.dao;

import cn.medcn.meet.dto.VideoProgressDTO;
import cn.medcn.meet.model.VideoHistory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/18.
 */
public interface VideoHistoryDAO extends Mapper<VideoHistory> {

    /**
     * 查询 视频观看进度 记录
     * @param map
     * @return
     */
    List<VideoProgressDTO> findVProgressByMeetId(Map<String,Object> map);

    /**
     * 查询用户观看视频总时长
     * @param userId
     * @param meetId
     * @return
     */
    Integer findUserVideoWatchTime(@Param("meetId") String meetId,@Param("userId") Integer userId);

    /**
     * 查询会议视频总时长
     * @param meetId
     * @return
     */
    Integer findVideoTotalTime(@Param("meetId") String meetId);
}
