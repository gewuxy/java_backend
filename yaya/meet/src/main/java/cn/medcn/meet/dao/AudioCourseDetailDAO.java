package cn.medcn.meet.dao;

import cn.medcn.common.pagination.Pageable;
import cn.medcn.meet.dto.AudioHistoryDTO;
import cn.medcn.meet.dto.AudioRecordDTO;
import cn.medcn.meet.model.AudioCourseDetail;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface AudioCourseDetailDAO extends Mapper<AudioCourseDetail> {

    List<AudioCourseDetail> findDetailsByCourseId(@Param("courseId")Integer courseId);

    /**
     * 批量增加排序号
     * @param courseId
     * @param startSort
     */
    void updateBatchAddSort(@Param("courseId") Integer courseId, @Param("startSort") Integer startSort);

    /**
     * 批量减少明细排序号
     * @param courseId
     * @param startSort
     */
    void updateBatchDecreaseSort(@Param("courseId") Integer courseId, @Param("startSort") Integer startSort);

    /**
     * 查询ppt完整观看人数（全部、本月、本周）
     * @param params
     * @return
     */
    List<AudioHistoryDTO> findViewPptCount(Map<String,Object> params);

    List<AudioRecordDTO> findViewAudioList(Map<String,Object> params);

    /**
     * 查询ppt观看时长明细
     * @param params
     * @return
     */
    List<AudioRecordDTO> findAudioRecordList(Map<String,Object> params);

    /**
     * 查询观看ppt总人数
     * @param meetId
     * @return
     */
    List findViewCount(String meetId);

    /**
     * 查询会议ppt总页数
     * @param meetId
     * @return
     */
    List<AudioCourseDetail> findPPtTotalCount(String meetId);

    List<AudioRecordDTO> findFinishedPPtCount(Map<String,Object> params);

    /**
     * 根据讲本ID获取第一页的封面
     * @param courseId
     * @return
     */
    String getCoverUrl(@Param("courseId") Integer courseId);

}
