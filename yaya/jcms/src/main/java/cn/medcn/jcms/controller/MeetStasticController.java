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
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.model.MeetLearningRecord;
import cn.medcn.meet.service.ExamService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.MeetStasticService;
import cn.medcn.meet.service.VideoService;
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
    private MeetStasticService meetStasticService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private ExamService examService;

    @Autowired
    private VideoService videoService;


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
        MeetStasticDTO staticsDTO = meetStasticService.findMeetStastic(userId);
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
        MyPage<MeetDataDTO> page = meetStasticService.findMyMeetList(pageable);
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
        return APIUtils.success(meetStasticService.findMeet(meetId));
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
        List<MeetAttendCountDTO> attendList = meetStasticService.findAttendCountByTag(conditionMap);

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
        List<UserDataDetailDTO> list = meetStasticService.findUserDataList(params);
        Integer totalCount = meetStasticService.findTotalCount(params);
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
            MyPage<MeetAttendUserDTO> page = meetStasticService.findAttendUserList(pageable);
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

           List<MeetAttendUserDTO> attendUserList = meetStasticService.findAttendUserExcel(meetId, principal.getId());
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
        if (attendUserDTO.getStartTime() == null) {
            excelData.setStartTime(null);
        } else {
            excelData.setStartTime(format.format(attendUserDTO.getStartTime()));
        }
        if (attendUserDTO.getEndTime() == null) {
            excelData.setEndTime(null);
        } else {
            excelData.setEndTime(format.format(attendUserDTO.getEndTime()));
        }
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
     * 导出参会成员相关数据
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
            String fileName = " 会议参与人员.xls";
            List<Object> dataList = Lists.newArrayList();

            List<AttendMeetUserDetailDTO> attendUserList = meetStasticService.findAttendUserDetailExcel(meetId,principal.getId());
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

                int tempUserId = 0; // 临时用户ID 对比是否同一个用户ID多次出现
                AttendMeetUserExcelData excelData = null;
                for (AttendMeetUserDetailDTO attendUserDTO : attendUserList) {
                    int userId = attendUserDTO.getId();
                    if (tempUserId != attendUserDTO.getId()){
                        excelData = new AttendMeetUserExcelData();

                        // 设置用户总的学习记录 百分比
                        Integer learningRecord = calculateLearningRecord(meetId,userId);
                        excelData.setLearnRecord(learningRecord + "%");

                        // 设置用户未观看视频的默认值
                        if (totalTime != null && totalTime > 0) {
                            if (excelData.getWatchVideoTime() != null
                                    && excelData.getWatchVideoTime().equals("0")) {
                                excelData.setWatchVideoTime("未完成");
                            }
                            if (excelData.getWatchVideoPercent() != null
                                    && excelData.getWatchVideoPercent().equals("0%")) {
                                excelData.setWatchVideoPercent("未完成");
                            }
                        }

                        // 设置视频总时长
                        excelData.setVideoTotalTime(videoTotalTime);

                        dataList.add(excelData);
                    }

                    fillValueToExcel(excelData, attendUserDTO);

                    tempUserId = attendUserDTO.getId();
                }

                workbook = ExcelUtils.writeExcel(fileName, dataList, AttendMeetUserExcelData.class);

                try {
                    ExcelUtils.createExcel(fileName, workbook, response);
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
     * 赋值到excel表格
     *
     * @param dto
     * @return
     */
    private void fillValueToExcel(AttendMeetUserExcelData excelData, AttendMeetUserDetailDTO dto) {
        if (excelData == null) {
            excelData = new AttendMeetUserExcelData();
        }

        UserInfoCheckHelper.checkAttendMeetUserDetailDTO(dto);

        Integer pptFuncId = AttendMeetUserDetailDTO.functionName.PPT.getFuncId();
        Integer videoFuncId = AttendMeetUserDetailDTO.functionName.VIDEO.getFuncId();
        Integer examFuncId = AttendMeetUserDetailDTO.functionName.EXAM.getFuncId();
        Integer surveyFuncId = AttendMeetUserDetailDTO.functionName.SURVEY.getFuncId();
        Integer signFuncId = AttendMeetUserDetailDTO.functionName.SIGN.getFuncId();

        Integer functionId = dto.getFunctionId();
        String percent = dto.getCompleteProgress() + "%";
        Integer uId = dto.getId();
        String meetId = dto.getMeetId();

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
        // 如果是ppt和视频模块 将使用时长转换为时长；如果是其他模块则转换为日期格式
        String usedTime = null;
        if (dto.getUsedTime() != null && dto.getUsedTime() > 0) {
            if ((functionId == pptFuncId)
                    || (functionId == videoFuncId)) {
                usedTime = CalendarUtils.secToTime(dto.getUsedTime().intValue());
            } else {
                usedTime = CalendarUtils.transferLongToDate(dto.getUsedTime(), Constants.DATE_FORMAT_TYPE);
            }
        }

        if (usedTime == null && percent.equals("0%")) {
            usedTime = "未完成";
            percent = "未完成";
        }
        if (functionId == pptFuncId) {
            excelData.setViewPPTTime(usedTime);
            excelData.setPptPercent(percent);
        } else if (functionId == videoFuncId) {
            excelData.setWatchVideoTime(usedTime);
            excelData.setWatchVideoPercent(percent);
        } else if (functionId == examFuncId) {
            excelData.setExamTime(usedTime);
            excelData.setExamPercent(percent);
            // 设置用户考试分数
            Integer userScore = examService.findUserExamScore(meetId, uId);
            excelData.setScore(String.valueOf(userScore));
        } else if (functionId == surveyFuncId) {
            excelData.setSurveyTime(usedTime);
            excelData.setSurveyPercent(percent);
        } else if (functionId == signFuncId) {
            excelData.setSignTime(usedTime);
            excelData.setSignPercent(percent);
            if (percent.equals("0%")) {
                excelData.setSignFlag("失败");
            } else {
                excelData.setSignFlag("成功");
            }
        } else {
            excelData.setViewPPTTime(usedTime);
            excelData.setPptPercent(percent);
            excelData.setWatchVideoTime(usedTime);
            excelData.setWatchVideoPercent(percent);
            excelData.setExamTime(usedTime);
            excelData.setExamPercent(percent);
            excelData.setScore("未完成");
            excelData.setSurveyTime(usedTime);
            excelData.setSurveyPercent(percent);
            excelData.setSignTime(usedTime);
            excelData.setSignPercent(percent);
            excelData.setSignFlag("未完成");
        }

    }

    /**
     * 计算学习记录
     * @param meetId
     * @param userId
     * @return
     */
    private Integer calculateLearningRecord(String meetId,Integer userId){
        Integer completeProgress = meetService.findUserLearningRecord(meetId,userId);
        return completeProgress;
    }


}
