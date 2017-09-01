package cn.medcn.meet.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.*;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.MeetAttend;
import cn.medcn.meet.model.MeetLearningRecord;
import cn.medcn.meet.model.MeetModule;
import cn.medcn.meet.service.MeetStasticService;
import cn.medcn.meet.support.UserInfoCheckHelper;
import cn.medcn.user.dto.UserDataDetailDTO;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Liuchangling on 2017/5/16.
 */
@Service
public class MeetStasticServiceImpl extends BaseServiceImpl<MeetAttend> implements MeetStasticService {

    @Autowired
    private MeetAttendDAO meetAttendDAO;

    @Autowired
    private MeetLearningRecordDAO meetLearningRecordDAO;

    @Autowired
    private VideoHistoryDAO videoHistoryDAO;

    @Autowired
    private ExamHistoryDAO examHistoryDAO;

    @Autowired
    private MeetModuleDAO meetModuleDAO;

    @Override
    public Mapper<MeetAttend> getBaseMapper() {
        return meetAttendDAO;
    }


    /**
     * 查询会议相关统计数
     *
     * @param userId
     * @return
     */
    @Override
    public MeetStasticDTO findMeetStastic(Integer userId) {
        MeetStasticDTO mtsticDto = new MeetStasticDTO();
        // 查询公众号发布的总会议数和当月发布的会议数
        MeetStasticDTO meetCount = meetAttendDAO.findMeetCount(userId);
        if (meetCount != null && meetCount.getAllMeetCount() != null) {
            mtsticDto.setAllMeetCount(meetCount.getAllMeetCount());
        } else {
            mtsticDto.setAllMeetCount(0);
        }
        if (meetCount != null && meetCount.getMonthMeetCount() != null) {
            mtsticDto.setMonthMeetCount(meetCount.getMonthMeetCount());
        } else {
            mtsticDto.setMonthMeetCount(0);
        }

        // 查询公众号发布的会议 所有参会人数 和当月参会人数
        MeetStasticDTO attendCount = meetAttendDAO.findUserAttendCount(userId);
        if (attendCount != null && attendCount.getAllAttendCount() != null) {
            mtsticDto.setAllAttendCount(attendCount.getAllAttendCount());
        } else {
            mtsticDto.setAllAttendCount(0);
        }
        if (attendCount != null && attendCount.getMonthAttendCount() != null) {
            mtsticDto.setMonthAttendCount(attendCount.getMonthAttendCount());
        } else {
            mtsticDto.setMonthAttendCount(0);
        }

        // 查询公众号分享的会议 被转载的总次数 和当月被转载的次数
        MeetStasticDTO reprintCount = meetAttendDAO.findReprintCount(userId);
        if (reprintCount != null && reprintCount.getAllReprintCount() != null) {
            mtsticDto.setAllReprintCount(reprintCount.getAllReprintCount());
        } else {
            mtsticDto.setAllReprintCount(0);
        }
        if (reprintCount != null && reprintCount.getMonthReprintCount() != null) {
            mtsticDto.setMonthReprintCount(reprintCount.getMonthReprintCount());
        } else {
            mtsticDto.setMonthReprintCount(0);
        }


        // 查询公众号分享的所有会议数 和当月分享的会议数

        return mtsticDto;
    }


    /**
     * 分页查询我的会议列表
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetDataDTO> findMyMeetList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<MeetDataDTO> page = MyPage.page2Mypage((Page) meetAttendDAO.findMeetListByPage(pageable.getParams()));
        return page;
    }

    /**
     * 统计所有、本月、本周、某个时间段内 公众号的所有会议的参会人数
     *
     * @param params
     * @return
     */
    @Override
    public List<MeetAttendCountDTO> findAttendCountByTag(Map<String, Object> params) {
        Integer tagNum = (Integer) params.get("tagNum");
        if (tagNum == null) tagNum = 0;

        List<MeetAttendCountDTO> attendList = meetAttendDAO.findAttendCountByTag(params);
        if (!CheckUtils.isEmpty(attendList)) {
            for (MeetAttendCountDTO attendCountDTO : attendList) {
                attendCountDTO.setTagNum(tagNum);
            }
            return attendList;
        } else {
            return null;
        }
    }

