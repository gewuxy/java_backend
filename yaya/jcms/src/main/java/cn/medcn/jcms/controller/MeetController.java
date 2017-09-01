package cn.medcn.jcms.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.*;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.MeetFolderDTO;
import cn.medcn.meet.dto.MeetListInfoDTO;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.*;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.model.Department;
import cn.medcn.user.model.Group;
import cn.medcn.user.service.DoctorService;
import cn.medcn.user.service.HospitalService;
import com.alipay.api.domain.Video;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lixuan on 2017/5/16.
 */
@Controller
@RequestMapping(value = "/func/meet")
public class MeetController extends BaseController {

    @Autowired
    private MeetService meetService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private MeetMaterialService meetMaterialService;

    @Value("${app.file.upload.base}")
    private String appUploadBase;

    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${upload.filetype.allowed}")
    private String uploadAllowed;

    @Value("${app.yaya.base}")
    protected String yayaAppBase;

    @Autowired
    private SystemRegionService systemRegionService;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AudioService audioService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ExamService examService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SignService signService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private MeetFolderService meetFolderService;


    /**
     * 已发布的会议列表
     *
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Pageable pageable, String preId, Model model) {
        Integer masterId = SubjectUtils.getCurrentUserid();
        pageable.put("masterId", masterId);
        MyPage<MeetFolderDTO> page = null;
        if (StringUtils.isEmpty(preId) || "0".equals(preId)) { //第一层
            page = meetFolderService.findPublishedMeeting(pageable);
            model.addAttribute("preId", "0");
        } else {
            pageable.put("preId", preId);
            page = meetFolderService.findPublishedSubList(pageable);
        }
        //获取指定目录完整路径名
        if (!StringUtils.isEmpty(preId) && !"0".equals(preId)) {
            String preName = meetFolderService.getAllFolder(preId);
            model.addAttribute("folderName", preName);
        }
        model.addAttribute("page", page);
        model.addAttribute("preId", preId);


        //获取所有文件夹
        InfinityTree tree = new InfinityTree();
        tree.setUserId(masterId);
        List<InfinityTree> treeList = meetFolderService.select(tree);
        if (!CheckUtils.isEmpty(treeList)) {
            treeList = getSubTreeList(treeList);
            model.addAttribute("folderList", treeList);
        }
        return "/meet/meetingList";
    }


    private List<InfinityTree> getSubTreeList(List<InfinityTree> treeList) {
        List<InfinityTree> mainList = Lists.newArrayList();
        for (InfinityTree tree : treeList) {
            if (StringUtils.isEmpty(tree.getPreid()) || "0".equals(tree.getPreid())) {
                mainList.add(tree);
            }
        }

        for (InfinityTree mainTree : mainList) {
            if (CheckUtils.isEmpty(mainTree.getTreeList())) {
                mainTree.setTreeList(new ArrayList<InfinityTree>());
            }
            for (InfinityTree tree : treeList) {
                if (!StringUtils.isEmpty(tree.getPreid()) && tree.getPreid().equals(mainTree.getId())) {
                    mainTree.getTreeList().add(tree);
                }
            }
        }
        return mainList;
    }


    /**
     * 移动会议到文件夹
     *
     * @param meetId
     * @param folderId
     * @return
     */
    @RequestMapping("/moveMeet")
    @ResponseBody
    public String moveMeet(String meetId, String folderId) {
        if (StringUtils.isEmpty(meetId)) {
            return error("会议id不能为空");
        }
        if (StringUtils.isEmpty(folderId)) {
            return error("请选择文件夹");
        }
        //需要拿到会议的名称，所以直接查询出meet，不使用checkMeetIsMine方法
        Integer userId = SubjectUtils.getCurrentUserid();
        Meet meet = new Meet();
        meet.setId(meetId);
        meet.setOwnerId(userId);
        meet = meetService.selectOne(meet);
        if (meet == null) {
            return error("不能操作不属于您的会议");
        }

        InfinityTreeDetail detail = new InfinityTreeDetail();
        detail.setResourceId(meetId);
        InfinityTreeDetail result = meetFolderService.selectTreeDetail(detail);
        //移动到根目录
        if ("0".equals(folderId)) {
            if (result != null) {
                meetFolderService.deleteTreeDetail(result);
            } else {
                return success();
            }
        }
        detail.setInfinityId(folderId);
        detail.setResourceName(meet.getMeetName());
        if (result != null) {
            detail.setId(result.getId());
            meetFolderService.updateTreeDetail(detail);
        } else {
            meetFolderService.insertDetail(detail);
        }

        return success(folderId);

    }


