package cn.medcn.csp.controller.web;

import cn.medcn.common.Constants;
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
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.dto.CspAudioCourseDTO;
import cn.medcn.csp.security.Principal;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.CourseCategoryService;
import cn.medcn.meet.service.LiveService;
import cn.medcn.meet.service.MeetWatermarkService;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.model.*;
import cn.medcn.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

import static cn.medcn.csp.CspConstants.MEET_COUNT_OUT_TIPS_KEY;
import static cn.medcn.csp.CspConstants.MIN_FLUX_LIMIT;

/**
 * Created by lixuan on 2017/10/17.
 */
@Controller
@RequestMapping(value = "/mgr/meet")
public class MeetingMgrController extends CspBaseController {

    @Autowired
    protected AudioService audioService;

    @Value("${app.file.upload.base}")
    protected String fileUploadBase;

    @Value("${app.file.base}")
    protected String fileBase;

    @Value("${csp.app.csp.base}")
    protected String appCspBase;

    @Autowired
    protected OpenOfficeService openOfficeService;

    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected AppUserService appUserService;

    @Autowired
    protected CourseCategoryService courseCategoryService;

    @Autowired
    protected LiveService liveService;

    @Autowired
    protected UserFluxService userFluxService;

    @Autowired
    protected MeetWatermarkService watermarkService;

    @Autowired
    protected CspUserPackageService cspUserPackageService;

    @Autowired
    protected CspPackageService cspPackageService;

    @Autowired
    protected CspUserService cspUserService;


    @Autowired
    protected CspUserPackageHistoryService cspUserPackageHistoryService;

    /**
     * 查询当前用户的课件列表
     *
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Pageable pageable, Model model, String keyword, Integer playType, String sortType, HttpServletRequest request) {
        pageable.setPageSize(6);

        //打开了投稿箱的公众号列表
        Pageable pageable2 = new Pageable();
        MyPage<AppUser> myPage = appUserService.findAccepterList(pageable2);
        AppUser.splitUserAvatar(myPage.getDataList(), fileBase);
        model.addAttribute("accepterList", myPage.getDataList());
        //web获取当前用户信息
        Principal principal = getWebPrincipal();

        if (meetCountOut()){
            model.addAttribute("meetCountOut", true);

            if (CookieUtils.getCookie(request, MEET_COUNT_OUT_TIPS_KEY) == null) {
                model.addAttribute("showTips", true);
            }
        }

        sortType = CheckUtils.isEmpty(sortType) ? "desc" : sortType;

        //高级版和专业版进行时间提醒
        Integer packageId = principal.getPackageId() == null ? CspPackage.TypeId.STANDARD.getId() : principal.getPackageId();
        if(packageId != CspPackage.TypeId.STANDARD.getId()){
            model.addAttribute("expireTimeCount",principal.getCspPackage().getExpireDays());
        }

        pageable.put("sortType", sortType);
        pageable.put("cspUserId", principal.getId());
        pageable.put("keyword", keyword);
        pageable.put("playType", playType);

        model.addAttribute("keyword", keyword);
        model.addAttribute("playType", playType);
        model.addAttribute("sortType", sortType);

        MyPage<CourseDeliveryDTO> page = audioService.findCspMeetingList(pageable);

        //如果第二页或其他页数查找到无会议时，用前一页的会议列表代替(不加以下判断，删除会议时可能会出现无会议内容的情况)
        if(page.getDataList().size() == 0 && pageable.getPageNum() != 1){
            pageable.setPageNum(pageable.getPageNum() - 1);
            page = audioService.findCspMeetingList(pageable);
        }

        CourseDeliveryDTO.splitCoverUrl(page.getDataList(),fileBase);
        model.addAttribute("page", page);
        model.addAttribute("newUser",principal.getNewUser());
        model.addAttribute("successMsg",principal.getPkChangeMsg());
        return localeView("/meeting/list");
    }



    /**
     * 进入投屏界面
     *
     * @param courseId
     * @param model
     * @return
     */
    @RequestMapping(value = "/screen/{courseId}")
    public String screen(@PathVariable Integer courseId, Model model, HttpServletRequest request) throws SystemException {
        AudioCourse course = audioService.findAudioCourse(courseId);
        Principal principal = getWebPrincipal();
        if (!principal.getId().equals(course.getCspUserId())) {
            throw new SystemException(local("meeting.error.not_mine"));
        }

        if (course.getPlayType() == null) {
            course.setPlayType(AudioCourse.PlayType.normal.getType());
        }

        if (course.getDeleted() != null && course.getDeleted()) {
            throw new SystemException(local("source.has.deleted"));
        }

        if (course.getLocked() != null && course.getLocked()) {
            throw new SystemException(local("course.error.locked"));
        }

        model.addAttribute("course", course);
        String wsUrl = genWsUrl(request, courseId);
        model.addAttribute("wsUrl", wsUrl);

        String scanUrl = genScanUrl(request, courseId);

        model.addAttribute("scanUrl", scanUrl);
        model.addAttribute("fileBase", fileBase);
        //判断二维码是否存在 不存在则重新生成
        String qrCodePath = FilePath.QRCODE.path + "/course/" + courseId + ".png";
        boolean qrCodeExists = FileUtils.exists(fileUploadBase + qrCodePath);
        if (!qrCodeExists) {
            QRCodeUtils.createQRCode(scanUrl, fileUploadBase + qrCodePath);
        }

        model.addAttribute("fileBase", fileBase);
        model.addAttribute("qrCodeUrl", qrCodePath);

        if (course.getPlayType().intValue() == AudioCourse.PlayType.normal.getType()) {
            AudioCoursePlay play = audioService.findPlayState(courseId);
            model.addAttribute("record", play);
            return localeView("/meeting/screen_record");
        } else {
            //查询出直播信息
            Live live = liveService.findByCourseId(courseId);
            if (live == null) {
                throw new SystemException(local("error.data"));
            }

            if (live.getLiveState() != null && live.getLiveState().intValue() == AudioCoursePlay.PlayState.over.ordinal()) {
                throw new SystemException(local("share.live.over"));
            }

            Date now = new Date();
            if (live.getStartTime().after(now)) {
                throw new SystemException(local("share.live.not_start.error"));
            } else if (live.getEndTime().before(now)){
                throw new SystemException(local("share.live.over"));
            }

            model.addAttribute("live", live);
            return localeView("/meeting/screen");
        }


    }


