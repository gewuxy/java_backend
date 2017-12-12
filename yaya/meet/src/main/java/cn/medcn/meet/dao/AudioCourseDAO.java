package cn.medcn.meet.dao;

import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.CourseReprintDTO;
import cn.medcn.meet.dto.CourseSharedDTO;
import cn.medcn.meet.dto.ResourceCategoryDTO;
import cn.medcn.meet.model.AudioCourse;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/27.
 */
public interface AudioCourseDAO extends Mapper<AudioCourse>{

    /**
     * 查找所有的共享资源
     * @param params
     * @return
     */
    List<CourseReprintDTO> findResource(Map<String, Object> params);

    /**
     * 查询所有的资源类别
     * @return
     */
    List<ResourceCategoryDTO> findResourceCategorys(@Param("userId") Integer userId);

    /**
     * 我的转载记录
     * @param params
     * @return
     */
    List<CourseReprintDTO> findMyReprints(Map<String, Object> params);

    /**
     * 查询我的分享记录
     * @param params
     * @return
     */
    List<CourseSharedDTO> findMyShared(Map<String, Object> params);

    /**
     * 查询我的被转载记录
     * @param params
     * @return
     */
    List<CourseReprintDTO> findMyReprinted(Map<String, Object> params);

    /**
     * 查询csp会议列表
     * @param params
     * @return
     */
    List<CourseDeliveryDTO> findCspMeetingList(Map<String, Object> params);

    /**
     * 查询最近未完善的PPT
     * @param cspUserId
     * @return
     */
    AudioCourse findLastDraft(@Param("cspUserId")String cspUserId);

    /**
     * 投稿给指定用户的会议列表
     * @param params
     * @return
     */
    List<CourseDeliveryDTO> findHistoryDeliveryByAcceptId(Map<String, Object> params);

    /**
     * csp后台获取会议列表
     * @param params
     * @return
     */
    List<AudioCourse> findAllMeetForManage(Map<String, Object> params);

    /**
     * csp后台
     * @param id
     * @return
     */
    CourseDeliveryDTO findMeetDetail(@Param("id")Integer id);

    List<AudioCourse> findAudioCourseList(@Param("cspUserId") String cspUserId);
}