    /**
     * 删除会议文件夹,移除里面的会议
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteFolder(String id) {
        if (StringUtils.isEmpty(id)) {
            return error("文件夹id不能为空");
        }
        Integer userId = SubjectUtils.getCurrentUserid();
        InfinityTree tree = new InfinityTree();
        tree.setUserId(userId);
        tree.setId(id);
        if (meetFolderService.selectOne(tree) == null) {
            return error("不能删除不属于自己的会议");
        }
        //递归删除
        meetFolderService.recursiveDeleteFolder(id);
        return success();
    }



    /**
     * 会议草稿箱
     *
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/draft")
    public String draft(Pageable pageable, Model model) {
        Integer masterId = SubjectUtils.getCurrentUserid();
        pageable.getParams().put("masterId", masterId);
        MyPage<MeetListInfoDTO> page = meetService.findDraft(pageable);
        model.addAttribute("page", page);
        return "/meet/draft";
    }

    // 从草稿箱复制会议信息
    private String copyMeetInfo(String oldMeetId) throws SystemException {
        Meet oldMeet = meetService.findMeet(oldMeetId);
        String newMeetId = null;
        if (oldMeet != null) {
            Meet newMeet = oldMeet;
            newMeet.setId(UUIDUtil.getNowStringID());
            newMeetId = meetService.addMeetInfo(newMeet);
            // 复制会议文件资料
            MeetMaterial condition = new MeetMaterial();
            condition.setMeetId(oldMeetId);
            List<MeetMaterial> materials = meetMaterialService.select(condition);
            if (!CheckUtils.isEmpty(materials)) {
                for (MeetMaterial material : materials) {
                    material.setMeetId(newMeetId);
                    MeetMaterial newMaterial = material;
                    newMaterial.setId(null);
                    meetMaterialService.insert(newMaterial);
                }
            }

            // 复制会议文件夹
            InfinityTreeDetail detail = new InfinityTreeDetail();
            detail.setResourceId(oldMeetId);
            InfinityTreeDetail treeDetail = meetFolderService.selectTreeDetail(detail);
            if (treeDetail != null) {
                treeDetail.setId(null);
                treeDetail.setResourceId(newMeetId);
                treeDetail.setResourceName(newMeet.getMeetName());
                meetFolderService.insertDetail(treeDetail);
            }
        }
        return newMeetId;
    }

    // 复制会议课程模块
    private void copyModuleCourse(String oldMeetId, String newMeetId) throws SystemException {
        // 查询会议所有模块
        List<MeetModule> moduleList = meetService.findModules(oldMeetId);
        if (!CheckUtils.isEmpty(moduleList)) {
            for (MeetModule oldMeetModule : moduleList) {
                // 复制模块表
                Integer newModuleId = meetService.addModuleReturnId(newMeetId, oldMeetModule);

                int oldFunctionId = oldMeetModule.getFunctionId();

                // 查询草稿会议的课程ID
                Integer oldCourseId = meetService.findModuleCourseId(oldMeetModule);

                // 根据courseId查询 草稿会议的课程表 并复制创建新的会议课程表获取新的课程ID
                if (oldFunctionId == MeetModule.ModuleFunction.PPT.getFunId()) {
                    // 复制PPT课程会议
                    copyAudioCourse(oldCourseId, newModuleId, newMeetId);
                } else if (oldFunctionId == MeetModule.ModuleFunction.VIDEO.getFunId()) {
                    // 复制视频课程会议
                    copyVideoCourse(oldCourseId, newModuleId, newMeetId);
                } else if (oldFunctionId == MeetModule.ModuleFunction.EXAM.getFunId()) {
                    // 复制考试课程会议
                    MeetExam oldExam = examService.findExam(oldMeetId, oldMeetModule.getId());
                    if (oldExam != null) {
                        copyExam(oldExam, newModuleId, newMeetId);
                    }
                } else if (oldFunctionId == MeetModule.ModuleFunction.SURVEY.getFunId()) {
                    // 复制问卷调查课程会议
                    copySurvey(oldCourseId, newModuleId, newMeetId);
                } else {
                    // 复制签到课程会议
                    MeetPosition oldPosition = signService.findPosition(oldMeetId, oldMeetModule.getId());
                    if (oldPosition != null) {
                        MeetPosition position = oldPosition;
                        position.setId(null);
                        position.setMeetId(newMeetId);
                        position.setModuleId(newModuleId);
                        signService.insert(position);
                    }
                }
            }
        }
    }

    private void copyAudioCourse(Integer oldCourseId, Integer newModuleId, String newMeetId) {
        AudioCourse oldAudioCourse = null;
        if (oldCourseId != null && oldCourseId != 0) {
            oldAudioCourse = audioService.findAudioCourse(oldCourseId);
        }
        Integer newCourseId = 0;
        if (oldAudioCourse != null) {
            // 复制PPT语音课程
            AudioCourse newAudioCourse = oldAudioCourse;
            newAudioCourse.setId(null);
            audioService.insert(newAudioCourse);
            newCourseId = newAudioCourse.getId();
            // 复制ppt明细表
            if (!CheckUtils.isEmpty(oldAudioCourse.getDetails())) {
                List<AudioCourseDetail> oldDetailList = oldAudioCourse.getDetails();
                for (AudioCourseDetail detail : oldDetailList) {
                    detail.setId(null);
                    detail.setCourseId(newCourseId);
                }
                audioService.insertBatch(oldDetailList);
            }
        }

        // 复制ppt语音表
        MeetAudio newAudio = new MeetAudio();
        newAudio.setId(null);
        newAudio.setMeetId(newMeetId);
        newAudio.setModuleId(newModuleId);
        newAudio.setCourseId(newCourseId);
        audioService.addMeetAudio(newAudio);


    }

    private void copyVideoCourse(Integer oldCourseId, Integer newModuleId, String newMeetId) {
        VideoCourse videoCourse = new VideoCourse();
        videoCourse.setId(oldCourseId);
        VideoCourse oldVideoCourse = videoService.selectByPrimaryKey(videoCourse);
        Integer newCourseId = 0;
        if (oldVideoCourse != null) {
            // 复制视频课程
            VideoCourse newVideoCourse = oldVideoCourse;
            newVideoCourse.setId(null);
            videoService.insert(newVideoCourse);
            newCourseId = newVideoCourse.getId();
        }

        // 复制视频表
        MeetVideo newVideo = new MeetVideo();
        newVideo.setMeetId(newMeetId);
        newVideo.setModuleId(newModuleId);
        newVideo.setCourseId(newCourseId);
        videoService.insertMeetVideo(newVideo);

        List<VideoCourseDetail> oldDetailList = videoService.findRootDetail(oldCourseId);
        if (!CheckUtils.isEmpty(oldDetailList)) {
            for (VideoCourseDetail detail : oldDetailList) {
                Integer oldPreId = detail.getId();
                List<VideoCourseDetail> oldLeafDetailList = videoService.findByPreid(oldPreId);
                // 复制父目录数据
                detail.setId(null);
                detail.setCourseId(newCourseId);
                Integer newPreId = videoService.addDetail(detail);
                // 复制子目录数据
                if (!CheckUtils.isEmpty(oldLeafDetailList)) {
                    for (VideoCourseDetail leafDetail : oldLeafDetailList) {
                        leafDetail.setId(null);
                        leafDetail.setCourseId(newCourseId);
                        leafDetail.setPreId(newPreId);
                        videoService.addDetail(leafDetail);
                    }
                }
            }
        }
    }

    private void copyExam(MeetExam oldExam, Integer newModuleId, String newMeetId) {
        Integer oldPaperId = oldExam.getPaperId();
        ExamPaper oldExamPaper = examService.findExamPaper(oldPaperId);
        if (oldExamPaper != null) {
            ExamPaper newExamPaper = oldExamPaper;
            newExamPaper.setId(null);
            Integer newPaperId = examService.addPaper(newExamPaper);

            MeetExam newExam = oldExam;
            newExam.setId(null);
            newExam.setPaperId(newPaperId);
            newExam.setModuleId(newModuleId);
            newExam.setMeetId(newMeetId);
            examService.insert(newExam);

            List<ExamPaperQuestion> oldQuestionList = examService.findQuestionListByPaperId(oldPaperId);
            if (!CheckUtils.isEmpty(oldQuestionList)) {
                for (ExamPaperQuestion paperQuestion : oldQuestionList) {
                    Integer oldQuestionId = paperQuestion.getQuestionId();
                    ExamQuestion oldQuestion = examService.findQuestion(paperQuestion.getPaperId(), oldQuestionId);
                    ExamQuestion newQuestion = oldQuestion;
                    newQuestion.setId(null);
                    Integer newQuestionId = examService.addQuestion(newQuestion);

                    paperQuestion.setId(null);
                    paperQuestion.setPaperId(newPaperId);
                    paperQuestion.setQuestionId(newQuestionId);
                    examService.insertExamPaperQuestion(paperQuestion);
                }
            }
        }
    }

    private void copySurvey(Integer oldPaperId, Integer newModuleId, String newMeetId) {
        SurveyPaper oldSurvey = surveyService.findSurveyPaper(oldPaperId);
        if (oldSurvey != null) {
            SurveyPaper newSurveyPaper = oldSurvey;
            newSurveyPaper.setId(null);
            Integer newPaperId = surveyService.addSurveyPaper(newSurveyPaper);

            MeetSurvey newSurvey = new MeetSurvey();
            newSurvey.setPaperId(newPaperId);
            newSurvey.setModuleId(newModuleId);
            newSurvey.setMeetId(newMeetId);
            surveyService.insert(newSurvey);

            List<SurveyQuestion> oldQuestionList = oldSurvey.getQuestionList();
            for (SurveyQuestion question : oldQuestionList) {
                question.setId(null);
                question.setPaperId(newPaperId);
                surveyService.addQuestion(question);
            }

        }
    }


    /**
     * 复制草稿 跳转到编辑会议界面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/draft/copy")
    public String copyMeetDraft(String id) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), id)) {
            throw new SystemException(SpringUtils.getMessage("meet.notmine"));
        }
        // 复制会议信息
        String newMeetId = copyMeetInfo(id);
        // 复制会议模块数据
        copyModuleCourse(id, newMeetId);
        return "redirect:/func/meet/edit?id=" + newMeetId;
    }

    /**
     * 跳转到添加会议页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String id, Model model) {
        boolean isAdd = StringUtils.isEmpty(id);
        if (!isAdd) {
            Meet meet = meetService.selectByPrimaryKey(id);
            model.addAttribute("meet", meet);
        }
        List<Department> departs = hospitalService.findAllDepart();
        model.addAttribute("departs", departs);
        return "/meet/add";
    }

    /**
     * 检测资源引用的合法性
     *
     * @param courseId
     * @throws SystemException
     */
    private void checkCourse(Integer courseId) throws SystemException {
        if (courseId != null && courseId != 0) {
            AudioCourse course = audioService.selectByPrimaryKey(courseId);
            //判断资源是否存在
            if (course == null) {
                throw new SystemException("您引用的资源不存在");
            }
            Principal principal = SubjectUtils.getCurrentUser();
            //判断资源是否属于当前用户
            if (principal.getId() != course.getOwner()) {
                throw new SystemException("您无法引用不属于您的资源");
            }
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Meet meet, Integer[] funIds, Integer courseId) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        checkCourse(courseId);
        boolean isAdd = StringUtils.isEmpty(meet.getId());
        if (isAdd) {
            meet.setId(UUIDUtil.getNowStringID());
            meet.setState((short) 0);
            meet.setOrganizer(principal.getNickname());
            meet.setCreateTime(new Date());
            meet.setOwnerId(principal.getId());
            meet.setShared(false);
            meet.setTuijian(false);
            meet.setOwner(principal.getNickname());
            meetService.addMeet(meet, funIds);

            //如果有选择文件夹，将会议添加到文件夹下
            String folderId = meet.getFolderId();
            if (folderId != null && !"".equals(folderId)) {
                InfinityTreeDetail detail = new InfinityTreeDetail();
                detail.setInfinityId(folderId);
                detail.setResourceId(meet.getId());
                detail.setResourceName(meet.getMeetName());
                meetFolderService.insertDetail(detail);
            }

            if (courseId != null && courseId != 0) {
                MeetAudio audio = audioService.findMeetAudioByMeetId(meet.getId());
                if (audio != null) {
                    audio.setCourseId(courseId);
                    audioService.updateMeetAudio(audio);
                }
            }
        } else {
            meetService.updateMeet(meet, funIds);
        }
        return "redirect:/func/meet/config?meetId=" + meet.getId();
    }

