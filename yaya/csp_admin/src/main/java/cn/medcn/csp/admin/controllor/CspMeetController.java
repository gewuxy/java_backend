package cn.medcn.csp.admin.controllor;

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
import cn.medcn.common.supports.upload.FileUploadProgress;
import cn.medcn.common.utils.*;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.CourseReprintDTO;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.dto.VideoDownloadDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.LiveService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.model.UserFluxUsage;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.UserFluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * by create HuangHuibin 2017/11/9
 */
@Controller
@RequestMapping(value="/csp/meet")
public class CspMeetController extends BaseController{

    @Value("${app.csp.base}")
    protected String cspBase;

    @Value("${app.file.base}")
    protected String fileBase;

    @Value("${app.file.upload.base}")
    protected String fileUploadBase;

    @Autowired
    private AudioService audioService;

    @Autowired
    private LiveService liveService;

    @Autowired
    protected UserFluxService userFluxService;

    @Autowired
    protected CspUserPackageService cspUserPackageService;

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Autowired
    protected FileUploadService fileUploadService;
    @Autowired
    protected OpenOfficeService openOfficeService;

    @RequestMapping(value="/list")
    @Log(name = "获取会议列表")
    public String searchMeetList(Pageable pageable, Integer deleted,String keyword, Model model){
        if (!StringUtils.isEmpty(keyword)) {
            pageable.put("keyword", keyword);
            model.addAttribute("keyword",keyword);
        }
        if (deleted != null) {
            pageable.put("deleted", deleted);
            model.addAttribute("deleted",deleted);
        }
        MyPage<AudioCourse> page = audioService.findAllMeetForManage(pageable);
        model.addAttribute("page",page);
        return "/meet/meetList";
    }

    @RequestMapping(value = "/edit")
    @Log(name = "跳转编辑讲本页面")
    public String toEditAudioCourse() {
        return "/meet/editMeetInfo";
    }

    @RequestMapping(value = "/save")
    @Log(name = "编辑讲本信息")
    public String saveAudioCourseInfo(AudioCourse course,Model model,RedirectAttributes redirectAttributes) {
        if (course != null){
            audioService.updateByPrimaryKey(course);
            addFlashMessage(redirectAttributes, "编辑讲本信息成功");
            return "redirect:/csp/meet/list";
        }
        return null;
    }


    @RequestMapping(value="/info")
    @Log(name = "查询会议详情")
    public String searchMeetInfo(Integer id,Model model){
        CourseDeliveryDTO info =  audioService.findMeetDetail(id);
        model.addAttribute("meet",info);
        return "/meet/meetInfo";
    }

    @RequestMapping(value = "/view/{courseId}")
    @ResponseBody
    public String view(@PathVariable Integer courseId) {
        AudioCourse course = audioService.findAudioCourse(courseId);
        audioService.handleHttpUrl(fileBase, course);
        return success(course);
    }

    @RequestMapping(value="/delete")
    @Log(name = "关闭会议")
    public String delete(Integer id, Integer status, RedirectAttributes redirectAttributes){
        AudioCourse course = audioService.selectByPrimaryKey(id);
        if (status == 1) { // 关闭会议
            course.setDeleted(true);
            addFlashMessage(redirectAttributes, "关闭成功");
        } else { // 撤销关闭会议
            course.setDeleted(false);
            addFlashMessage(redirectAttributes, "撤销关闭成功");
        }
        audioService.updateByPrimaryKeySelective(course);
        //更新缓存
        if (course.getCspUserId() != null){
            cspUserService.updatePackagePrincipal(course.getCspUserId());
        }
        return "redirect:/csp/meet/list";
    }