    /**
     * 生成二维码的地址
     *
     * @param request
     * @return
     */
    protected String genScanUrl(HttpServletRequest request, Integer courseId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(appCspBase);
        buffer.append("/api/meeting/scan/callback?courseId=");
        buffer.append(courseId);
        return buffer.toString();
    }

    /**
     * 进入课件编辑页面
     * 如果courseId为空 则查找最近编辑的未发布的AudioCourse
     *
     * @param courseId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(Integer courseId, Model model, HttpServletRequest request) throws SystemException {
        uploadClear(request);
        convertClear(request);

        Principal principal = getWebPrincipal();

        if (meetCountOut()) {
            return defaultRedirectUrl();
        }

        AudioCourse course = null;
        if (courseId != null) {
            course = audioService.findAudioCourse(courseId);

            boolean editAble = audioService.editAble(courseId);
            if (!editAble) {
                throw new SystemException(courseNonEditAbleError());
            }
            //水印信息
            MeetWatermark watermark = watermarkService.findWatermarkByCourseId(courseId);
            model.addAttribute("watermark",watermark);

        } else {
            course = audioService.findLastDraft(principal.getId());
            if (course == null) {
                course = audioService.createNewCspCourse(principal.getId());
            }
        }
        if (course.getPlayType() == null) {
            course.setPlayType(AudioCourse.PlayType.normal.getType());
        }

        if (course.getCategoryId() != null) {
            model.addAttribute("courseCategory", courseCategoryService.selectByPrimaryKey(course.getId()));
        }

        model.addAttribute("rootList", courseCategoryService.findByLevel(CourseCategory.CategoryDepth.root.depth));
        model.addAttribute("subList", courseCategoryService.findByLevel(CourseCategory.CategoryDepth.sub.depth));
        model.addAttribute("course", course);
        model.addAttribute("fileBase", fileBase);

        if (course.getPlayType() == null) {
            course.setPlayType(AudioCourse.PlayType.normal.getType());
        }

        if (course.getPlayType() > AudioCourse.PlayType.normal.getType()) {
            model.addAttribute("live", liveService.findByCourseId(course.getId()));
        } else {
            model.addAttribute("play", audioService.findPlayState(course.getId()));
        }
        UserFlux flux = userFluxService.selectByPrimaryKey(principal.getId());
        float fluxValue = flux == null ? 0f : Math.round(flux.getFlux() * 1.0f / Constants.BYTE_UNIT_K * 100) / 100;
        model.addAttribute("flux", fluxValue);
        model.addAttribute("packageId",getWebPrincipal().getPackageId());
        return localeView("/meeting/edit");
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
        if (!audioService.editAble(courseId)) {
            return error(courseNonEditAbleError());
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
            course.setTitle(fileName.substring(0, fileName.lastIndexOf(".")));
            audioService.updateByPrimaryKey(course);
        }
        audioService.updateAllDetails(courseId, imgList);
        return success();
    }


    protected void handleHttpPath(AudioCourse course) {
        audioService.handleHttpUrl(fileBase, course);
    }

    /**
     * 进入到PPT明细编辑页面
     *
     * @param courseId
     * @param model
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/details/{courseId}")
    public String details(@PathVariable Integer courseId, Model model) throws SystemException {
        AudioCourse course = audioService.findAudioCourse(courseId);
        if (course == null) {
            throw new SystemException(local("source.not.exists"));
        }
        handleHttpPath(course);
        Principal principal = getWebPrincipal();
        if (!principal.getId().equals(course.getCspUserId())) {
            throw new SystemException(local("meeting.error.not_mine"));
        }
        model.addAttribute("course", course);
        return localeView("/meeting/details");
    }

    /**
     * @param file
     * @param index
     * @return
     */
    @RequestMapping(value = "/detail/add")
    @ResponseBody
    public String add(@RequestParam(value = "file") MultipartFile file, Integer courseId, Integer index) {
        if (!audioService.editAble(courseId)) {
            return error(courseNonEditAbleError());
        }

        boolean isPicture = isPicture(file.getOriginalFilename());
        String dir = FilePath.COURSE.path + "/" + courseId + "/" + (isPicture ? "ppt" : "video");
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        AudioCourseDetail detail = new AudioCourseDetail();
        detail.setCourseId(courseId);
        detail.setSort(index + 1);

        if (isPicture) {
            detail.setImgUrl(result.getRelativePath());
        } else {
            detail.setVideoUrl(result.getRelativePath());
            detail.setImgUrl(dir + "/" + FFMpegUtils.printScreen(fileUploadBase + result.getRelativePath()));
        }

        audioService.addDetail(detail);
        return success();
    }