    /**
     * 检查象数
     *
     * @param requiredXs 奖励1或支付0象数
     * @param xsCredits  奖励或支付的象数值
     * @param limit      奖励限制人数
     * @throws SystemException
     */
    @RequestMapping("/check/user/credit")
    @ResponseBody
    public String checkXsCredit(Integer requiredXs, Integer xsCredits, Integer limit) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        Credits credits = creditsService.doFindMyCredits(principal.getId());
        if (requiredXs != null && requiredXs == 1) {
            if (credits.getCredit() < limit * xsCredits) {
                return APIUtils.error(credits.getCredit());
            }
        }
        return APIUtils.success(credits.getCredit());
    }

    private void checkCredits(Integer eduCredits, Integer xsCredits, Integer userId, Integer limit) throws SystemException {
        if (eduCredits != null && eduCredits == 1) {
            if (xsCredits == null) {
                xsCredits = 0;
            }
            if (xsCredits > 0) {//判断象数是否足够
                if (limit == null || limit == 0) {
                    throw new SystemException("请填写奖励人数限制");
                }
                Credits credits = creditsService.doFindMyCredits(userId);
                if (credits.getCredit() < limit * xsCredits) {
                    throw new SystemException("保存会议失败,您的象数不足");
                }
            }
        }
    }


    private void loadMeetInfo(String id, Model model) {
        Meet meet = meetService.findMeet(id);
        model.addAttribute("meet", meet);
        //格式化开始时间和结束时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startTime = format.format(meet.getMeetProperty().getStartTime());
        String endTime = format.format(meet.getMeetProperty().getEndTime());
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        //查询参会人员限制
        findMemberLimit(meet, model);

        //查询出会议的所有模块
        List<MeetModule> moduleList = meetService.findModules(id);
        model.addAttribute("moduleList", moduleList);
        Map<String, Boolean> moduleMap = Maps.newHashMap();
        for (MeetModule module : moduleList) {
            moduleMap.put(String.valueOf(module.getFunctionId()), true);
        }
        //查询会议所在的文件夹
        InfinityTreeDetail detail = new InfinityTreeDetail();
        detail.setResourceId(meet.getId());
        detail = meetFolderService.selectTreeDetail(detail);
        if (detail != null) {
            String preName = meetFolderService.getAllFolder(detail.getInfinityId());
            model.addAttribute("preName", preName);
            model.addAttribute("folderId", detail.getInfinityId());
        }

        model.addAttribute("moduleMap", moduleMap);
    }


    private void loadExtraInfo(Model model) {
        Principal principal = SubjectUtils.getCurrentUser();
        //查询出省份信息
        List<SystemRegion> provinces = systemRegionService.findRegionByPreid(0);
        model.addAttribute("provinces", provinces);
        model.addAttribute("appFileBase", appFileBase);
        //查询出个人象数
        Credits credits = creditsService.doFindMyCredits(principal.getId());
        model.addAttribute("credits", credits.getCredit());
        //查询出科室列表
        List<Department> departs = hospitalService.findAllDepart();
        model.addAttribute("departs", departs);
        //查询出所有医生分组
        List<Group> groups = doctorService.findGroupList(principal.getId());
        model.addAttribute("groups", groups);
        //查询会议文件夹列表
        InfinityTree tree = new InfinityTree();
        tree.setUserId(principal.getId());
        List<InfinityTree> list = meetFolderService.select(tree);
        if (CheckUtils.isEmpty(list)) {
            model.addAttribute("folderList", null);
        } else {
            list = getSubTreeList(list);
            model.addAttribute("folderList", list);
        }
    }


    private void loadCourseInfo(Integer courseId, Model model) throws SystemException {
        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
    }

    @RequestMapping(value = "/edit")
    public String edit(String id, Model model, Integer courseId) throws SystemException {
        if (!StringUtils.isEmpty(id)) {
            loadMeetInfo(id, model);
        }
        //加载会议相关的属性
        loadExtraInfo(model);
        //加载资源引用相关的信息
        if (courseId != null && courseId != 0) {
            loadCourseInfo(courseId, model);
        }
        return "/meet/add";
    }

    private void findMemberLimit(Meet meet, Model model) {
        String specifyProvince = meet.getMeetProperty().getSpecifyProvince();
        Integer memberLimitType = meet.getMeetProperty().getMemberLimitType();
        Integer groupId = meet.getMeetProperty().getGroupId();
        if (memberLimitType == null) {
            memberLimitType = 0;
        }
        if (memberLimitType == 1 && !StringUtils.isEmpty(specifyProvince)) {
            List<SystemRegion> cities = systemRegionService.findRegionByPreName(specifyProvince);
            model.addAttribute("cities", cities);
        } else if (memberLimitType == 2 &&
                (groupId != null && groupId != 0)) {// 指定群组
            // 查询组名
            Group group = new Group();
            group.setId(groupId);
            Group g = doctorService.selectByPrimaryKey(group);
            if (g != null) {
                model.addAttribute("group", g);
            }
        }
    }


    @RequestMapping(value = "/lecturer/uphead")
    @ResponseBody
    public String uploadHeadImg(@RequestParam(value = "file", required = false) MultipartFile file) {
        String dir = FilePath.PORTRAIT.path;
        try {
            FileUploadResult result = fileUploadService.upload(file, dir);
            Map<String, String> map = Maps.newHashMap();
            map.put("url1", result.getAbsolutePath());//绝对路径
            map.put("url2", result.getRelativePath());//相对路径
            return APIUtils.success(map);
        } catch (SystemException e) {
            return APIUtils.error(e.getMessage());
        }

    }


    @RequestMapping(value = "/saveDraft")
    @ResponseBody
    public String saveDraft(Meet meet) {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meet.getId())) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        meetService.updateMeet(meet);
        return APIUtils.success();
    }


    @RequestMapping(value = "/report")
    public String report(String id, Model model) {

        Meet meet = meetService.findMeet(id);
        model.addAttribute("meet", meet);
        return "/meet/report";
    }

    @RequestMapping(value = "/config")
    public String config(String meetId, Integer moduleId, Model model) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            throw new SystemException(SpringUtils.getMessage("meet.notmine"));
        }
        model.addAttribute("appFileBase", appFileBase);
        List<MeetModule> modules = meetService.findModules(meetId);
        Meet meet = meetService.findMeetSimple(meetId);
        model.addAttribute("modules", modules);
        MeetModule module = null;
        if (moduleId == null || moduleId == 0) {
            module = modules.get(0);
            moduleId = module.getId();
        } else {
            for (MeetModule m : modules) {
                if (moduleId.intValue() == m.getId().intValue()) {
                    module = m;
                    break;
                }
            }
        }
        model.addAttribute("module", module);
        model.addAttribute("moduleId", moduleId);
        model.addAttribute("appFileBase", appFileBase);
        model.addAttribute("meetId", meetId);
        return setModuleInfo(meet, module, model);
    }

    private AudioCourse createCourse(Meet meet, MeetAudio meetAudio) {
        AudioCourse course = new AudioCourse();
        course.setCreateTime(new Date());
        course.setShareType(0);
        course.setCredits(0);
        course.setShared(false);
        course.setPublished(false);
        course.setCategory(meet.getMeetType());
        Principal principal = SubjectUtils.getCurrentUser();
        course.setOwner(principal.getId());
        course.setTitle(meet.getMeetName());
        course.setPrimitiveId(0);
        audioService.insert(course);
        meetAudio.setCourseId(course.getId());
        audioService.updateMeetAudio(meetAudio);
        return course;
    }


    private VideoCourse addVideoCourse(MeetVideo video) {
        Principal principal = SubjectUtils.getCurrentUser();
        Meet meet = meetService.selectByPrimaryKey(video.getMeetId());
        VideoCourse course = new VideoCourse();
        course.setCategory(meet.getMeetType());
        course.setTitle(meet.getMeetName());
        course.setCreateTime(new Date());
        course.setPublished(false);
        course.setOwner(principal.getId());
        Integer courseId = videoService.addCourse(video, course);
        course.setId(courseId);
        return course;
    }

    private String setModuleInfo(Meet meet, MeetModule module, Model model) {
        switch (module.getFunctionId()) {
            case 1://ppt+音频
                MeetAudio meetAudio = audioService.findMeetAudioSimple(meet.getId(), module.getId());
                if (meetAudio != null &&
                        (meetAudio.getCourseId() != null && meetAudio.getCourseId() != 0)) {
                    model.addAttribute("course", audioService.findAudioCourse(meetAudio.getCourseId()));
                }
                break;
            case 2://视频
                MeetVideo meetVideo = videoService.findMeetVideoSimple(meet.getId(), module.getId());
                model.addAttribute("meetVideo", meetVideo);
                if (meetVideo != null && meetVideo.getCourseId() != null) {
                    VideoCourse course = videoService.selectByPrimaryKey(meetVideo.getCourseId());
                    List<VideoCourseDetail> details = videoService.findRootDetail(meetVideo.getCourseId());
                    model.addAttribute("details", details);
                    model.addAttribute("course", course);
                } else {
                    VideoCourse course = addVideoCourse(meetVideo);
                    model.addAttribute("course", course);
                }
                break;
            case 3://考试
                MeetExam exam = examService.findExam(meet.getId(), module.getId());
                model.addAttribute("exam", exam);
                if (exam != null &&
                        (exam.getPaperId() != null && exam.getPaperId() != 0)) {
                    model.addAttribute("paper", examService.findExamPaper(exam.getPaperId()));
                } else {
                    model.addAttribute("paper", createPaper(exam));
                }
                break;
            case 4://问卷
                MeetSurvey survey = surveyService.findMeetSurveySimple(meet.getId(), module.getId());
                model.addAttribute("survey", survey);
                if (survey != null &&
                        (survey.getPaperId() != null && survey.getPaperId() != 0)) {
                    model.addAttribute("paper", surveyService.findSurveyPaper(survey.getPaperId()));
                } else {
                    model.addAttribute("paper", createSurveyPaper(survey));
                }
                break;
            case 5:
                MeetPosition position = signService.findPosition(meet.getId(), module.getId());
                if (position != null) {
                    if (position.getStartTime() != null) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        String startTime = format.format(position.getStartTime());
                        String endTime = format.format(position.getEndTime());
                        model.addAttribute("startTime", startTime);
                        model.addAttribute("endTime", endTime);
                    }
                }
                model.addAttribute("position", position);
                break;
            default:
                break;
        }
        model.addAttribute("funId", module.getFunctionId());
        return "/meet/config_" + module.getFunctionId();
    }


    private SurveyPaper createSurveyPaper(MeetSurvey survey) {
        Principal principal = SubjectUtils.getCurrentUser();
        Meet meet = meetService.selectByPrimaryKey(survey.getMeetId());
        SurveyPaper paper = new SurveyPaper();
        paper.setPaperName(meet.getMeetName());
        paper.setCreateTime(new Date());
        paper.setOwner(principal.getId());
        paper.setCategory(meet.getMeetType());
        Integer paperId = surveyService.addSurveyPaper(paper);
        survey.setPaperId(paperId);
        surveyService.updateByPrimaryKeySelective(survey);
        return paper;
    }

    private ExamPaper createPaper(MeetExam exam) {
        Principal principal = SubjectUtils.getCurrentUser();
        Meet meet = meetService.selectByPrimaryKey(exam.getMeetId());
        ExamPaper paper = new ExamPaper();
        paper.setPaperName(meet.getMeetName());
        paper.setCreateTime(new Date());
        paper.setOwner(principal.getId());
        paper.setShared(false);
        paper.setCategory(meet.getMeetType());
        paper.setTotalPoint(0);
        Integer paperId = examService.addPaper(paper);
        exam.setPaperId(paperId);
        examService.updateMeetExam(exam);
        return paper;
    }


    @RequestMapping(value = "/view")
    public String view(String id, Model model, Integer tag) {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), id)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        Meet meet = meetService.findMeet(id);
        model.addAttribute("meet", meet);
        model.addAttribute("modules", meetService.findModules(id));
        model.addAttribute("pubUserHeadImg", appFileBase + principal.getHeadimg());
        model.addAttribute("appFileBase", appFileBase);
        //Integer attendCount = meetService.getMeetAttendCount(id);
        Integer awardCount = meetService.findGetRewardUserCount(id, MeetRewardHistory.rewardLabel.XS.getRewardType());
        model.addAttribute("attendCount", awardCount);

        String qrCodePath = checkAndCreateQRCode(id);
        model.addAttribute("qrCodePath", qrCodePath);

        //将会议简介中的回车换成<br>
