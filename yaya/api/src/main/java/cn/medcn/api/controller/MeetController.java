package cn.medcn.api.controller;

import cn.medcn.api.dto.Principal;
import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.LocationUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.*;
import cn.medcn.user.dto.MeetingDTO;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by lixuan on 2017/4/20.
 */
@RequestMapping(value = "/api/meet")
@Controller
public class MeetController extends BaseController {

    @Autowired
    private MeetService meetService;

    @Autowired
    private SignService signService;

    @Value("${meet.sign.max.distance}")
    private String maxDistance;

    @Autowired
    private AudioService audioService;

    @Autowired
    private ExamService examService;

    @Autowired
    private SurveyService surveyService;


    @Autowired
    private VideoService videoService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MeetMaterialService meetMaterialService;

    @Value("${app.file.base}")
    private String appFileBase;

    @Autowired
    private MeetFolderService meetFolderService;


    /**
     * 会议详情
     *
     * @param meetId
     * @return
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String info(String meetId) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        if (StringUtils.isEmpty(meetId)) {
            return APIUtils.error(SpringUtils.getMessage("meet.meetid.required"));
        } else {
            MeetInfoDTO meetInfoDTO = meetService.findFinalMeetInfo(meetId, principal.getId());
            if (meetInfoDTO == null) {
                return APIUtils.error(SpringUtils.getMessage("meet.notexisted"));
            } else {
                // 会议资料
                if (meetInfoDTO.getMaterials() != null) {
                    for (MeetMaterial material : meetInfoDTO.getMaterials()) {
                        if (!StringUtils.isEmpty(material.getFileUrl())) {
                            material.setFileUrl(appFileBase + material.getFileUrl());
                        }
                    }
                }
                meetInfoDTO.setAttention(appUserService.checkAttention(meetInfoDTO.getPubUserId(), principal.getId()));
            }
            return APIUtils.success(meetInfoDTO);
        }
    }


    @RequestMapping(value = "/pageMaterial")
    @ResponseBody
    public String pageMaterial(Pageable pageable, String meetId) {
        pageable.getParams().put("meetId", meetId);
        MyPage<MeetMaterial> page = meetMaterialService.page(pageable);
        if (page.getDataList() != null && !page.getDataList().isEmpty()) {
            for (MeetMaterial meetMaterial : page.getDataList()) {
                if (!StringUtils.isEmpty(meetMaterial.getFileUrl())) {
                    meetMaterial.setFileUrl(appFileBase + meetMaterial.getFileUrl());
                }
            }
        }
        return APIUtils.success(page.getDataList());
    }


    /**
     * 推荐会议列表
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/tuijian")
    @ResponseBody
    public String rec(Pageable pageable) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        MyPage<MeetTuijianDTO> page = meetService.findMeetTuijian(pageable, principal.getId());
        for (MeetTuijianDTO dto : page.getDataList()) {
            if (!StringUtils.isEmpty(dto.getLecturerImg())) {
                dto.setLecturerImg(appFileBase + dto.getLecturerImg());
            } else {
                dto.setLecturerImg(appFileBase + Constants.USER_DEFAULT_AVATAR_MAN);
            }
            if (!StringUtils.isEmpty(dto.getPubUserHead())) {
                dto.setPubUserHead(appFileBase + dto.getPubUserHead());
            }
        }
        return APIUtils.success(page.getDataList());
    }

    /**
     * 查询已关注的会议科室统计
     *
     * @return
     */
    @RequestMapping(value = "/department")
    @ResponseBody
    public String departmentList() {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        List<MeetTypeState> list = meetService.findMeetTypes(principal.getId());
        return APIUtils.success(list);
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public String search(Pageable pageable, String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return APIUtils.error(SpringUtils.getMessage("meet.keyword.notnull"));
        }
        pageable.put(Constants.KEY_WORD, keyword);
        MyPage<MeetFolderDTO> page = meetService.searchMeetFolderInfo(pageable);
        return APIUtils.success(page.getDataList());
    }

