package cn.medcn.jcms.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.service.OfficeConvertProgress;
import cn.medcn.common.service.OpenOfficeService;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.supports.MediaInfo;
import cn.medcn.common.utils.*;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.AudioHistoryDTO;
import cn.medcn.meet.dto.AudioRecordDTO;
import cn.medcn.meet.dto.SeePPTDetailExcelData;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.CourseDeliveryService;
import cn.medcn.meet.service.LiveService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.support.UserInfoCheckHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/2.
 */
@Controller
@RequestMapping(value = "/func/meet/ppt")
public class AudioCourseController extends BaseController {

    @Autowired
    private AudioService audioService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private MeetService meetService;

    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${app.file.upload.base}")
    private String appFileUploadBase;

    @Autowired
    private LiveService liveService;

    @Autowired
    private OpenOfficeService openOfficeService;

    /**
     * 引用转载资源
     *
     * @param courseId
     * @param meetId
     * @param moduleId
     * @return
     */
    @RequestMapping(value = "/quote")
    @ResponseBody
    public String quote(Integer courseId, String meetId, Integer moduleId,Integer playType) {
        if (moduleId == null || courseId == null || StringUtils.isEmpty(meetId)) {
            return APIUtils.error("参数错误");
        }
        /*if (playType == null) {
            // 引用共享资源库 复制已获取的会议
            meetService.copyAudioCourse(courseId, moduleId, meetId);
        } */
            MeetAudio audio = audioService.findMeetAudio(meetId, moduleId);
            audio.setCourseId(courseId);
            audioService.updateMeetAudio(audio);
            //引用直播投稿,修改会议时间
            if(playType == AudioCourse.PlayType.live_ppt.getType() || playType == AudioCourse.PlayType.live_video.getType()){
                Live live = liveService.findByCourseId(courseId);
                MeetProperty property = new MeetProperty();
                property.setMeetId(meetId);
                property = meetService.findMeetProperty(meetId);
                if(property != null){
                    property.setStartTime(live.getStartTime());
                    property.setEndTime(live.getEndTime());
                    meetService.updateMeetProp(property);
                }
            }


        return APIUtils.success();
    }



