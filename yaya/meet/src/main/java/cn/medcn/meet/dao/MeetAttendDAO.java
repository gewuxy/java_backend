package cn.medcn.meet.dao;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.MeetAttend;
import cn.medcn.user.dto.UserDataDetailDTO;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface MeetAttendDAO extends Mapper<MeetAttend> {

    int findAttendCount(@Param("meetId") String meetId);

    MeetStasticDTO findMeetCount(@Param("userId") Integer userId);

    MeetStasticDTO findUserAttendCount(@Param("userId") Integer userId);

    MeetStasticDTO findReprintCount(@Param("userId") Integer userId);

    MeetStasticDTO findShareCount(@Param("userId") Integer userId);

    List<MeetDataDTO> findMeetListByPage(Map<String, Object> params);

    List<MeetAttendCountDTO> findAttendCountByTime(Map<String, Object> params);

    Integer findTotalCount(Map<String,Object> params);

    List<UserDataDetailDTO> findDataByPro(Map<String,Object> params);

    List<UserDataDetailDTO> findCityDataByPro(Map<String,Object> params);

    List<UserDataDetailDTO> findDataByHos(Map<String,Object> params);

    List<UserDataDetailDTO> findDataByTitle(Map<String,Object> params);

    List<UserDataDetailDTO> findDataByDepart(Map<String,Object> params);

    List<MeetAttendUserDTO> findAttendUserList(Map<String,Object> params);

    List<MeetAttendUserDTO> findAttendUserExcel(Map<String,Object> params);

    List<AttendMeetUserDetailDTO> findAttendUserDetailExcel(Map<String,Object> params);

    List<MeetDTO> findMeet(String meetId);

    /**
     * 根据会议ID 查询会议是否有限制分组参加
     * @param meetId
     * @return
     */
    MeetLimitDTO findGroupIdLimitByMeetId(@Param("meetId")String meetId);

    List<MeetAttendDetailDTO> findByPersonal(@Param("userId")Integer userId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<MeetAttendDetailDTO> findPublishByPersonal(@Param("userId")Integer userId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    int countPublishByPersonal(@Param("userId")Integer userId);
}
