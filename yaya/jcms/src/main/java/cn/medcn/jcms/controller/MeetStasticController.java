package cn.medcn.jcms.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.*;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.MeetModule;
import cn.medcn.meet.service.*;
import cn.medcn.meet.support.UserInfoCheckHelper;
import cn.medcn.user.dto.UserDataDetailDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Liuchangling on 2017/5/16.
 * 会议统计模块
 */
@Controller
@RequestMapping(value = "/data/state")
public class MeetStasticController extends BaseController {

    @Autowired
    private MeetStatsService meetStatsService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private ExamService examService;

    @Autowired
    private VideoService videoService;

    private static final String UNFINISHED = "未完成";

    /**
     * 会议统计 记录数统计
     *
     * @return
     */
    @RequestMapping(value = "/meet")
    public String meetStatistics() {
        Principal principal = SubjectUtils.getCurrentUser();
        return "/tongji/meetStastic";
    }

    /**
     * 会议统计--> 会议发布数、参与人数、被转载次数 统计
     *
     * @return
     */
    @RequestMapping(value = "/statistics")
    @ResponseBody
    public String statistics() {
        Integer userId = SubjectUtils.getCurrentUserid();
        MeetStasticDTO staticsDTO = meetStatsService.findMeetStastic(userId);
        return APIUtils.success(staticsDTO);
    }

    /**
     * 会议统计--> 我的会议
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/meetList")
    @ResponseBody
    public String getMeetDataList(Pageable pageable) {
        Integer userId = SubjectUtils.getCurrentUserid();
        pageable.put("userId", userId);
        MyPage<MeetDataDTO> page = meetStatsService.findMyMeetList(pageable);
        return APIUtils.success(page);
    }

    /**
     * 会议详情-> 头部会议部分信息
     *
     * @param meetId
     * @return
     */
    @RequestMapping(value = "/meets")
    @ResponseBody
    public String meets(String meetId) {
        return APIUtils.success(meetStatsService.findMeet(meetId));
    }

    /**
     * 折线图 会议参与人数统计 根据本月、本周、自定义日期
     * @param tagNum  本月、本周、自定义日期、全部 标识号码
     * @param startTime 开始时间
     * @param endTime  结束时间
     * @param meetId  会议ID
     * @return
     */
    @RequestMapping(value = "/attend/meet/statistics")
    @ResponseBody
    public String attendMeetStatistics(Integer tagNum,String startTime, String endTime, String meetId) {
        Integer userId = SubjectUtils.getCurrentUserid();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("userId", userId);
        conditionMap.put("tagNum", tagNum);

        Integer customNum = MeetAttendCountDTO.dateTypeNumber.CUSTOM_TIME;
        MeetAttendCountDTO attendCountDTO = new MeetAttendCountDTO();
        attendCountDTO.setTagNum(tagNum);
        if (tagNum < customNum) { // 不等于自定义时间
            startTime = attendCountDTO.getStartTime();
            endTime = attendCountDTO.getEndTime();
        }

        if (!StringUtils.isBlank(startTime)) {
            conditionMap.put("startTime", startTime);
        }
        if (!StringUtils.isBlank(endTime)) {
            conditionMap.put("endTime", endTime);
        }
        if (!StringUtils.isBlank(meetId)) {
            conditionMap.put("meetId", meetId);
        }
        List<MeetAttendCountDTO> attendList = meetStatsService.findAttendCountByTag(conditionMap);

        Map<String, Object> attendMap = new HashMap<String, Object>();
        if (!CheckUtils.isEmpty(attendList)) {
            attendMap.put("list", attendList);
        }
        attendMap.put("startTime", startTime);
        attendMap.put("endTime", endTime);
        return APIUtils.success(attendMap);

    }