    protected boolean isPicture(String fileName) {
        fileName = fileName.toLowerCase();
        boolean isPic = fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix)
                || fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_JPEG.suffix)
                || fileName.endsWith(FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix);
        return isPic;
    }


    @RequestMapping(value = "/detail/del/{courseId}/{detailId}")
    public String del(@PathVariable Integer courseId, @PathVariable Integer detailId) throws SystemException {
        if (!audioService.editAble(courseId)) {
            throw new SystemException(courseNonEditAbleError());
        }

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

    @RequestMapping(value = "/del/{courseId}")
    @ResponseBody
    public String del(@PathVariable Integer courseId) {
        if (!audioService.editAble(courseId)) {
            return error(courseNonDeleteAble());
        }

        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        Principal principal = getWebPrincipal();
        if (!principal.getId().equals(course.getCspUserId())) {
            return error(local("meeting.error.not_mine"));
        }

        course.setDeleted(true);
        audioService.updateByPrimaryKey(course);

        updatePackagePrincipal(principal.getId());

        //当前删除的会议如果是锁定状态则不处理 否则需要解锁用户最早的一个锁定的会议
        if (course.getLocked() != null || !course.getLocked()) {
            //判断是否有锁定的会议
            if (principal.getPackageId().intValue() != CspPackage.TypeId.PROFESSIONAL.getId()){
                AudioCourse earliestActiveCourse = audioService.findEarliestCourse(principal.getId());
                if (earliestActiveCourse != null) {
                    earliestActiveCourse.setLocked(false);
                }
                audioService.updateByPrimaryKey(earliestActiveCourse);
            }
        }

        return success();
    }


    @RequestMapping(value = "/more/{courseId}")
    public String more(@PathVariable Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return localeView("/meeting/more");
    }


    @RequestMapping(value = "/view/{courseId}")
    @ResponseBody
    public String view(@PathVariable Integer courseId, Model model) {
        AudioCourse course = audioService.findAudioCourse(courseId);
        handleHttpPath(course);
        return success(course);
    }


    @RequestMapping(value = "/copy/{courseId}")
    @ResponseBody
    public String copy(@PathVariable Integer courseId, String title) {
        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        Principal principal = getWebPrincipal();
        if (!principal.getId().equals(course.getCspUserId())) {
            return error(local("meeting.error.not_mine"));
        }
        audioService.addCourseCopy(courseId, title);

        updatePackagePrincipal(principal.getId());

        return success();
    }


    @RequestMapping(value = "/share/{courseId}")
    @ResponseBody
    public String share(@PathVariable Integer courseId, HttpServletRequest request) {
        String local = LocalUtils.getLocalStr();
        Principal principal = getWebPrincipal();
        boolean abroad = principal.getAbroad();
        StringBuffer buffer = new StringBuffer();
        buffer.append("id=").append(courseId).append("&").append(Constants.LOCAL_KEY).append("=")
                .append(local).append("&abroad=" + (abroad ? CspUserInfo.AbroadType.abroad.ordinal() : CspUserInfo.AbroadType.home.ordinal()));
        String signature = DESUtils.encode(Constants.DES_PRIVATE_KEY, buffer.toString());

        StringBuffer buffer2 = new StringBuffer();
        try {
            buffer2.append(appCspBase)
                    .append("api/meeting/share?signature=")
                    .append(URLEncoder.encode(signature, Constants.CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("shareUrl", buffer2.toString());
        return success(result);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(CspAudioCourseDTO course, Integer openLive, String liveTime, RedirectAttributes redirectAttributes) throws SystemException {
        AudioCourse ac = course.getCourse();
        MeetWatermark newWatermark = course.getWatermark();

        //编辑操作需要判断是否可以进行修改
        if (ac.getId() != null && ac.getId() != 0) {
            if (!audioService.editAble(ac.getId())) {
                throw new SystemException(courseNonEditAbleError());
            }
        }

        if (openLive != null && openLive == 1 && ac.getPlayType() > AudioCourse.PlayType.normal.getType()) {
            ac.setPlayType(AudioCourse.PlayType.live_video.getType()); //视频直播
            //判断是否有足够的流量
            UserFlux flux = userFluxService.selectByPrimaryKey(getWebPrincipal().getId());
            if (flux == null || flux.getFlux() < MIN_FLUX_LIMIT * Constants.BYTE_UNIT_K) {
                throw new SystemException(local("user.flux.not.enough"));
            }
        }
        //更新操作，包括更新或生成水印
        Integer packageId = getWebPrincipal().getPackageId();
        audioService.updateInfo(ac,course.getLive() ,newWatermark,packageId);

        updatePackagePrincipal(getWebPrincipal().getId());

        addFlashMessage(redirectAttributes, local("operate.success"));
        return defaultRedirectUrl();
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


    @RequestMapping(value = "/editable/{courseId}")
    @ResponseBody
    public String editAble(@PathVariable Integer courseId){
        boolean editAble = audioService.editAble(courseId);
        if (editAble) {
            return success();
        } else {
            return error(courseNonEditAbleError());
        }
    }

    @RequestMapping(value = "/delete/able/{courseId}")
    @ResponseBody
    public String deleteAble(@PathVariable Integer courseId){
        boolean editAble = audioService.editAble(courseId);
        if (editAble) {
            return success();
        } else {
            return error(courseNonDeleteAble());
        }
    }

    @RequestMapping(value = "/tips/close")
    @ResponseBody
    public String closeMeetOutTips(HttpServletResponse response){

        CookieUtils.setCookie(response, MEET_COUNT_OUT_TIPS_KEY, "true");
        return success();
    }
}
