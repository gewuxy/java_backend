package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.service.OpenOfficeService;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.Principal;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.service.AudioService;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import com.pingplusplus.model.App;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuan on 2017/10/17.
 */
@Controller
@RequestMapping(value = "/mgr/meet")
public class MeetingMgrController extends CspBaseController{

    @Autowired
    protected AudioService audioService;

    @Value("${app.file.upload.base}")
    protected String fileUploadBase;

    @Value("${app.file.base}")
    protected String fileBase;

    @Autowired
    protected OpenOfficeService openOfficeService;

    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected AppUserService appUserService;

    /**
     * 查询当前用户的课件列表
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Pageable pageable, Model model){
        //打开了投稿箱的公众号列表
        MyPage<AppUser> myPage = appUserService.findAccepterList(pageable);
        AppUser.splitUserAvatar(myPage.getDataList(),fileBase);
        model.addAttribute("accepterList",myPage.getDataList());
        //web获取当前用户信息
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        pageable.put("cspUserId", principal.getId());
        MyPage<CourseDeliveryDTO> page = audioService.findCspMeetingList(pageable);
        model.addAttribute("page", page);

        return localeView("/meeting/list");
    }

    /**
     * 进入投屏界面
     * @param courseId
     * @param model
     * @return
     */
    @RequestMapping(value = "/screen/{courseId}")
    public String screen(@PathVariable Integer courseId, Model model, HttpServletRequest request) throws SystemException{
        AudioCourse course = audioService.findAudioCourse(courseId);
        Principal principal = getWebPrincipal();
        if (!principal.getId().equals(course.getCspUserId())){
            throw new SystemException(local("meeting.error.not_mine"));
        }
        model.addAttribute("course", course);
        String wsUrl = genWsUrl(request, courseId);
        model.addAttribute("wsUrl", wsUrl);

        String scanUrl = genScanUrl(request, courseId);
        //判断二维码是否存在 不存在则重新生成
        String qrCodePath = FilePath.QRCODE.path + "/course/" + courseId + ".png";
        boolean qrCodeExists = FileUtils.exists(fileUploadBase + qrCodePath);
        if (!qrCodeExists) {
            QRCodeUtils.createQRCode(scanUrl, fileUploadBase + qrCodePath);
        }

        model.addAttribute("fileBase", fileBase);
        model.addAttribute("qrCodeUrl", qrCodePath);

        return localeView("/meeting/screen");
    }


    /**
     * 生成二维码的地址
     * @param request
     * @return
     */
    protected String genScanUrl(HttpServletRequest request, Integer courseId){
        StringBuffer buffer = new StringBuffer();
        buffer.append(request.getScheme());
        buffer.append("://").append(request.getServerName()).append(":").append(request.getServerPort());
        buffer.append("/api/meeting/scan/callback?courseId=");
        buffer.append(courseId);
        return buffer.toString();
    }

    /**
     * 进入课件编辑页面
     * 如果courseId为空 则查找最近编辑的未发布的AudioCourse
     * @param courseId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(Integer courseId, Model model){
        Principal principal = getWebPrincipal();
        AudioCourse course = null;
        if (courseId != null) {
            course = audioService.findAudioCourse(courseId);
        } else {
            course = audioService.findLastDraft(principal.getId());
            if (course == null) {
                course = new AudioCourse();
                course.setPlayType(AudioCourse.PlayType.normal.getType());
                course.setPublished(false);
                course.setShared(false);
                course.setCspUserId(principal.getId());
                course.setTitle("");
                audioService.insert(course);
            }
        }
        if (course.getPlayType() == null) {
            course.setPlayType(AudioCourse.PlayType.normal.getType());
        }
        model.addAttribute("course", course);
        return localeView("/meeting/edit");
    }

    /**
     * 上传PPT或者PDF文件 并转换成图片
     * @param file
     * @param courseId
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file")MultipartFile file, Integer courseId, HttpServletRequest request){
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, FilePath.TEMP.path);
        } catch (SystemException e) {
            return local("upload.error");
        }
        String imgDir = FilePath.COURSE.path + "/" +courseId + "/ppt/";
        List<String> imgList = null;
        if (result.getRelativePath().endsWith(".ppt") || result.getRelativePath().endsWith(".pptx")) {
            imgList = openOfficeService.convertPPT(fileUploadBase + result.getRelativePath(), imgDir, courseId, request);
        } else if (result.getRelativePath().endsWith(".pdf")) {
            imgList = openOfficeService.pdf2Images(fileUploadBase + result.getRelativePath(), imgDir, courseId, request);
        }
        if (CheckUtils.isEmpty(imgList)) {
            return error(local("upload.convert.error"));
        }
        audioService.updateAllDetails(courseId, imgList);
        return success();
    }


    protected void handleHttpPath(AudioCourse course) {
        if (course != null && !CheckUtils.isEmpty(course.getDetails())) {
            for (AudioCourseDetail detail : course.getDetails()) {
                if (!CheckUtils.isEmpty(detail.getAudioUrl())) {
                    detail.setAudioUrl(fileBase + detail.getAudioUrl());
                }

                if (!CheckUtils.isEmpty(detail.getImgUrl())) {
                    detail.setImgUrl(fileBase + detail.getImgUrl());
                }

                if (!CheckUtils.isEmpty(detail.getVideoUrl())) {
                    detail.setVideoUrl(detail.getVideoUrl());
                }
            }
        }
    }

    /**
     * 进入到PPT明细编辑页面
     * @param courseId
     * @param model
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/details/{courseId}")
    public String details(@PathVariable Integer courseId, Model model) throws SystemException {
        AudioCourse course = audioService.findAudioCourse(courseId);
        handleHttpPath(course);
        Principal principal = getWebPrincipal();
        if (!principal.getId().equals(course.getCspUserId())){
            throw new SystemException(local("meeting.error.not_mine"));
        }
        model.addAttribute("course", course);
        return localeView("/meeting/details");
    }

    /**
     *
     * @param file
     * @param index
     * @return
     */
    @RequestMapping(value = "/detail/add")
    @ResponseBody
    public String add(@RequestParam(value = "file")MultipartFile file, Integer courseId, Integer index){
        boolean isPicture = isPicture(file.getOriginalFilename());
        String dir = FilePath.COURSE.path + "/" + courseId + "/" + (isPicture ? "ppt" : "video");
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        AudioCourseDetail detail = new AudioCourseDetail();
        detail.setCourseId(courseId);
        detail.setSort(index + 1);

        if (isPicture) {
            detail.setImgUrl(result.getRelativePath());
        } else {
            detail.setVideoUrl(result.getRelativePath());
        }

        audioService.addDetail(detail);
        return success();
    }


    protected boolean isPicture(String fileName){
        fileName = fileName.toLowerCase();
        boolean isPic = fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix)
                || fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_JPEG.suffix)
                || fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix);
        return isPic;
    }


    @RequestMapping(value = "/detail/del/{courseId}/{detailId}")
    public String del(@PathVariable Integer courseId, @PathVariable Integer detailId){
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
        return "redirect:/mgr/meet/details/" + courseId + "?index=" + sort;
    }
}