    /**
     * 饼图数据 查询不同条件下的 用户数据分布
     *
     * @param propNum 要查询的数据类型标识号（地区：1, 医院：2, 职称：3, 科室：4）
     * @param meetId  会议ID
     * @param province 省份 根据省份查询不同数据
     * @return
     */
    @RequestMapping(value = "/user/distribution")
    @ResponseBody
    public String userDataDistribution(Integer propNum, String meetId, String province) throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("propNum",propNum);
        params.put("meetId", meetId);
        params.put("province", province);
        List<UserDataDetailDTO> list = meetStatsService.findUserDataList(params);
        Integer totalCount = meetStatsService.findTotalCount(params);
        Float percent;
        Integer userCount;
        if (!CheckUtils.isEmpty(list)) {
            for (UserDataDetailDTO detailDTO : list) {
                if (StringUtils.isBlank(detailDTO.getPropName())) {
                    detailDTO.setPropName("未设置");
                }
                // 用户数
                userCount = detailDTO.getUserCount();
                // 计算百分比
                percent = userCount * 1.0F / totalCount;
                detailDTO.setPercent(percent);
            }
        }
        return APIUtils.success(list);
    }

    /**
     * 用户统计 跳转页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/statistics")
    public String meetAttendList(String id, Model model) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        Boolean isMine = meetService.checkMeetIsMine(principal.getId(), id);
        if (!isMine) {
            throw new SystemException("会议不存在");
        }
        model.addAttribute("meetId", id);
        return "/tongji/userStastic";
    }

    /**
     * 查询参加会议的用户列表
     *
     * @param pageable
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/attend")
    @ResponseBody
    public String meetAttendUserData(Pageable pageable, String id) {
        // 查询参加会议的用户数据
        if (!StringUtils.isEmpty(id)) {
            pageable.put("meetId", id);
            MyPage<MeetAttendUserDTO> page = meetStatsService.findAttendUserList(pageable);
            if (!CheckUtils.isEmpty(page.getDataList())) {
                return APIUtils.success(page);
            } else {
                return APIUtils.error("没有用户数据");
            }
        } else {
            return APIUtils.error("会议ID不存在");
        }
    }


    /**
     * 导出参会成员列表 excel
     *
     * @param meetId
     * @param response
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public String exportExcel(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        Boolean isMine = meetService.checkMeetIsMine(principal.getId(), meetId);
        if (!isMine) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if (!StringUtils.isEmpty(meetId)) {
            Workbook workbook = null;
            String fileName = " 会议参与人员.xls";
            List<Object> dataList = Lists.newArrayList();

           List<MeetAttendUserDTO> attendUserList = meetStatsService.findAttendUserExcel(meetId, principal.getId());
            if (!CheckUtils.isEmpty(attendUserList)) {
                fileName = attendUserList.get(0).getMeetName() + fileName;
                Map<Integer, List> attendUserMap = Maps.newHashMap();
                for (MeetAttendUserDTO userDTO : attendUserList) {
                    if (attendUserMap.get(userDTO.getId()) == null) {
                        List<MeetAttendUserDTO> userRecordList = Lists.newArrayList();
                        userRecordList.add(userDTO);
                        attendUserMap.put(userDTO.getId(), userRecordList);
                    } else {
                        attendUserMap.get(userDTO.getId()).add(userDTO);
                    }
                }

                for (Integer userId : attendUserMap.keySet()) {
                    List<MeetAttendUserDTO> userRecordList = attendUserMap.get(userId);
                    if (!userRecordList.isEmpty()) {
                        for (MeetAttendUserDTO attendUserDTO : userRecordList) {
                            // 填充Excel数据
                            UserStatisticsExcelData excelData = fillExcelData(attendUserDTO, meetId);
                            dataList.add(excelData);
                        }
                    }
                }

                workbook = ExcelUtils.writeExcel(fileName, dataList, UserStatisticsExcelData.class);

                try {
                    Integer nameIndex = UserStatisticsExcelData.columnIndex.NAME.getColumnIndex();
                    Integer unitNameIndex = UserStatisticsExcelData.columnIndex.UNIT_NAME.getColumnIndex();
                    Integer subUnitNameIndex = UserStatisticsExcelData.columnIndex.SUB_UNIT_NAME.getColumnIndex();
                    Integer hosLevelIndex = UserStatisticsExcelData.columnIndex.HOS_LEVEL.getColumnIndex();
                    Integer titleIndex = UserStatisticsExcelData.columnIndex.TITLE.getColumnIndex();
                    Integer provinceIndex = UserStatisticsExcelData.columnIndex.PROVINCE.getColumnIndex();
                    Integer groupIndex = UserStatisticsExcelData.columnIndex.GROUP_NAME.getColumnIndex();
                    Integer cmeIdIndex = UserStatisticsExcelData.columnIndex.CME_ID.getColumnIndex();
                    Integer learnRecordIndex = UserStatisticsExcelData.columnIndex.LEARN_RECORD.getColumnIndex();
                    //int[] columnIndexArray = new int[]{0, 1, 2, 3, 4, 5, 6};
                    int[] columnIndexArray = new int[]{nameIndex, unitNameIndex, subUnitNameIndex, hosLevelIndex,
                            titleIndex, provinceIndex, groupIndex, cmeIdIndex, learnRecordIndex};
                    ExcelUtils.createMergeExcel(fileName, workbook, attendUserMap, columnIndexArray, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    return APIUtils.error("导出文件出错");
                }
            } else {
                return APIUtils.error("暂无数据导出");
            }
        }
        return APIUtils.error("导出文件出错");
    }

    /**
     * 填充Excel表格数据
     *
     * @param attendUserDTO
     * @return
     */
    private UserStatisticsExcelData fillExcelData(MeetAttendUserDTO attendUserDTO, String meetId) {
        UserInfoCheckHelper.checkAttendUserDTO(attendUserDTO);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        UserStatisticsExcelData excelData = new UserStatisticsExcelData();
        excelData.setName(attendUserDTO.getNickName());
        excelData.setUnitName(attendUserDTO.getUnitName());
        excelData.setSubUnitName(attendUserDTO.getSubUnitName());
        excelData.setHosLevel(attendUserDTO.getLevel());
        excelData.setTitle(attendUserDTO.getTitle());
        excelData.setProvince(attendUserDTO.getProvince() + attendUserDTO.getCity());
        excelData.setGroupName(attendUserDTO.getGroupName());
        excelData.setStartTime(format.format(attendUserDTO.getStartTime()));
        excelData.setEndTime(format.format(attendUserDTO.getEndTime()));
        // 设置学习总时长
        if (attendUserDTO.getUseTime() != null) {
            excelData.setTotalHours(CalendarUtils.secToTime(attendUserDTO.getUseTime()));
        } else {
            excelData.setTotalHours("0");
        }
        excelData.setCmeId(attendUserDTO.getCmeId());
        // 设置学习记录
        Integer completeProgress = meetService.findUserLearningRecord(meetId, attendUserDTO.getId());
        if (completeProgress == 100) {
            excelData.setLearnRecord("完成");
        } else {
            excelData.setLearnRecord(completeProgress + "%");
        }
        return excelData;
    }


    /**
     * 导出参会成员学习记录
     *
     * @param meetId
     * @param response
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/export/attend/user")
    @ResponseBody
    public String exportAttendUserExcel(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        Boolean isMine = meetService.checkMeetIsMine(principal.getId(), meetId);
        if (!isMine) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if (!StringUtils.isEmpty(meetId)) {
            Workbook workbook = null;
            String fileName = "_会议参与人员.xls";
            List<Object> dataList = Lists.newArrayList();

            // 查询参会用户信息
            List<AttendMeetUserDetailDTO> attendUserList = meetStatsService.findAttendUserDetailExcel(meetId,principal.getId());
            if (!CheckUtils.isEmpty(attendUserList)) {
                fileName = attendUserList.get(0).getMeetName() + fileName;

                // 查询视频总时长
                Integer totalTime = videoService.findVideoTotalTime(meetId);
                String videoTotalTime = null;
                if (totalTime != null && totalTime > 0) {
                    videoTotalTime = CalendarUtils.secToTime(totalTime);
                } else {
                    videoTotalTime = "0";
                }

                int tempUserId = 0; // 临时用户id 对比是否同一个用户id多次出现
                AttendMeetUserExcelData excelData = null;
                for (AttendMeetUserDetailDTO attendUserDTO : attendUserList) {
                    int userId = attendUserDTO.getId();
                    if (tempUserId != attendUserDTO.getId()){
                        // 临时用户id 不等于用户id，说明用户是第一次出现，需要new新的数据对象存储数据
                        excelData = new AttendMeetUserExcelData();

                        // 设置用户总的学习完成度 百分比
                        Integer learningRecord = calculateLearningRecord(meetId,userId);
                        excelData.setLearnRecord(learningRecord + "%");

                        // 设置用户未观看视频的默认值
                        notWatchVideoDefaultValue(excelData, totalTime);

                        // 设置视频总时长
                        excelData.setVideoTotalTime(videoTotalTime);

                        dataList.add(excelData);
                    }

                    // 设置用户基本信息
                    fillUserDatailToExcel(excelData, attendUserDTO);
                    // 设置用户模块学习记录数据
                    fillUserLearningRecordToExcel(excelData, attendUserDTO);

                    // 变更临时用户id
                    tempUserId = attendUserDTO.getId();
                }

                workbook = ExcelUtils.writeExcel(fileName, dataList, AttendMeetUserExcelData.class);

                try {
                    ExcelUtils.outputWorkBook(fileName, workbook, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    return APIUtils.error("导出文件出错");
                }
            } else {
                return APIUtils.error("暂无数据导出");
            }
        }
        return APIUtils.error("导出文件出错");
    }

    /**
     * 设置用户没有观看视频，相关的字段默认值
     * @param excelData
     * @param totalTime
     */
    private void notWatchVideoDefaultValue(AttendMeetUserExcelData excelData, Integer totalTime){
        if (totalTime != null && totalTime > 0) {
            if (excelData.getWatchVideoTime() != null
                    && excelData.getWatchVideoTime().equals("0")) {
                excelData.setWatchVideoTime(UNFINISHED);
            }
            if (excelData.getWatchVideoPercent() != null
                    && excelData.getWatchVideoPercent().equals("0%")) {
                excelData.setWatchVideoPercent(UNFINISHED);
            }
        }
    }

    /**
     * 填充用户基本信息
     * @param excelData
     * @param dto
     * @return
     */
    private void fillUserDatailToExcel(AttendMeetUserExcelData excelData, AttendMeetUserDetailDTO dto) {
        // excelData 为空，说明用户是第一次出现，需新new一个新的对象存储用户信息数据,反之覆盖上一次的用户信息
        if (excelData == null) {
            excelData = new AttendMeetUserExcelData();
        }

        UserInfoCheckHelper.checkAttendMeetUserDetailDTO(dto);

        if (dto.getStartTime() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            excelData.setAttendTime(dateFormat.format(dto.getStartTime()));
        } else {
            excelData.setAttendTime(dto.getStartTime().toString());
        }
        excelData.setNickName(dto.getNickName());
        excelData.setUnitName(dto.getUnitName());
        excelData.setSubUnitName(dto.getSubUnitName());
        excelData.setGroupName(dto.getGroupName());
        excelData.setMobile(dto.getMobile());
        excelData.setEmail(dto.getUsername());
        excelData.setCmeId(dto.getCmeId());
    }

    /**
     * 填充用户模块学习记录
     * @param excelData
     * @param dto
     */
    private void fillUserLearningRecordToExcel(AttendMeetUserExcelData excelData, AttendMeetUserDetailDTO dto){
        Integer pptFuncId = MeetModule.ModuleFunction.PPT.getFunId();
        Integer videoFuncId = MeetModule.ModuleFunction.VIDEO.getFunId();
        Integer examFuncId = MeetModule.ModuleFunction.EXAM.getFunId();
        Integer surveyFuncId = MeetModule.ModuleFunction.SURVEY.getFunId();
        Integer signFuncId = MeetModule.ModuleFunction.SIGN.getFunId();

        Integer uId = dto.getId();
        String meetId = dto.getMeetId();
        Integer functionId = dto.getFunctionId();
        String percent = dto.getCompleteProgress() + "%";

        String usedTime = null;
        if (dto.getUsedTime() != null && dto.getUsedTime() > 0) {
            if ((functionId == pptFuncId)
                    || (functionId == videoFuncId)) {
                // 如果是ppt和视频模块 将使用时长转换为时长（时:分:秒格式）
                usedTime = CalendarUtils.secToTime(dto.getUsedTime().intValue());
            } else {
                // 如果是其他模块则转换为日期格式
                usedTime = CalendarUtils.transferLongToDate(dto.getUsedTime(), Constants.DATE_FORMAT_TYPE);
            }
        }

        if (usedTime == null || percent.equals("0%")) {
            usedTime = UNFINISHED;
            percent = UNFINISHED;
        }

        if (functionId == pptFuncId) {
            // 设置ppt学习记录
            excelData.setViewPPTTime(usedTime);
            excelData.setPptPercent(percent);

        } else if (functionId == videoFuncId) {
            // 设置视频学习记录
            excelData.setWatchVideoTime(usedTime);
            excelData.setWatchVideoPercent(percent);

        } else if (functionId == examFuncId) {
            // 设置考试记录
            excelData.setExamTime(usedTime);
            excelData.setExamPercent(percent);
            // 设置用户考试分数
            Integer userScore = examService.findUserExamScore(meetId, uId);
            excelData.setScore(String.valueOf(userScore));

        } else if (functionId == surveyFuncId) {
            // 设置问卷记录
            excelData.setSurveyTime(usedTime);
            excelData.setSurveyPercent(percent);

        } else if (functionId == signFuncId) {
            // 设置签到记录
            excelData.setSignTime(usedTime);
            excelData.setSignPercent(percent);
            if (percent.equals("0%")) {
                excelData.setSignFlag("失败");
            } else {
                excelData.setSignFlag("成功");
            }

        } else {
            // 没有学习记录的情况 设置默认值
            excelData.setViewPPTTime(usedTime);
            excelData.setPptPercent(percent);
            excelData.setWatchVideoTime(usedTime);
            excelData.setWatchVideoPercent(percent);
            excelData.setExamTime(usedTime);
            excelData.setExamPercent(percent);
            excelData.setScore(UNFINISHED);
            excelData.setSurveyTime(usedTime);
            excelData.setSurveyPercent(percent);
            excelData.setSignTime(usedTime);
            excelData.setSignPercent(percent);
            excelData.setSignFlag(UNFINISHED);
        }
    }


    /**
     * 计算学习记录
     * @param meetId
     * @param userId
     * @return
     */
    private Integer calculateLearningRecord(String meetId,Integer userId){
        return meetService.findUserLearningRecord(meetId,userId);
    }


}