    /**
     * 我关注过的公众的会议
     *
     * @param state 会议状态
     * @return
     */
    @RequestMapping(value = "/meets")
    @ResponseBody
    public String meets(Pageable pageable, Integer state, String depart) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Integer userId = principal.getId();
        if (state == null) {
            state = Meet.MeetType.NOT_START.ordinal();
        }
        pageable.put("state", state);
        if (!CheckUtils.isEmpty(depart)){
            pageable.put("meetType", depart);
        }
        pageable.put("userId", userId);
        MyPage<MeetFolderDTO> page = meetFolderService.findMyMeetFolderList(pageable);
        if (!CheckUtils.isEmpty(page.getDataList())) {
            for (MeetFolderDTO folderDTO : page.getDataList()) {
                userLearningProgress(folderDTO, userId);
                if (folderDTO.getType() == MeetFolderDTO.FolderType.folder.ordinal()) { // 文件夹 设置相应的状态
                    folderDTO.setState(state);
                }
            }
        }
        return success(page.getDataList());
    }


    public void attend(String meetId, Integer moduleId) throws NotEnoughCreditsException, SystemException {
        if (StringUtils.isEmpty(meetId)) {
            throw new SystemException(SpringUtils.getMessage("meet.meetid.required"));
        }
        if (moduleId == null || moduleId == 0) {
            throw new SystemException(SpringUtils.getMessage("meet.moduleid.required"));
        }
        Principal principal = SecurityUtils.getCurrentUserInfo();
        meetService.executeAttend(meetId, moduleId, principal.getId());
    }


    /**
     * 参加考试
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @RequestMapping(value = "/toexam")
    @ResponseBody
    public String toexam(String meetId, Integer moduleId) {
        try {
            attend(meetId, moduleId);
        } catch (NotEnoughCreditsException e) {
            return APIUtils.error(APIUtils.ERROR_CODE_NOTENOUGH_CREDITS, e.getMessage());
        } catch (SystemException se) {
            return APIUtils.error(se.getMessage());
        }
        Principal principal = SecurityUtils.getCurrentUserInfo();

        ExamDTO dto = examService.findMeetExam(meetId, moduleId);
        dto.setResitTimes(dto.getResitTimes() + 1);
        if (dto == null) {
            return APIUtils.error(SpringUtils.getMessage("meet.exam.paper.notfound"));
        }
        dto.setServerTime(new Date());
        ExamHistory condition = new ExamHistory();
        condition.setMeetId(meetId);
        condition.setUserId(principal.getId());
        ExamHistory history = examService.findHistory(condition);
        if (history != null) {
            dto.setFinished(true);
            dto.setScore(history.getScore());
            dto.setFinishTimes(history.getFinishTimes());
        } else {
            dto.setFinished(false);
            dto.setScore(0);
            dto.setFinishTimes(0);
        }
        if (dto.getShuffle() != null && dto.getShuffle()) {
            Collections.shuffle(dto.getPaper().getQuestions());
        }
        return APIUtils.success(dto);
    }


    @RequestMapping(value = "/submitex", method = RequestMethod.POST)
    @ResponseBody
    public String submitex(ExamHistory history) {
        history.initItems();//将json格式的item转换成List
        Principal principal = SecurityUtils.getCurrentUserInfo();
        history.setUserId(principal.getId());
        ExamHistory scoredHistory = examService.score(history);
        //提交到队列中
        //examService.submitToCache(scoredHistory);

        //直接保存
        try {
            examService.saveHistory(scoredHistory);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        // 添加或修改 考试学习进度记录
        Integer funcId = MeetLearningRecord.functionName.EXAM.getFunId();
        MeetLearningRecord learningRecord = assignDataToLearning(history.getUserId(),history.getMeetId(),funcId);
        learningRecord.setUsedTime(scoredHistory.getSubmitTime().getTime());
        meetService.saveOrUpdateLearnRecord(learningRecord);

        return APIUtils.success(scoredHistory.getHistoryState());
    }

    /**
     * 参加问卷
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @RequestMapping(value = "/tosurvey")
    @ResponseBody
    public String tosurvey(String meetId, Integer moduleId) {
        try {
            attend(meetId, moduleId);
        } catch (NotEnoughCreditsException e) {
            return APIUtils.error(APIUtils.ERROR_CODE_NOTENOUGH_CREDITS, e.getMessage());
        } catch (SystemException se) {
            return APIUtils.error(se.getMessage());
        }
        Principal principal = SecurityUtils.getCurrentUserInfo();
        SurveyHistory condition = new SurveyHistory();
        condition.setMeetId(meetId);
        condition.setUserId(principal.getId());
        SurveyHistory history = surveyService.findSurveyHistory(condition);
        if (history != null) {
            return APIUtils.error("您已经提交过此问卷");
        }
        MeetSurvey survey = surveyService.findMeetSurvey(meetId, moduleId);
        return APIUtils.success(SurveyDTO.build(survey));
    }

    @RequestMapping(value = "/submitsur", method = RequestMethod.POST)
    @ResponseBody
    public String submitsur(SurveyHistory history) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        history.setUserId(principal.getId());
        surveyService.executeSubmit(history);

        // 添加或修改 学习进度记录
        Integer funcId = MeetLearningRecord.functionName.SURVEY.getFunId();
        MeetLearningRecord learningRecord = assignDataToLearning(principal.getId(),history.getMeetId(),funcId);
        learningRecord.setUsedTime(history.getSubmitTime().getTime());
        meetService.saveOrUpdateLearnRecord(learningRecord);

        return APIUtils.success();
    }


    @RequestMapping(value = "/toppt")
    @ResponseBody
    public String toppt(String meetId, Integer moduleId) {
        try {
            attend(meetId, moduleId);
        } catch (NotEnoughCreditsException e) {
            return APIUtils.error(APIUtils.ERROR_CODE_NOTENOUGH_CREDITS, e.getMessage());
        } catch (SystemException se) {
            return APIUtils.error(se.getMessage());
        }
        MeetAudio meetAudio = audioService.findMeetAudio(meetId, moduleId);
        if (meetAudio != null && meetAudio.getCourseId() != null) {
            meetAudio.setCourse(audioService.findAudioCourse(meetAudio.getCourseId()));
        }
        MeetAudioDTO audioDTO = MeetAudioDTO.build(meetAudio);
        if (audioDTO.getCourse() != null && audioDTO.getCourse().getDetails() != null) {
            for (AudioCourseDetailDTO dto : audioDTO.getCourse().getDetails()) {
                dto.setVideoUrl(StringUtils.isEmpty(dto.getVideoUrl()) ? "" : appFileBase + dto.getVideoUrl());
                dto.setImgUrl(StringUtils.isEmpty(dto.getImgUrl()) ? "" : appFileBase + dto.getImgUrl());
                dto.setAudioUrl(StringUtils.isEmpty(dto.getAudioUrl()) ? "" : appFileBase + dto.getAudioUrl());
            }
        }
        return APIUtils.success(audioDTO);
    }


    @RequestMapping(value = "/audio/record")
    @ResponseBody
    public String ppthistory(AudioHistory history) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Integer userId = principal.getId();
        history.setUserId(userId);
        audioService.insertHistory(history);
        return APIUtils.success();
    }


    @RequestMapping(value = "/video/record")
    @ResponseBody
    public String videoRecord(VideoHistory history) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Integer userId = principal.getId();
        history.setUserId(userId);
        history.setEndTime(new Date());
        videoService.insertHistory(history);

        String meetId = history.getMeetId();
        // 添加或修改 视频学习进度记录 (学习视频总时长 / 所有视频的总时长 = 完成率)
        int totalTime = videoService.findVideoTotalTime(meetId);
        int watchTime = videoService.findUserVideoWatchTime(meetId, userId);
        int completeCount = 0;
        if (totalTime > 0) {
            completeCount = Math.round((float) watchTime / totalTime * 100);
        }
        Integer funcId = MeetLearningRecord.functionName.VIDEO.getFunId();
        MeetLearningRecord learningRecord = assignDataToLearning(userId, meetId, funcId);
        learningRecord.setCompleteProgress(completeCount);
        learningRecord.setUsedTime(new Long(watchTime));
        meetService.saveOrUpdateLearnRecord(learningRecord);

        return APIUtils.success();
    }

    @RequestMapping("/ppt/record")
    @ResponseBody
    public String pptRecord(PPTAudioDTO pptAudioDTO) {
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        AudioHistory history = new AudioHistory();
        history.setUserId(userId);
        history.setMeetId(pptAudioDTO.getMeetId());
        history.setCourseId(pptAudioDTO.getCourseId());
        history.setModuleId(pptAudioDTO.getModuleId());
        JSONArray jsonArray = JSON.parseArray(pptAudioDTO.getDetails());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer detailId = (Integer) jsonObject.get("detailId");
            Integer usedtime = (Integer) jsonObject.get("usedtime");
            Boolean finished = (Boolean) jsonObject.get("finished");
            history.setDetailId(detailId);
            history.setUsedtime(usedtime);
            history.setFinished(finished);
            audioService.insertHistory(history);
        }
        String meetId = history.getMeetId();
        Integer completeCount = calculatePPTProgress(meetId, userId);
        // 添加或修改学习进度记录
        Integer funcId = MeetLearningRecord.functionName.PPT.getFunId();
        MeetLearningRecord learningRecord = assignDataToLearning(userId, meetId, funcId);
        learningRecord.setCompleteProgress(completeCount);
        learningRecord.setUsedTime(new Long(history.getUsedtime()));
        meetService.saveOrUpdateLearnRecord(learningRecord);
        return APIUtils.success();
    }

    /**
     * 计算ppt学习进度
     *
     * @param meetId
     * @param userId
     * @return
     */
    private int calculatePPTProgress(String meetId, Integer userId) {
        // 新增学习记录时，才会相应的增加或修改学习进度
        // ppt总页数
        List<AudioCourseDetail> audioCourseList = audioService.findPPtTotalCount(meetId);
        Integer pptTotalCount = audioCourseList.size();
        // 查询用户观看的记录数
        Integer userViewCount = audioService.findUserViewPPTCount(meetId, userId);
        Integer completeCount = 0;
        if (pptTotalCount > 0) {
            completeCount = Math.round((float) userViewCount / pptTotalCount * 100);
        }
        return completeCount;
    }


    @RequestMapping(value = "/tovideo")
    @ResponseBody
    public String tovideo(String meetId, Integer moduleId) {
        try {
            attend(meetId, moduleId);
        } catch (NotEnoughCreditsException e) {
            return APIUtils.error(APIUtils.ERROR_CODE_NOTENOUGH_CREDITS, e.getMessage());
        } catch (SystemException se) {
            return APIUtils.error(se.getMessage());
        }
        MeetVideo video = videoService.findMeetVideo(meetId, moduleId);
        //添加视频观看时长
        if (video.getCourseId() != null) {
            Integer userId = SecurityUtils.getCurrentUserInfo().getId();
            VideoHistory history = new VideoHistory();
            history.setUserId(userId);
            for (VideoCourseDetail detail : video.getCourse().getDetails()) {
                if (detail.getType() == 1) {
                    history.setDetailId(detail.getId());
                    VideoHistory target = videoService.findVideoHistory(history);
                    detail.setUserdtime(target == null ? 0 : (target.getUsedtime() == null ? 0 : target.getUsedtime()));
                }
            }
        }
        return APIUtils.success(video);
    }


    @RequestMapping(value = "/video/sublist")
    @ResponseBody
    public String sublist(Integer preId) {
        if (preId == null) {
            return APIUtils.error("参数错误,preId不能为空");
        }
        List<VideoCourseDetail> list = videoService.findByPreid(preId);
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        VideoHistory history = new VideoHistory();
        history.setUserId(userId);
        for (VideoCourseDetail detail : list) {
            if (detail.getType() == 1) {
                history.setDetailId(detail.getId());
                VideoHistory target = videoService.findVideoHistory(history);
                detail.setUserdtime(target == null ? 0 : target.getUsedtime());
            }
        }
        return APIUtils.success(list);
    }

    @RequestMapping(value = "/tosign")
    @ResponseBody
    public String tosign(String meetId, Integer moduleId) {
        try {
            attend(meetId, moduleId);
        } catch (NotEnoughCreditsException e) {
            return APIUtils.error(APIUtils.ERROR_CODE_NOTENOUGH_CREDITS, e.getMessage());
        } catch (SystemException se) {
            if (!SpringUtils.getMessage("meet.starttime.error").equals(se.getMessage())) {
                return APIUtils.error(se.getMessage());
            }
        }
        Principal principal = SecurityUtils.getCurrentUserInfo();
        MeetSign signCondition = new MeetSign();
        signCondition.setMeetId(meetId);
        signCondition.setUserId(principal.getId());
        MeetSign sign = signService.selectOneSign(signCondition);
        if (sign != null) {//如果已经签到过 返回已经签到的信息
            return APIUtils.success(PositionDTO.buildFromSign(sign));
        }
        MeetPosition condition = new MeetPosition();
        condition.setMeetId(meetId);
        MeetPosition position = signService.selectOne(condition);
        if (position.getStartTime().getTime() > System.currentTimeMillis()) {
            return APIUtils.error("签到时间未到");
        }
        return APIUtils.success(PositionDTO.buildFromPostion(position));
    }

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    @ResponseBody
    public String dosign(MeetSign sign) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        if (StringUtils.isEmpty(sign.getMeetId())) {
            return APIUtils.error(SpringUtils.getMessage("meet.meetid.required"));
        }
        if (sign.getSignLat() == null || sign.getSignLng() == null) {
            return APIUtils.error(SpringUtils.getMessage("meet.sign.notnull"));
        }
        MeetPosition position = signService.selectByPrimaryKey(sign.getPositionId());
        if (position == null) {
            return APIUtils.error(SpringUtils.getMessage("meet.sign.postionerror"));
        }
        if (position.getStartTime().getTime() > System.currentTimeMillis()) {
            return APIUtils.error("尚未开始签到");
        }
        if (position.getEndTime().getTime() < System.currentTimeMillis()) {
            return APIUtils.error("签到已经过期");
        }

        MeetSign condition = new MeetSign();
        condition.setMeetId(sign.getMeetId());
        condition.setUserId(principal.getId());
        condition.setPositionId(sign.getPositionId());
        MeetSign existed = signService.selectOneSign(condition);
        if (existed != null) {
            // 已经签到过 且签到失败 则需更新用户再次签到标记及签到时间
            if (existed.getSignFlag() == false) {
                existed.setSignTime(new Date());
                if (!sign(position, existed)) {
                    existed.setSignFlag(false);
                    signService.updateSign(existed);
                    return APIUtils.error("您不在签到地点");
                } else {
                    existed.setSignFlag(true);
                    signService.updateSign(existed);
                }
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("signTime", existed.getSignTime());
            return APIUtils.success(map);
        }

        sign.setUserId(principal.getId());
        sign.setSignFlag(true);
        sign.setSignTime(new Date());
        if (!sign(position, sign)) {
            sign.setSignFlag(false);
            signService.insertSign(sign);
            return APIUtils.error("您不在签到地点");
        }
        signService.insertSign(sign);

        // 添加或修改 会议签到进度记录
        Integer funcId = MeetLearningRecord.functionName.SIGN.getFunId();
        MeetLearningRecord learningRecord = assignDataToLearning(principal.getId(), sign.getMeetId(), funcId);
        learningRecord.setUsedTime(sign.getSignTime().getTime());
        meetService.saveOrUpdateLearnRecord(learningRecord);
        Map<String, Object> map = Maps.newHashMap();
        map.put("signTime", System.currentTimeMillis());
        return APIUtils.success(map);
    }


    protected boolean sign(MeetPosition position, MeetSign sign) {
        double distance = LocationUtils.getDistance(position.getPositionLat(), position.getPositionLng(), sign.getSignLat(), sign.getSignLng());
        return !(distance > Double.valueOf(maxDistance));
    }


    @RequestMapping(value = "/exit")
    @ResponseBody
    public String exit(String meetId, Integer usedtime) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        MeetAttend meetAttend = meetService.findMeetAttend(meetId, principal.getId());
        if (meetAttend != null) {
            meetAttend.setEndTime(new Date());
            meetAttend.setUsetime((meetAttend.getUsetime() == null ? 0 : meetAttend.getUsetime()) + usedtime);
            meetService.updateMeetAttend(meetAttend);
        }
        return APIUtils.success();
    }

    @RequestMapping("/share")
    public String toSharePage(String meetId, Model model) {
        Meet meet = meetService.findMeet(meetId);
        if (meet != null) {
            model.addAttribute("meet", meet);
            AppUser owner = appUserService.selectByPrimaryKey(meet.getOwnerId());
            model.addAttribute("pubUserHeadImg", appFileBase + owner.getHeadimg());
            Integer attendCount = meetService.getMeetAttendCount(meetId);
            model.addAttribute("attendCount", attendCount);
            model.addAttribute("linkman", owner.getLinkman());
            model.addAttribute("appFileBase", appFileBase);
        }
        return "/sharePage";
    }

    public SurveyService getSurveyService() {
        return surveyService;
    }

    /**
     * app首页 推荐会议文件夹
     * pageable
     *
     * @return
     */
    @RequestMapping("/recommend/meet/folder")
    @ResponseBody
    public String recommendMeetFolder(Pageable pageable) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        List<MeetFolderDTO> list = null;
        if (pageable.getPageNum() > 1) {
            list = new ArrayList<MeetFolderDTO>();
        } else {
            list = meetService.findRecommendMeetFolder(principal.getId());
        }
        return success(list);
    }

    /**
     * 会议文件夹 下级目录或资源数据
     *
     * @param preId
     * @return
     * @link showFlag=0 隐藏空文件夹 只有会议列表传该参数 判断为0隐藏空文件夹
     */
    @RequestMapping("/folder/leaf")
    @ResponseBody
    public String folderLeafList(Pageable pageable, String preId, Integer showFlag) {
        if (preId == null) {
            return error("preId不能为空");
        }
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Integer userId = principal.getId();
        pageable.put("preId", preId);
        pageable.put("userId", userId);
        pageable.put("showFlag", showFlag);
        MyPage<MeetFolderDTO> page = meetFolderService.findLeafMeetFolder(pageable);
        if (!CheckUtils.isEmpty(page.getDataList())) {
            for (MeetFolderDTO folderDTO : page.getDataList()) {
                userLearningProgress(folderDTO, userId);
            }
        }
        return success(page.getDataList());
    }


    /**
     * 给会议学习进度表 赋值数据
     *
     * @param userId
     * @param meetId
     * @param functionId
     * @return
     */
    private MeetLearningRecord assignDataToLearning(int userId, String meetId, int functionId) {
        MeetLearningRecord learningRecord = new MeetLearningRecord();
        learningRecord.setUserId(userId);
        learningRecord.setMeetId(meetId);
        learningRecord.setFunctionId(functionId);
        return learningRecord;
    }

    /**
     * 获取学习进度
     *
     * @param folderDTO
     * @param userId
     * @return
     */
    private void userLearningProgress(MeetFolderDTO folderDTO, int userId) {
        if (folderDTO.getType() == MeetFolderDTO.FolderType.meet.ordinal() && folderDTO.getState() == Meet.MeetType.OVER.ordinal()) {
            int completeCount = meetService.findUserLearningRecord(folderDTO.getId(), userId);
            if (completeCount > 0) {
                folderDTO.setCompleteProgress(completeCount);
            } else {
                folderDTO.setCompleteProgress(0);
            }
        }
    }


}
