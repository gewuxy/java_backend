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
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.SeeVideoRecordExcelData;
import cn.medcn.meet.dto.VideoCourseRecordDTO;
import cn.medcn.meet.dto.VideoProgressDTO;
import cn.medcn.meet.dto.VideoStatisticsExcelData;
import cn.medcn.meet.model.VideoCourse;
import cn.medcn.meet.model.VideoCourseDetail;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.VideoService;
import com.google.common.collect.Lists;
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

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/6.
 */
@Controller
@RequestMapping(value = "/func/meet/video")
public class VideoController extends BaseController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private MeetService meetService;

    @Value("${app.file.upload.base}")
    private String appUploadBase;

    @RequestMapping(value = "/sublist")
    @ResponseBody
    public String sublist(Integer preId, Integer courseId) {
        List<VideoCourseDetail> list = videoService.findByPreid(preId);
        if (preId == null || preId == 0) {
            list = videoService.findRootDetail(courseId);
        }
        return APIUtils.success(list);
    }


    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(Integer detailId) {
        VideoCourseDetail detail = videoService.findDetail(detailId);
        return APIUtils.success(detail);
    }


    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(VideoCourseDetail detail) {
        return APIUtils.success(detail);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(VideoCourseDetail detail) {
        if (detail.getId() == null) {
            Integer detailId = videoService.addDetail(detail);
            detail.setId(detailId);
        } else {
            videoService.updateDetail(detail);
        }
        return APIUtils.success(detail);
    }


    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(Integer id) {
        VideoCourseDetail detail = videoService.findDetail(id);
        if (detail == null) {
            return APIUtils.error("不能删除空");
        }
        videoService.deleteDetail(id);
        return APIUtils.success();
    }


    /**
     * 视频统计 查询视频观看人数记录
     *
     * @param pageable
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/statistics")
    public String videoRecord(Pageable pageable, String id, Model model) {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), id)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if (!StringUtils.isEmpty(id)) {
            pageable.getParams().put("meetId", id);
            MyPage<VideoCourseRecordDTO> page = videoService.findVideoRecords(pageable);
            model.addAttribute("page", page);
        }
        return "/tongji/videoStastic";
    }

    /**
     * 导出 视频观看人数记录
     *
     * @param meetId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportvideo")
    @ResponseBody
    public String exportVideo(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }

        if (!StringUtils.isEmpty(meetId)) {
            Workbook workbook = null;
            String fileName = "视频观看人数统计.xls";
            List<Object> dataList = Lists.newArrayList();

            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("meetId", meetId);
            List<VideoCourseRecordDTO> videoRecordList = videoService.findVideoRecordExcel(conditionMap);
            if (!CheckUtils.isEmpty(videoRecordList)) {
                for (VideoCourseRecordDTO recordDTO : videoRecordList) {
                    VideoStatisticsExcelData videoExcel = new VideoStatisticsExcelData();
                    videoExcel.setVideoTitle(recordDTO.getName());
                    videoExcel.setVideoDuration(recordDTO.getDuration());
                    videoExcel.setViewNubmer(recordDTO.getTotalCount() == null ? 0 : recordDTO.getTotalCount());
                    dataList.add(videoExcel);
                }

                workbook = ExcelUtils.writeExcel(fileName, dataList, VideoStatisticsExcelData.class);
            } else {
                return APIUtils.error("暂无数据导出");
            }

            try {
                ExcelUtils.outputWorkBook(fileName, workbook, response);
            } catch (Exception e) {
                e.printStackTrace();
                return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
            }
        }
        return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
    }

    /**
     * 视频观看进度
     *
     * @param pageable
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/progress")
    @ResponseBody
    public String viewProgress(Pageable pageable, String id) {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), id)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if (!StringUtils.isEmpty(id)) {
            pageable.put("meetId", id);
            MyPage<VideoProgressDTO> page = videoService.findVideoProgress(pageable);
            if (page != null
                    && !CheckUtils.isEmpty(page.getDataList())) {

                for (VideoProgressDTO vDTO : page.getDataList()) {
                    assignedToVideoProgress(vDTO);
                }

            }
            return APIUtils.success(page);
        } else {
            return APIUtils.error("会议ID不能为空");
        }
    }

    /**
     * 导出视频观看进度
     *
     * @param meetId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportProgress")
    @ResponseBody
    public String exportProgress(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }

        if (!StringUtils.isEmpty(meetId)) {
            Workbook workbook = null;
            String fileName = "视频观看进度统计.xls";
            List<Object> dataList = Lists.newArrayList();

            Map<String, Object> conditionMap = new HashMap();
            conditionMap.put("meetId", meetId);
            List<VideoProgressDTO> progressList = videoService.findVProgressExcel(conditionMap);

            if (!CheckUtils.isEmpty(progressList)) {
                for (VideoProgressDTO proDTO : progressList) {
                    proDTO = assignedToVideoProgress(proDTO);
                    SeeVideoRecordExcelData excelData = new SeeVideoRecordExcelData();
                    excelData.setVideoTitle(proDTO.getName());
                    excelData.setVideoDuration(proDTO.getDuration().toString());
                    excelData.setName(proDTO.getNickname());
                    excelData.setWatchTime(proDTO.getUsedtime() == null ? 0 : proDTO.getUsedtime());
                    excelData.setWatchProgress(proDTO.getViewProgress() == null ? "0%" : proDTO.getViewProgress());
                    dataList.add(excelData);
                }

                workbook = ExcelUtils.writeExcel(fileName, dataList, SeeVideoRecordExcelData.class);

            } else {
                return APIUtils.error("暂无数据导出");
            }

            try {
                ExcelUtils.outputWorkBook(fileName, workbook, response);
            } catch (Exception e) {
                e.printStackTrace();
                return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
            }
        }
        return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
    }

    /**
     * 赋值 视频观看进度对象
     *
     * @param progressDTO
     * @return
     */
    public VideoProgressDTO assignedToVideoProgress(VideoProgressDTO progressDTO) {
        DecimalFormat df = new DecimalFormat("0%");
        Integer usetime = progressDTO.getUsedtime();
        if (usetime != 0) {
            float progress = ((float) usetime / (float) progressDTO.getDuration());
            String proPercent = df.format(progress);
            if (progress >= 1.0) {
                progressDTO.setViewProgress("100%");
            } else {
                progressDTO.setViewProgress(proPercent);
            }
        } else {
            progressDTO.setViewProgress("0%");
        }
        return progressDTO;
    }

    @RequestMapping(value = "/finish")
    public String finish(String meetId, Integer courseId, Integer moduleId) {
        if (courseId != null) {
            VideoCourse course = videoService.selectByPrimaryKey(courseId);
            course.setPublished(true);
            videoService.updateByPrimaryKeySelective(course);
        }
        return "redirect:/func/meet/finish?meetId=" + meetId + "&moduleId=" + moduleId;
    }


    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, Integer courseId, Integer preId, Integer detailId) {
        FileUploadResult result;
        try {
            if (file.getSize() > Constants.UPLOAD_VIDEO_SIZE_LIMIT * Constants.BYTE_UNIT_M) {
                return error("视频文件不能大于500M");
            }
            result = fileUploadService.upload(file, FilePath.COURSE.path + "/" + courseId + "/video");
            VideoCourseDetail detail = new VideoCourseDetail();
            detail.setUrl(result.getAbsolutePath());
            detail.setVideoType(VideoCourseDetail.VideoType.INNER_LINK.ordinal());
            detail.setName(file.getOriginalFilename());
            detail.setPreId(preId);
            detail.setFileSize(file.getSize());
            detail.setType(VideoCourseDetail.VideoDetailType.VIDEO.ordinal());
            detail.setDuration(FFMpegUtils.duration(appUploadBase + "/" + result.getRelativePath()));
            detail.setCourseId(courseId);
            if (detailId != null && detailId != 0) {
                detail.setId(detailId);
                videoService.updateDetail(detail);
            } else {
                videoService.insertDetail(detail);
            }

            return success(detail);
        } catch (SystemException e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }
}