    /**
     * 查询某会议的所有参会人数 、某会议特定的省份下的参会人数
     *
     * @param params
     * @return
     */
    @Override
    public Integer findTotalCount(Map<String, Object> params) {
        Integer totalCount = meetAttendDAO.findTotalCount(params);
        return totalCount;
    }

    /**
     * 查询参会的所有用户 所在省市的占比数
     *
     * @param params
     * @return
     */
    @Override
    public List<UserDataDetailDTO> findDataByPro(Map<String, Object> params) {
        List<UserDataDetailDTO> prolist = meetAttendDAO.findDataByPro(params);
        return prolist;
    }

    @Override
    public List<UserDataDetailDTO> findCityDataByPro(Map<String, Object> params) {
        List<UserDataDetailDTO> ctylist = meetAttendDAO.findCityDataByPro(params);
        return ctylist;
    }

    /**
     * 查询参会用户 所在医院级别的占比数
     *
     * @param params
     * @return
     */
    @Override
    public List<UserDataDetailDTO> findDataByHos(Map<String, Object> params) {
        List<UserDataDetailDTO> hoslist = meetAttendDAO.findDataByHos(params);
        return hoslist;
    }

    /**
     * 查询参会用户 职称的占比数
     *
     * @param params
     * @return
     */
    @Override
    public List<UserDataDetailDTO> findDataByTitle(Map<String, Object> params) {
        List<UserDataDetailDTO> titlist = meetAttendDAO.findDataByTitle(params);
        return titlist;
    }

    /**
     * 查询参会用户 所在科室的占比数
     *
     * @param params
     * @return
     */
    @Override
    public List<UserDataDetailDTO> findDataByDepart(Map<String, Object> params) {
        List<UserDataDetailDTO> deptlist = meetAttendDAO.findDataByDepart(params);
        return deptlist;
    }

    @Override
    public List<UserDataDetailDTO> findUserDataList(Map<String,Object> params){
        Integer propNum = (Integer) params.get("propNum");
        if (propNum == null) {
            propNum = UserDataDetailDTO.conditionNumber.PRO_OR_CITY;
        }
        String province = (String)params.get("province");
        List<UserDataDetailDTO> list;
        if (propNum == UserDataDetailDTO.conditionNumber.PRO_OR_CITY) {
            if (province.equals(UserDataDetailDTO.conditionNumber.DEFAULT_CITY)) {
                list = meetAttendDAO.findDataByPro(params);
            } else {
                list = meetAttendDAO.findCityDataByPro(params);
            }
        } else if (propNum == UserDataDetailDTO.conditionNumber.HOSPITAL) {
            list = meetAttendDAO.findDataByHos(params);
        } else if (propNum == UserDataDetailDTO.conditionNumber.USER_TITLE) {
            list = meetAttendDAO.findDataByTitle(params);
        } else {
            list = meetAttendDAO.findDataByDepart(params);
        }
        return list;
    }

    @Override
    public List<MeetDTO> findMeet(String meetId) {
        List<MeetDTO> mtlist = meetAttendDAO.findMeet(meetId);
        String modName = "";
        List<MeetDTO> list = new ArrayList<MeetDTO>();
        ;
        MeetDTO mdto;
        for (MeetDTO m : mtlist) {
            mdto = new MeetDTO();
            modName += m.getModuleName() + "/";
            mdto.setId(m.getId());
            mdto.setMeetName(m.getMeetName());
            mdto.setModuleName(m.getModuleName());
            mdto.setMeetType(m.getMeetType());
            mdto.setMeetTime(m.getMeetTime());
            mdto.setFunctionId(m.getFunctionId());
            mdto.setModuleId(m.getModuleId());
            mdto.setModuleNames(modName);
            list.add(mdto);
        }

        return list;
    }