    /**
     * 生成视频下载地址
     * @param userId
     * @param courseId
     * @return
     */
    @RequestMapping("/download/address")
    @ResponseBody
    public String createAddress(String userId,String courseId){
        if(StringUtils.isEmpty(userId)){
            return error("userId不能为空");
        }
        if(StringUtils.isEmpty(courseId)){
            return error("courseId不能为空");
        }

        String key = MD5Utils.md5(courseId + userId);
        VideoDownloadDTO result = redisCacheUtils.getCacheObject(Constants.VIDEO_DOWNLOAD_URL + key);
        //缓存中的视频链接不存在，重新获取下载地址
        if(result == null){
            Live live = liveService.findByCourseId(Integer.valueOf(courseId));
            if(live == null){
                return error("找不到相关视频");
            }
            if(StringUtils.isEmpty(live.getReplayUrl())){
                return error("找不到视频链接");
            }

            //获取视频名称
            AudioCourse course = audioService.selectByPrimaryKey(Integer.parseInt(courseId));
            if(course == null){
               return error("获取视频名称失败");
            }

            String url = fileBase + live.getReplayUrl();
            String fileName = course.getTitle();
            VideoDownloadDTO dto = new VideoDownloadDTO();
            dto.setCourseId(courseId);
            dto.setDownloadUrl(url);
            dto.setUserId(userId);
            dto.setFileName(fileName);

            //将下载链接存到缓存中，默认30天超时
            redisCacheUtils.setCacheObject(Constants.VIDEO_DOWNLOAD_URL + key,dto,Constants.TOKEN_EXPIRE_TIME);
        }

        return success(cspBase + "/mgr/user/cache/download?key=" + key);
    }

    /**
     * 上传PPT或者PDF文件 并转换成图片
     *
     * @param file
     * @param courseId
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file") MultipartFile file, Integer courseId, HttpServletRequest request) {
        try {
            if (courseId != null) {
                audioService.editAble(courseId);
            } else {
                // 创建讲本模板
                AudioCourse audioCourse = audioService.createNewCspCourse(null);
                courseId = audioCourse.getId();
            }

        } catch (SystemException e) {
            return error(e.getMessage());
        }

        String fileName = file.getOriginalFilename();
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, FilePath.TEMP.path);
        } catch (SystemException e) {
            return local("upload.error");
        }
        String imgDir = FilePath.COURSE.path + "/" + courseId + "/ppt/";
        List<String> imgList = null;
        if (result.getRelativePath().endsWith(FileTypeSuffix.PPT_SUFFIX_PPT.suffix) || result.getRelativePath().endsWith(FileTypeSuffix.PPT_SUFFIX_PPTX.suffix)) {
            imgList = openOfficeService.convertPPT(fileUploadBase + result.getRelativePath(), imgDir, courseId, request);
        } else if (result.getRelativePath().endsWith(FileTypeSuffix.PDF_SUFFIX.suffix)) {
            imgList = openOfficeService.pdf2Images(fileUploadBase + result.getRelativePath(), imgDir, courseId, request);
        }
        if (CheckUtils.isEmpty(imgList)) {
            return error(local("upload.convert.error"));
        }
        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        if (course != null) {
            course.setSourceType(AudioCourse.SourceType.template.ordinal());
            course.setTitle(fileName.substring(0, fileName.lastIndexOf(".")));
            audioService.updateByPrimaryKey(course);
        }
        audioService.updateAllDetails(courseId, imgList);
        Map<String, Object> map = new HashMap<>();
        map.put("coverUrl", fileBase + imgList.get(0));
        map.put("title", course.getTitle());
        map.put("course",course);
        return success(map);
    }


    @RequestMapping(value = "/upload/progress")
    @ResponseBody
    public String uploadProgress(HttpServletRequest request){
        FileUploadProgress progress = (FileUploadProgress) request.getSession().getAttribute(Constants.UPLOAD_PROGRESS_KEY);
        if(progress == null){
            progress = new FileUploadProgress();
        }
        return success(progress);
    }

    @RequestMapping(value = "/upload/clear")
    @ResponseBody
    public String uploadClear(HttpServletRequest request){
        request.getSession().removeAttribute(Constants.UPLOAD_PROGRESS_KEY);
        return success();
    }


    @RequestMapping(value = "/convert/progress")
    @ResponseBody
    public String convertProgress(HttpServletRequest request){
        uploadClear(request);
        OfficeConvertProgress progress = (OfficeConvertProgress) request.getSession().getAttribute(Constants.OFFICE_CONVERT_PROGRESS);
        if (progress == null) {
            progress = new OfficeConvertProgress(0, 0, 0);
        }
        return success(progress);
    }


    @RequestMapping(value = "/convert/clear")
    @ResponseBody
    public String convertClear(HttpServletRequest request){
        request.getSession().removeAttribute(Constants.OFFICE_CONVERT_PROGRESS);
        return success();
    }

    @RequestMapping(value = "/upload/cover")
    @ResponseBody
    @Log(name = "上传讲本封面")
    public String uploadPicture(@RequestParam MultipartFile file) {
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, FilePath.COURSE_COVER.path);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success(result);
    }
}