//        if(!StringUtils.isEmpty(meet.getIntroduction())){
//            meet.setIntroduction(meet.getIntroduction().replaceAll("\n","<br>"));
//        }
        if (tag != null && tag == 1) {
            return "/meet/detail";
        } else {
            return "/meet/view";
        }

    }


    protected String checkAndCreateQRCode(String meetId) {
        String qrCodeFilePath = appFileBase + FilePath.QRCODE.path + "/" + meetId + "." + QRCodeUtils.PICTURE_FORMAT;
        boolean accessAble = HttpUtils.accessAble(qrCodeFilePath);
        if (!accessAble) {
            String meetAccessUrl = yayaAppBase + "view/meet/info?id=" + meetId;
            QRCodeUtils.createQRCode(meetAccessUrl, appUploadBase + "/" + FilePath.QRCODE.path + "/" + meetId + "." + QRCodeUtils.PICTURE_FORMAT);
        }
        return qrCodeFilePath;
    }


    @RequestMapping(value = "/finish")
    public String finish(String meetId, Integer moduleId) {
        List<MeetModule> modules = meetService.findModules(meetId);
        Integer nextModuleId = 0;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getId().intValue() == moduleId.intValue()) {
                if (i < modules.size() - 1) {
                    nextModuleId = modules.get(i + 1).getId();
                }
                break;
            }
        }
        if (nextModuleId == 0) {
            //最后一个模块 跳转到资料编辑
            return "redirect:/func/meet/materials?meetId=" + meetId;
        } else {
            return "redirect:/func/meet/config?meetId=" + meetId + "&moduleId=" + nextModuleId;
        }
    }

    @RequestMapping(value = "/getsign")
    @ResponseBody
    public String getSign(String meetId) {
        // 查询会议的模块
        //List<MeetModule> modlist = meetService.findAllModules(meetId);
        String pos = meetService.findSignPos(meetId);
        return APIUtils.success(pos);
    }


    @RequestMapping(value = "/materials")
    public String materials(String meetId, Model model) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            throw new SystemException("您无法操作不属于您的会议");
        }
        MeetMaterial condition = new MeetMaterial();
        condition.setMeetId(meetId);
        List<MeetMaterial> materials = meetMaterialService.select(condition);
        model.addAttribute("list", materials);

        List<MeetModule> modules = meetService.findModules(meetId);
        model.addAttribute("modules", modules);
        model.addAttribute("meetId", meetId);
        return "/meet/materialList";
    }


    @RequestMapping(value = "/publish")
    @ResponseBody
    public String publish(String meetId, Integer saveAction) {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            return APIUtils.error("您不能操作不属于您的会议");
        }
        try {
            meetService.doPublish(meetId, saveAction);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        return APIUtils.success();
    }

    @RequestMapping(value = "/uploadMetarial")
    @ResponseBody
    public String uploadMaterial(@RequestParam(value = "file", required = false) MultipartFile file, String meetId) {
        if (file == null) {
            return error("不能上传空文件");
        }
        if (file.getSize() > Constants.MEET_MATERIAL_LIMIT_SIZE) {
            return APIUtils.error("文件大小超出限制");
        }
        String dir = "material" + File.separator + meetId;
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        Principal principal = SubjectUtils.getCurrentUser();
        String originalName = file.getOriginalFilename();
        String suffix = file.getOriginalFilename().substring(originalName.lastIndexOf(".") + 1);
        MeetMaterial material = new MeetMaterial();
        material.setMeetId(meetId);
        material.setFileUrl(result.getRelativePath());
        material.setCreateTime(new Date());
        material.setFileSize(file.getSize());
        material.setFileType(suffix);
        material.setName(originalName);
        material.setUserId(principal.getId());
        meetMaterialService.insert(material);
        return APIUtils.success();
    }

    public static void main(String[] args) {
        String fileName = "123123.pdf";
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println(suffix);

    }


    @RequestMapping(value = "/delMaterial")
    public String delMaterial(Integer id, String meetId, RedirectAttributes redirectAttributes) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            throw new SystemException("您无法操作不属于您的会议");
        }
        MeetMaterial material = meetMaterialService.selectByPrimaryKey(id);
        if (material != null) {
            meetMaterialService.deleteByPrimaryKey(id);
            if (!StringUtils.isEmpty(material.getFileUrl())) {
                File file = new File(appUploadBase + material.getFileUrl());
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        addFlashMessage(redirectAttributes, "删除会议课件成功");
        return "redirect:/func/meet/materials?meetId=" + meetId;
    }


    /**
     * 将已发布会议的状态改为4
     *
     * @return
     */
    @RequestMapping("/deleteMeet")
    @ResponseBody
    public String changeMeetStatus(String meetId) {
        if (StringUtils.isEmpty(meetId)) {
            return error("会议id不能为空");
        }
        Meet meet = new Meet();
        meet.setId(meetId);
        meet.setState(Meet.MeetType.CANCEL.getState());
        meet.setOwnerId(SubjectUtils.getCurrentUserid());
        int count = meetService.updateByPrimaryKeySelective(meet);

        InfinityTreeDetail detail = new InfinityTreeDetail();
        detail.setResourceId(meetId);
        detail = meetFolderService.selectTreeDetail(detail);
        if (detail != null) {
            meetFolderService.deleteTreeDetail(detail);
        }
        return count > 0 ? success() : error("删除失败");
    }


    /**
     * 删除会议
     *
     * @param meetId
     * @return
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(String meetId) {
        Principal principal = SubjectUtils.getCurrentUser();
        boolean isMine = meetService.checkMeetIsMine(principal.getId(), meetId);
        if (!isMine) {
            return APIUtils.error("您无法删除不属于您的会议");
        }
        meetService.deleteMeet(meetId);
        return success();
    }

//
//    @RequestMapping(value = "/toDraft")
//    @ResponseBody
//    public String toDraft(String meetId){
//        Principal principal = SubjectUtils.getCurrentUser();
//        boolean isMine = meetService.checkMeetIsMine(principal.getId(), meetId);
//        if(!isMine){
//            return APIUtils.error("您不能删除不属于您的会议");
//        }else{
//            Meet meet = meetService.selectByPrimaryKey(meetId);
//            if(meet != null){
//                meet.setState(Meet.MeetType.DRAFT.getState());
//                meetService.updateByPrimaryKeySelective(meet);
//            }
//            return success();
//        }
//    }


    /**
     * 更新或新增文件夹
     *
     * @param tree
     * @return
     */
    @RequestMapping("/saveFolder")
    @ResponseBody
    public String saveFolder(InfinityTree tree) {

        if (tree == null) {
            return error("参数不能为空");
        }
        if (!StringUtils.isEmpty(tree.getId())) {  //更新操作
            InfinityTree result = meetFolderService.selectByPrimaryKey(tree.getId());
            if (result == null) {
                return error("id错误");
            }
            meetFolderService.updateByPrimaryKeySelective(tree);
            return success();
        } else {  //保存操作
            String id = UUIDUtil.getNowStringID();
            Integer userId = SubjectUtils.getCurrentUserid();
            tree.setUserId(userId);
            tree.setId(id);
            if (StringUtils.isEmpty(tree.getPreid())) {
                tree.setPreid("0");
            }
            tree.setMountType(Constants.NUMBER_ONE);
            meetFolderService.insertSelective(tree);
            return success(tree.getId());
        }

    }

}
