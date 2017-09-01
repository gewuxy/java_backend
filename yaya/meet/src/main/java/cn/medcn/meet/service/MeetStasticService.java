package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.model.MeetAttend;
import cn.medcn.user.dto.UserDataDetailDTO;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by Liuchangling on 2017/5/16.
 */

public interface MeetStasticService extends BaseService<MeetAttend>{
    /**
     * 查询会议相关统计数
     * @param userId
     * @return
     */
    MeetStasticDTO findMeetStastic(Integer userId);


    /**
     * 分页查询我的会议列表
     * @param pageable
     * @return
     */
    MyPage<MeetDataDTO> findMyMeetList(Pageable pageable);

    /**
     * 统计所有、本月、本周、某个时间段内 公众号的所有会议的参会人数
     * @param params
     * @return
     */
    List<MeetAttendCountDTO> findAttendCountByTag(Map<String, Object> params);

    /**
     * 查询某会议的所有参会人数 、某会议特定的省份下的参会人数
     * @param params
     * @return
     */
    Integer findTotalCount(Map<String,Object> params);


    /**
     * 查询参会的所有用户 所在省市的占比数
     * @param params
     * @return
     */
    List<UserDataDetailDTO> findDataByPro(Map<String,Object> params);

    List<UserDataDetailDTO> findCityDataByPro(Map<String,Object> params);

    /**
     * 查询参会用户 所在医院级别的占比数
     * @param params
     * @return
     */
    List<UserDataDetailDTO> findDataByHos(Map<String,Object> params);

    /**
     * 查询参会用户 职称的占比数
     * @param params
     * @return
     */
    List<UserDataDetailDTO> findDataByTitle(Map<String,Object> params);

    /**
     * 查询参会用户 所在科室的占比数
     * @param params
     * @return
     */
    List<UserDataDetailDTO> findDataByDepart(Map<String,Object> params);

    List<UserDataDetailDTO> findUserDataList(Map<String,Object> params);

    List<MeetDTO> findMeet(String meetId);

    /**
     * 查询参会用户信息列表
     * @param pageable
     * @return
     */
    MyPage<MeetAttendUserDTO> findAttendUserList(Pageable pageable);

    List<MeetAttendUserDTO> findAttendUserExcel(String meetId,Integer userId);

    /**
     * 导出参会用户模块完成进度
     * @param meetId
     * @param userId
     * @return
     */
    List<AttendMeetUserDetailDTO> findAttendUserDetailExcel(String meetId,Integer userId);


    /**
     * 查询个人参会明细统计
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<MeetAttendDetailDTO> findAttendDetailByPersonal(Integer userId, Date startDate, Date endDate);

    /**
     * 查询个人参会统计
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    MeetAttendDTO findAttendByPersonal(Integer userId, Date startDate, Date endDate);

    /**
     * 统计当前用户关注的单位号发布的会议
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    MeetAttendDTO findPublishByPersonal(Integer userId, Date startDate, Date endDate);

    /**
     * 个人参会统计
     * @param userId
     * @param offset
     * @return
     */
    MeetAttendDTO findFinalAttendByPersonal(Integer userId, Integer offset);

    /**
     * 关注的单位号发布会议统计
     * @param userId
     * @param offset
     * @return
     */
    MeetAttendDTO findFinalPublishByPersonal(Integer userId, Integer offset);
}