    protected boolean isPick(String fileName){
        fileName = fileName.toLowerCase();
        boolean isPic = fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix)
                || fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_JPEG.suffix)
                || fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix);
        return isPic;
    }

    @RequestMapping(value = "/changeImg")
    @ResponseBody
    public String changeImg(@RequestParam(value = "file", required = false) MultipartFile file, String meetId, Integer moduleId, Integer courseId, Integer detailId) {
        String fileName = file.getOriginalFilename();
        boolean isPic = isPick(fileName);

        try {
            checkFileSize(file.getSize(), isPic);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        String dir = FilePath.COURSE.path + "/" + courseId + "/" + (isPic ? "ppt" : "video");

        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        AudioCourseDetail detail = audioService.findDetail(detailId);
        //fileUploadService.removeFile(detail.getImgUrl());
        modifyUrl(detail, isPic, result, dir);
        audioService.updateDetail(detail);
        return APIUtils.success();
    }

    /**
     * 更换ppt音频
     *
     * @param detailId
     * @return
     */
    @RequestMapping(value = "/changeAudio")
    @ResponseBody
    public String changeAudio(@RequestParam(value = "file", required = false) MultipartFile file, Integer courseId, Integer detailId) {
        String dir = FilePath.COURSE.path + File.separator + courseId + File.separator + "audio";
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        AudioCourseDetail detail = audioService.findDetail(detailId);
        fileUploadService.removeFile(detail.getAudioUrl());
        detail.setAudioUrl(result.getRelativePath());
        //解析音频时长
        MediaInfo mediaInfo = FileUtils.parseAudioMediaInfo(appFileUploadBase + detail.getAudioUrl());
        detail.setDuration(mediaInfo == null ? 0 : mediaInfo.getDuration());
        audioService.updateDetail(detail);
        return APIUtils.success();
    }


    protected void modifyUrl(AudioCourseDetail detail, boolean isPic, FileUploadResult result, String prefix){
        if (isPic) {
            detail.setImgUrl(result.getRelativePath());
            detail.setVideoUrl("");
        } else {
            detail.setImgUrl(prefix + "/" +FFMpegUtils.printScreen(appFileUploadBase + result.getRelativePath()));
            detail.setVideoUrl(result.getRelativePath());
        }
    }


    protected void checkFileSize(Long fileSize, boolean isPic) throws SystemException {
        if (isPic) {
            if (fileSize > Constants.BYTE_UNIT_M) {
                throw new SystemException("图片不能大于1M");
            }
        } else {
            if (fileSize > Constants.BYTE_UNIT_M * 50){
                throw new SystemException("视频不能大于50M");
            }
        }
    }

    /**
     * 增加新的ppt明细
     *
     * @param addtype  默认为0 0表示在最后添加 1表示在当前页前面添加 2表示在当前页后面添加
     * @param meetId
     * @param moduleId
     * @param courseId
     * @param detailId
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(@RequestParam(value = "file", required = false) MultipartFile file, Integer addtype, String meetId, Integer moduleId, Integer courseId, Integer detailId) {
        if (addtype == null) {
            addtype = 0;
        }
        if (courseId == null) {
            courseId = createCourse(meetId, moduleId);
        }
        boolean isPic = isPick(file.getOriginalFilename());

        try {
            checkFileSize(file.getSize(), isPic);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        String dir = FilePath.COURSE.path + "/" + courseId + "/" + (isPic ? "ppt" : "video");
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        AudioCourseDetail detail = new AudioCourseDetail();
        detail.setCourseId(courseId);
        switch (addtype) {
            case 0://在最后添加
                List<AudioCourseDetail> detailList = audioService.findDetails(courseId);
                detail.setSort(detailList == null || detailList.size() == 0 ? 1 : detailList.size() + 1);
                break;
            case 1://在本明细之前添加
                AudioCourseDetail existedDetail = audioService.findDetail(detailId);
                detail.setSort(existedDetail.getSort() - 1);
                break;
            case 2://在本明细之后添加
                AudioCourseDetail savedDetail = audioService.findDetail(detailId);
                if (savedDetail == null) {
                    detail.setSort(1);
                } else {
                    detail.setSort(savedDetail.getSort() + 1);
                }
                break;
            default:
                break;
        }
        modifyUrl(detail, isPic, result, dir);
        audioService.addDetail(detail);
        return APIUtils.success();
    }


    private Integer createCourse(String meetId, Integer moduleId) {
        Principal principal = SubjectUtils.getCurrentUser();
        AudioCourse course = new AudioCourse();
        Meet meet = meetService.selectByPrimaryKey(meetId);
        course.setTitle(meet.getMeetName());
        course.setPrimitiveId(0);
        course.setOwner(principal.getId());
        course.setCategory(meet.getMeetType());
        course.setCreateTime(new Date());
        course.setCredits(0);
        course.setPublished(false);
        course.setShared(false);
        course.setShareType(0);
        audioService.insert(course);
        MeetAudio meetAudio = audioService.findMeetAudioSimple(meetId, moduleId);
        meetAudio.setCourseId(course.getId());
        audioService.updateMeetAudio(meetAudio);
        return course.getId();
    }

    /**
     * 删除当前的ppt明细
     *
     * @param meetId
     * @param moduleId
     * @param detailId
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/del")
    public String del(String meetId, Integer moduleId, Integer detailId, RedirectAttributes redirectAttributes) {
        AudioCourseDetail detail = audioService.findDetail(detailId);
        Integer sort = 1;
        if (detail != null) {
            sort = detail.getSort();
            audioService.deleteDetail(detail.getCourseId(), detailId);
        }
        List<AudioCourseDetail> details = audioService.findDetails(detail.getCourseId());
        sort--;
        if (sort == details.size()) {
            sort--;
        }
        return "redirect:/func/meet/config?meetId=" + meetId + "&moduleId=" + moduleId + "&activeIndex=" + sort;
    }


    @RequestMapping(value = "/finish")
    public String finish(String meetId, Integer courseId, Integer moduleId) {
        if (courseId != null) {
            AudioCourse course = audioService.selectByPrimaryKey(courseId);
            course.setPublished(true);
            course.setShared(true);
            audioService.updateByPrimaryKeySelective(course);
        }
        return "redirect:/func/meet/finish?meetId=" + meetId + "&moduleId=" + moduleId;
    }


    /**
     * ppt完整观看人数统计 柱状图数据
     *
     * @param meetId
     * @param tagNo
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/viewpptCount")
    @ResponseBody
    public String viewpptCount(String meetId, Integer tagNo, String startTime, String endTime) {
        Principal principal = SubjectUtils.getCurrentUser();
        boolean isMine = meetService.checkMeetIsMine(principal.getId(), meetId);
        if (!isMine) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        List<AudioHistoryDTO> audioList;
        Map<String, Object> dataMap = null;
        if (!StringUtils.isEmpty(meetId)) {
            Map<String, Object> map = new HashMap<>();
            map.put("meetId", meetId);
            map.put("tagNo", tagNo);
            AudioHistoryDTO audioHistoryDTO = new AudioHistoryDTO();
            audioHistoryDTO.setTagNo(tagNo);
            if (tagNo < 3) {
                startTime = audioHistoryDTO.getStartTime();
                endTime = audioHistoryDTO.getEndTime();
            }

            if (!StringUtils.isBlank(startTime)) {
                map.put("startTime", startTime);
            }
            if (!StringUtils.isBlank(endTime)) {
                map.put("endTime", endTime);
            }

            audioList = audioService.findViewPptCount(map);
            dataMap = new HashMap();
            dataMap.put("startTime", startTime);
            dataMap.put("endTime", endTime);
            dataMap.put("list", audioList);
        }
        return APIUtils.success(dataMap);
    }

    /**
     * ppt内容统计
     *
     * @param
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/content/statistics")
    public String contentStatistics(String id, Model model) {
        Principal principal = SubjectUtils.getCurrentUser();
        boolean isMine = meetService.checkMeetIsMine(principal.getId(), id);
        if (!isMine) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if (!StringUtils.isEmpty(id)) {
            Integer viewCount = audioService.findViewCount(id);
            model.addAttribute("viewCount", viewCount);
        }
        return "/tongji/contentStastic";
    }

    /**
     * 获取用户观看ppt记录 列表
     *
     * @param pageable
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/record")
    @ResponseBody
    public String viewPPtRecord(Pageable pageable, String id) {
        Principal principal = SubjectUtils.getCurrentUser();
        boolean isMine = meetService.checkMeetIsMine(principal.getId(), id);
        if (!isMine) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        MyPage<AudioRecordDTO> page = null;
        if (!StringUtils.isEmpty(id)) {
            pageable.put("meetId", id);
            page = audioService.findAudioRecord(pageable);
            for (AudioRecordDTO dto : page.getDataList()) {
                if (!StringUtils.isEmpty(dto.getHeadimg())) {
                    dto.setHeadimg(appFileBase + dto.getHeadimg());
                }
            }
        } else {
            return APIUtils.error("会议ID不能为空");
        }
        return APIUtils.success(page);
    }


    /**
     * 导出用户观看ppt时长明细 excel
     *
     * @param userId
     * @param meetId
     * @param response
     */
    @RequestMapping(value = "/pptexcel")
    @ResponseBody
    public String userPPTExcel(Integer userId, String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        boolean isMine = meetService.checkMeetIsMine(principal.getId(), meetId);
        if (!isMine) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if (meetId != null) {
            Workbook workbook = null;
            List<Object> dataList = Lists.newArrayList();
            String fileName = " PPT明细.xls";

            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("userId", principal.getId());
            conditionMap.put("meetId", meetId);

            if (userId != null && userId != 0) {// 导出单个用户ppt明细时 需传入用户ID
                conditionMap.put("id", userId);
            }

            int pptTotalCount = 0;
            List<AudioRecordDTO> recordDTOList = audioService.findAudioRecordtoExcel(conditionMap);
            if (!CheckUtils.isEmpty(recordDTOList)) {
                fileName = recordDTOList.get(0).getMeetName() + fileName;
                Map<Integer, List> userRecordMap = Maps.newHashMap();
                for (AudioRecordDTO audioDTO : recordDTOList) {
                    if (userRecordMap.get(audioDTO.getId()) == null) {
                        List<AudioRecordDTO> userRecordList = Lists.newArrayList();
                        userRecordList.add(audioDTO);
                        userRecordMap.put(audioDTO.getId(), userRecordList);
                    } else {
                        userRecordMap.get(audioDTO.getId()).add(audioDTO);
                    }
                }

                // 查询ppt数据
                List<AudioCourseDetail> audioCourseList = audioService.findPPtTotalCount(meetId);
                DecimalFormat fmt = new DecimalFormat("0%");
                if (!CheckUtils.isEmpty(audioCourseList)) {
                    pptTotalCount = audioCourseList.size();
                    for (Integer id : userRecordMap.keySet()) {
                        List<AudioRecordDTO> userRecordList = userRecordMap.get(id);
                        AudioRecordDTO defaultInfo = userRecordList.get(userRecordList.size() - 1);

                        for (AudioCourseDetail courseDetail : audioCourseList) {
                            SeePPTDetailExcelData detailExcelData = new SeePPTDetailExcelData();
                            for (AudioRecordDTO recordDTO : userRecordList) {
                                if (recordDTO.getSort().intValue() == courseDetail.getSort().intValue()) {
                                    recordDTO.setSort(courseDetail.getSort().intValue());
                                    recordDTO.setDuration(courseDetail.getDuration());
                                    recordDTO.setTime(defaultInfo.getTime());
                                    detailExcelData = fillExcelData(recordDTO);
                                    detailExcelData.setFinishRate(fmt.format((float) recordDTO.getPptCount() / (float) pptTotalCount));
                                    break;
                                }
                            }

                            if (StringUtils.isBlank(detailExcelData.getName())) {
                                defaultInfo.setSort(courseDetail.getSort().intValue());
                                defaultInfo.setDuration(courseDetail.getDuration());
                                defaultInfo.setUsedtime(0);
                                detailExcelData = fillExcelData(defaultInfo);
                                detailExcelData.setFinishRate(fmt.format((float) defaultInfo.getPptCount() / (float) pptTotalCount));
                            }
                            dataList.add(detailExcelData);
                        }
                    }
                }

                workbook = ExcelUtils.writeExcel(fileName, dataList, SeePPTDetailExcelData.class);

            } else {
                return APIUtils.error("暂无数据导出");
            }

            try {
                //int[] columnIndexArray = new int[]{0, 1, 2, 3, 4, 5, 6, 10, 11};
                // 需要合并的列
                Integer nameIndex = SeePPTDetailExcelData.columnIndex.NAME.getColumnIndex();
                Integer unitNameIndex = SeePPTDetailExcelData.columnIndex.UNIT_NAME.getColumnIndex();
                Integer subUnitNameIndex = SeePPTDetailExcelData.columnIndex.SUB_UNIT_NAME.getColumnIndex();
                Integer hosLevelIndex = SeePPTDetailExcelData.columnIndex.HOS_LEVEL.getColumnIndex();
                Integer titleIndex = SeePPTDetailExcelData.columnIndex.TITLE.getColumnIndex();
                Integer provinceIndex = SeePPTDetailExcelData.columnIndex.PROVINE.getColumnIndex();
                Integer groupIndex = SeePPTDetailExcelData.columnIndex.GROUP_NAME.getColumnIndex();
                Integer watchTotalTimeIndex = SeePPTDetailExcelData.columnIndex.WATCH_TOTAL_TIME.getColumnIndex();
                Integer finishRateIndex = SeePPTDetailExcelData.columnIndex.FINISH_RATE.getColumnIndex();
                int[] columnIndexArray = new int[]{nameIndex, unitNameIndex, subUnitNameIndex, hosLevelIndex, titleIndex,
                        provinceIndex, groupIndex, watchTotalTimeIndex, finishRateIndex};
                if (pptTotalCount > 1) {
                    ExcelUtils.createMergeExcel(fileName, workbook, pptTotalCount, columnIndexArray, response);
                } else {
                    ExcelUtils.outputWorkBook(fileName, workbook, response);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
            }

        }
        return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
    }


    /**
     * 填充用户观看ppt记录 excel表格
     *
     * @param audioRecordDTO
     * @return
     */
    private SeePPTDetailExcelData fillExcelData(AudioRecordDTO audioRecordDTO) {
        UserInfoCheckHelper.checkPPTDetailUserDTO(audioRecordDTO);

        SeePPTDetailExcelData excelData = new SeePPTDetailExcelData();
        excelData.setName(audioRecordDTO.getNickName());
        excelData.setUnitName(audioRecordDTO.getUnitName());
        excelData.setSubUnitName(audioRecordDTO.getSubUnitName());
        excelData.setHosLevel(audioRecordDTO.getLevel());
        excelData.setTitle(audioRecordDTO.getTitle());
        excelData.setProvince(audioRecordDTO.getProvince() + audioRecordDTO.getCity());
        excelData.setGroupName(audioRecordDTO.getGroupName());
        excelData.setPptSort("第" + audioRecordDTO.getSort().toString() + "页");
        if (audioRecordDTO.getDuration() == null) {
            excelData.setPptDuration("未知");
        }
        excelData.setWatchTime(audioRecordDTO.getUsedtime().toString());
        excelData.setWatchTotalTime(CalendarUtils.secToTime(audioRecordDTO.getTime()));
        excelData.setFinishRate("0%");
        return excelData;
    }


    /**
     * 上传录音文件
     *
     * @param file
     * @param courseId
     * @param detailId
     * @return
     */
    @RequestMapping(value = "/uploadRecord")
    @ResponseBody
    public String uploadRecord(@RequestParam(value = "file", required = false) MultipartFile file, Integer courseId, Integer detailId) {
        String dir = FilePath.COURSE.path + "/" + courseId + "/audio";
        FileUploadResult result;
        try {
            result = fileUploadService.uploadBinaryWav(file, dir);
            FFMpegUtils.wavToMp3(appFileUploadBase + result.getRelativePath(), appFileUploadBase + dir);
            //删除wav文件
            File wavFile = new File(appFileUploadBase + result.getRelativePath());
            if (wavFile.exists()) {
                wavFile.delete();
            }
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        AudioCourseDetail detail = audioService.findDetail(detailId);
        fileUploadService.removeFile(detail.getAudioUrl());
        detail.setAudioUrl(result.getRelativePath().replace(".wav", ".mp3"));
        //解析音频时长
        MediaInfo mediaInfo = FileUtils.parseAudioMediaInfo(appFileUploadBase + detail.getAudioUrl());
        detail.setDuration(mediaInfo == null ? 0 : mediaInfo.getDuration());
        audioService.updateDetail(detail);
        return APIUtils.success();
    }


    @RequestMapping(value = "/uploadPPT")
    @ResponseBody
    public String uploadPPT(@RequestParam(value = "file", required = false) MultipartFile file, String meetId, Integer courseId, HttpServletRequest request) {
        Principal principal = SubjectUtils.getCurrentUser();
        request.getSession().removeAttribute(Constants.OFFICE_CONVERT_PROGRESS);
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            return APIUtils.error("您无法操作不属于您的会议");
        }
        if (courseId == null || courseId == 0) {
            courseId = createCourse(meetId, 0);
        }
        String fileName = file.getOriginalFilename();
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, FilePath.TEMP.path);
            String imgDir = FilePath.COURSE.path + "/" + courseId + "/ppt/";
            List<String> imgList = null;
            if (fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
                imgList = openOfficeService.convertPPT(appFileUploadBase + result.getRelativePath(), imgDir, courseId, request);
            } else if (fileName.endsWith(".pdf")) {
                imgList = openOfficeService.pdf2Images(appFileUploadBase + result.getRelativePath(), imgDir, courseId, request);
            }
            if (CheckUtils.isEmpty(imgList)) {
                return error("解析PPT/PDF文件出错");
            }
            saveOrUpdatePPTCourse(meetId, courseId, imgList);
        } catch (SystemException e) {
            return APIUtils.error(e.getMessage());
        }

        return APIUtils.success();
    }

    /**
     * 添加或者修改PPT微课信息
     *
     * @param meetId
     * @param courseId
     * @param pptList
     */
    private void saveOrUpdatePPTCourse(String meetId, Integer courseId, List<String> pptList) throws SystemException {
        audioService.updateAllDetails(courseId, pptList);
    }


    /**
     * 将ppt转换成图片的进度放到session中 在前端显示
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/convertStatus")
    @ResponseBody
    public String convertStatus(HttpServletRequest request) {
        HttpSession session = request.getSession();
        OfficeConvertProgress progress = (OfficeConvertProgress) session.getAttribute(Constants.OFFICE_CONVERT_PROGRESS);
        if (progress == null) {
            progress = new OfficeConvertProgress();
        } else {
            progress.setFinished(FileUtils.countFiles(new File(appFileUploadBase + FilePath.COURSE.path + "/" + progress.getCourseId() + "/ppt/")));
        }
        return APIUtils.success(progress);
    }
}