    @Override
    public MyPage<MeetAttendUserDTO> findAttendUserList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<MeetAttendUserDTO> page = MyPage.page2Mypage((Page) meetAttendDAO.findAttendUserList(pageable.getParams()));
        return page;
    }

    @Override
    public List<MeetAttendUserDTO> findAttendUserExcel(String meetId, Integer userId) {
        Map<String, Object> map = new HashMap();
        map.put("meetId", meetId);
        map.put("userId", userId);
        List<MeetAttendUserDTO> list = meetAttendDAO.findAttendUserExcel(map);
        return list;
    }

    /**
     * 导出参会用户信息 学习完成进度
     *
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    @ResponseBody
    public List<AttendMeetUserDetailDTO> findAttendUserDetailExcel(String meetId, Integer userId) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("meetId", meetId);
        conditionMap.put("userId", userId);
        List<AttendMeetUserDetailDTO> list = meetAttendDAO.findAttendUserDetailExcel(conditionMap);
        return list == null ? null : list;
    }

    /**
     * 查询个人参会统计
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<MeetAttendDetailDTO> findAttendDetailByPersonal(Integer userId, Date startDate, Date endDate) {
        return meetAttendDAO.findByPersonal(userId, startDate, endDate);
    }

    /**
     * 查询个人参会统计
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public MeetAttendDTO findAttendByPersonal(Integer userId, Date startDate, Date endDate) {
        MeetAttend condition = new MeetAttend();
        condition.setUserId(userId);
        int totalCount = selectCount(condition);
        MeetAttendDTO meetAttendDTO = new MeetAttendDTO();
        meetAttendDTO.setTotalCount(totalCount);
        List<MeetAttendDetailDTO> details = findAttendDetailByPersonal(userId, startDate, endDate);
        int unitCount = 0;
        for (MeetAttendDetailDTO detailDTO : details) {
            unitCount += detailDTO.getCount();
        }
        meetAttendDTO.setUnitCount(unitCount);
        meetAttendDTO.setDetailList(details);
        return meetAttendDTO;
    }

    /**
     * 统计当前用户关注的单位号发布的会议
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public MeetAttendDTO findPublishByPersonal(Integer userId, Date startDate, Date endDate) {
        int totalCount = meetAttendDAO.countPublishByPersonal(userId);
        MeetAttendDTO meetAttendDTO = new MeetAttendDTO();
        meetAttendDTO.setTotalCount(totalCount);
        List<MeetAttendDetailDTO> details = meetAttendDAO.findPublishByPersonal(userId, startDate, endDate);
        int unitCount = 0;
        for (MeetAttendDetailDTO detailDTO : details) {
            unitCount += detailDTO.getCount();
        }
        meetAttendDTO.setUnitCount(unitCount);
        meetAttendDTO.setDetailList(details);
        return meetAttendDTO;
    }

    /**
     * 个人参会统计
     *
     * @param userId
     * @param offset
     * @return
     */
    @Override
    public MeetAttendDTO findFinalAttendByPersonal(Integer userId, Integer offset) {
        MeetAttendDTO meetAttendDTO = findAttendByPersonal(userId,
                CalendarUtils.getWeekStartByOffset(offset),
                CalendarUtils.getWeekEndByOffset(offset));
        meetAttendDTO.setDateArray(CalendarUtils.getWeekStartToEndByOffset(offset));
        meetAttendDTO.initCountArray();
        return meetAttendDTO;
    }

    /**
     * 关注的单位号发布会议统计
     *
     * @param userId
     * @param offset
     * @return
     */
    @Override
    public MeetAttendDTO findFinalPublishByPersonal(Integer userId, Integer offset) {
        MeetAttendDTO meetAttendDTO = findPublishByPersonal(userId,
                CalendarUtils.getWeekStartByOffset(offset),
                CalendarUtils.getWeekEndByOffset(offset));
        meetAttendDTO.setDateArray(CalendarUtils.getWeekStartToEndByOffset(offset));
        meetAttendDTO.initCountArray();
        return meetAttendDTO;
    }
}
