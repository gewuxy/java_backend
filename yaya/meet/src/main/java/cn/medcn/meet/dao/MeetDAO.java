package cn.medcn.meet.dao;

import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.Lecturer;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.model.MeetSetting;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/20.
 */
public interface MeetDAO extends Mapper<Meet> {

    List<MeetTuijianDTO> findMeetTuijian(@Param("userId")Integer userId);

    List<MeetTypeState> findMeetTypes(@Param("userId")Integer userId);

    MeetInfoDTO findMeetInfo(@Param("meetId")String meetId);

    List<MeetInfoDTO> searchMeetInfo(Map<String, Object> params);

    List<MeetFolderDTO> searchMeetFolderInfo(Map<String, Object> params);

    List<MeetInfoDTO> findMyMeets(Map<String, Object> params);

    List<MeetInfoDTO> findMyStore(Map<String, Object> params);

    /**
     * 查询已经发布的会议列表
     * @param params
     * @return
     */
    List<MeetListInfoDTO> findPublished(Map<String, Object> params);

    List<MeetListInfoDTO> findMeetForSend(Map<String, Object> params);

    /**
     * 查询草稿箱
     * @param params
     * @return
     */
    List<MeetListInfoDTO> findDraft(Map<String, Object> params);

    /**
     * 获取医生的会议历史(医生详情)
     * @param params
     * @return
     */
    List<MeetHistoryDTO> getMeetHistory(Map<String, Object> params);

    /**
     * 查询会议签到地址
     * @param meetId
     * @return
     */
    String findSignPos(@Param("meetId")String meetId);


    void modifyStartState();

    void modifyEndState();


    /**
     * 查询 我的会议列表 条件可能是状态 或者 会议类型
     * @param params
     * @return
     */
    List<MeetInfoDTO> findMyMeetList(Map<String, Object> params);

    /**
     * 查询会议奖励象数或学分属性内容
     * @param params
     * @return
     */
    List<MeetSetting> findMeetSetting(Map<String,Object> params);

    /**
     * 查询个人收藏会议
     * @param params
     * @return
     */
    List<MeetInfoDTO> findMeetFavorite(Map<String, Object> params);

    MeetFolderDTO findFixedRecommend();

    /**
     * 查询出所有的推荐的会议 排除掉固定的
     * @return
     */
    List<MeetFolderDTO> findRandomRecommendMeets(@Param("randLimit") Integer randLimit);

    /**
     * 获取指定长度的随机会议
     * @param searchRandomMeetDTO
     * @return
     */
    List<MeetFolderDTO> findRandomMeets(SearchRandomMeetDTO searchRandomMeetDTO);

    List<Lecturer> findRecommendLecturers(@Param("folderId")String folderId, @Param("max")Integer max);

    MeetFolderDTO getMeetFolder(@Param("id") String id);

}
