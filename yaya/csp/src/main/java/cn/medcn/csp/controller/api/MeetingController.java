package cn.medcn.csp.controller.api;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.AddressDTO;
import cn.medcn.common.email.MailBean;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.EmailService;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.dto.CspCourseInfoDTO;
import cn.medcn.csp.dto.RecordUploadDTO;
import cn.medcn.csp.dto.ReportType;
import cn.medcn.csp.dto.ZeGoCallBack;
import cn.medcn.csp.live.LiveOrderHandler;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.csp.utils.TXLiveUtils;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.*;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.model.Principal;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.service.WXTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.medcn.common.Constants.*;
import static cn.medcn.common.utils.APIUtils.ERROR_CODE_COURSE_DELETED;
import static cn.medcn.csp.CspConstants.*;
import static cn.medcn.meet.dto.AudioCourseDTO.HandelCoverUrl;

/**
 * 会议控制器
 * Created by lixuan on 2017/9/27.
 */
@Controller
@RequestMapping(value = "/api/meeting")
public class MeetingController extends CspBaseController {
    /**
     * 小程序上传录音格式 约定为MP3
     */
    private static final String MINI_PROGRAM_UPLOAD_AUDIO_FORMAT = ".mp3";


    @Autowired
    protected AudioService audioService;

    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected LiveService liveService;

    @Autowired
    protected WXTokenService wxTokenService;

    @Value("${ZeGo.replay.expire.days}")
    protected int expireDays;

    @Value("${app.file.upload.base}")
    protected String fileUploadBase;

    @Value("${app.file.base}")
    protected String fileBase;

    @Value("${csp.app.csp.base}")
    protected String appCspBase;

    @Value("${mini.appid}")
    private String appId;

    @Value("${mini.secret}")
    private String secret;

    @Autowired
    protected EmailTempService emailTempService;

    @Autowired
    protected MeetWatermarkService meetWatermarkService;

    @Autowired
    protected EmailService cspEmailService;

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected CourseThemeService courseThemeService;

    @Autowired
    protected CspStarRateService cspStarRateService;

    @Autowired
    protected MeetService meetService;

    /**
     * 会议阅览
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/view")
    @ResponseBody
    public String view(Integer courseId) {
        if (courseId == null || courseId == 0) {
            return error(local("user.param.empty"));
        }
        CourseThemeDTO themeDTO = courseThemeService.findCourseTheme(courseId);
        return success(themeDTO);
    }


    /**
     * 课件分享
     *
     * @param signature 将参数进行DES加密之后的字符串
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/share")
    public String share(String signature, Model model, HttpServletRequest request, HttpServletResponse response) {
        String linkError = local("share.link.error");

        Map<String, Object> params = null;

        try {
            params = parseParams(signature);
        } catch (SystemException e) {
            model.addAttribute("error", linkError);
            return localeView("/meeting/share_error");
        }
        try {
            String id = (String) params.get("id");
            String local = (String) params.get(LOCAL_KEY);
            LocalUtils.set(LocalUtils.getByKey(local));
            LocalUtils.setLocalStr(local);

            String abroad = (String) params.get(ABROAD_KEY);

            String abroadError = local("share.aboard.error");
            linkError = local("share.link.error");

            boolean isAbroad = CheckUtils.isEmpty(abroad) ? false : ("0".equals(abroad) ? false : true);
            AddressDTO address = new AddressDTO();
            address.setCountry_id("CN");

            if (!AddressUtils.isLan(request.getRemoteHost())) {
                address = AddressUtils.parseAddress(request.getRemoteHost());
            }
            if (address != null && isAbroad && !address.isAbroad()) {
                model.addAttribute("error", abroadError);
                return localeView("/meeting/share_error");
            }

            Integer courseId = Integer.valueOf(id);
            AudioCourse course = audioService.selectByPrimaryKey(courseId);

            if (course == null) {
                model.addAttribute("error", linkError);
                return localeView("/meeting/share_error");
            }

            if (course.getPlayType() == null) {
                course.setPlayType(0);
            }

            if (course.getDeleted() != null
                    && course.getDeleted() == true
                    && course.getPlayType().intValue() == AudioCourse.PlayType.normal.getType()) {
                model.addAttribute("error", local("source.has.deleted"));
                return localeView("/meeting/share_error");
            }

            //处理简介文字的回车键替换成br
            if (course.getInfo() != null) {
                course.setInfo(course.getInfo().replace("\n", "<br>"));
            }

            //查询出星评信息
            if (course.getStarRateFlag() != null && course.getStarRateFlag()) {
                StarRateInfoDTO rateResult = cspStarRateService.findFinalRateResult(courseId);
                model.addAttribute("rateResult", rateResult);

                List<CspStarRateOption> options = cspStarRateService.findRateOptions(courseId);
                model.addAttribute("rateOptions", options);

                String ticket = CookieUtils.getCookieValue(request, CspConstants.COURSE_RATE_TICKET_KEY);
                if (CheckUtils.isEmpty(ticket)) {
                    ticket = StringUtils.uniqueStr();
                    CookieUtils.setCookie(response, COURSE_RATE_TICKET_KEY, ticket);
                } else {
                    StarRateInfoDTO rateHistory = cspStarRateService.findRateHistory(courseId, ticket);
                    model.addAttribute("rateHistory", rateHistory);
                }
            }

            //对观看密码进行简单加密
            if (CheckUtils.isNotEmpty(course.getPassword())) {
                course.setPassword(DESUtils.encode(Constants.DES_PRIVATE_KEY, course.getPassword()));
            }
            //查询出会议发布者信息
            CspUserInfo publisher = cspUserService.selectByPrimaryKey(course.getCspUserId());
            if (publisher != null && CheckUtils.isNotEmpty(publisher.getAvatar()) && !publisher.getAvatar().toLowerCase().startsWith("http")){
                publisher.setAvatar(fileBase + publisher.getAvatar());
                model.addAttribute("publisher", publisher);
            }

            //设置会议水印
            MeetWatermark watermark = meetWatermarkService.findWatermarkByCourseId(courseId);
            model.addAttribute("watermark", watermark);

            if (course.getPlayType().intValue() > AudioCourse.PlayType.normal.getType()) {
                course.setDetails(audioService.findLiveDetails(courseId));

                audioService.handleHttpUrl(fileBase, course);
                model.addAttribute("course", course);

                String wsUrl = genWsUrl(request, courseId);
                wsUrl += "&liveType=" + LiveOrderDTO.LIVE_TYPE_PPT;
                model.addAttribute("wsUrl", wsUrl);

                model.addAttribute("appStoreUrl", Constants.CSP_APP_STORE_ANDROID_URL);

                Live live = liveService.findByCourseId(courseId);
                if (live.getLiveState().intValue() == AudioCoursePlay.PlayState.deleted.ordinal()) {
                    model.addAttribute("totalLiveTime", CalendarUtils.formatTimesDiff(live.getStartTime(), live.getEndTime(), 0));
                }
                model.addAttribute("live", live);
            } else {
                course.setDetails(audioService.findDetailsByCourseId(courseId));
                AudioCoursePlay play = audioService.findPlayState(courseId);
                model.addAttribute("play", play);
                //查询出讲本的背景音乐和皮肤
                AudioCourseTheme theme = courseThemeService.findByCourseId(courseId);
                if (theme != null) {
                    AudioCourseTheme.handleUrl(theme, fileBase);
                    model.addAttribute("theme", theme);
                }
            }

            audioService.handleHttpUrl(fileBase, course);
            model.addAttribute("course", course);

            return localeView("/meeting/course_" + course.getPlayType().intValue());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", linkError);
            return localeView("/meeting/share_error");
        }
    }

    @RequestMapping(value = "/share/pwd/check")
    @ResponseBody
    public String checkPassword(Integer courseId, String password){
        if (courseId == null || courseId == 0) {
            return error();
        }

        if (CheckUtils.isEmpty(password)) {
            return error();
        }

        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        if (course == null) {
            return error();
        }

        if (!password.equalsIgnoreCase(course.getPassword())){
            return error();
        }
        return success();
    }

    /**
     * 复制副本
     *
     * @param courseId
     * @param title
     * @return
     */
    @RequestMapping("/share/copy")
    @ResponseBody
    public String copy(Integer courseId, String title) {
        Principal principal = SecurityUtils.get();
        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        if (!principal.getId().equals(course.getCspUserId())) {
            return error(local("meeting.error.not_mine"));
        }

        if (meetCountNotEnough(CspUserInfo.RegisterDevice.APP)) {
            return error(local("meet.error.count.out"));
        }

        if (courseId == null || courseId == 0
                || StringUtils.isEmpty(title)) {
            return error(local("error.param"));
        }

        int newCourseId = audioService.addCourseCopy(courseId, title);

        //更改用户缓存信息
        updatePackagePrincipal(principal.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("id", newCourseId);
        return success(map);
    }


    public Map<String, Object> parseParams(String signature) throws SystemException {
        if (CheckUtils.isEmpty(signature)) {
            throw new SystemException(local("error.param"));
        }

        String plain = DESUtils.decode(Constants.DES_PRIVATE_KEY, signature);

        String[] params = plain.split("&");
        if (params.length != 3) {
            throw new SystemException(local("error.param"));
        }

        Map<String, Object> paramMap = new HashMap<>();
        for (String param : params) {
            if (param.indexOf("=") < 0) {
                throw new SystemException(local("error.param"));
            }
            String[] paramArr = param.split("=");
            paramMap.put(paramArr[0], paramArr[1]);
        }

        return paramMap;
    }

    /**
     * 上传音频
     *
     * @param file
     * @param record
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, RecordUploadDTO record, HttpServletRequest request) {
        String osType = LocalUtils.getOSType();
        if (file == null) {
            return error(local("upload.error.null"));
        }

        AudioCourse course = audioService.selectByPrimaryKey(record.getCourseId());
        if (course == null) {
            return error(local("source.not.exists"));
        }
        if (course.getDeleted() != null && course.getDeleted()) {
            return error(APIUtils.ERROR_CODE_COURSE_DELETED, local("source.has.deleted"));
        }

        String suffix = "." + (OS_TYPE_ANDROID.equals(osType) ? FileTypeSuffix.AUDIO_SUFFIX_AMR.suffix : FileTypeSuffix.AUDIO_SUFFIX_AAC.suffix);

        String relativePath = FilePath.COURSE.path + "/" + record.getCourseId() + "/audio/";

        File dir = new File(fileUploadBase + relativePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String saveFileName = UUIDUtil.getNowStringID();
        String sourcePath = fileUploadBase + relativePath + saveFileName + suffix;
        File saveFile = new File(sourcePath);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            return error(local("upload.error"));
        }

        //删除缓存
        redisCacheUtils.delete(LiveService.SYNC_CACHE_PREFIX + record.getCourseId());

        FFMpegUtils.wavToMp3(sourcePath, fileUploadBase + relativePath);
        //删除源文件
        FileUtils.deleteTargetFile(sourcePath);

        return handleUploadResult(record, relativePath, saveFileName + "." + FileTypeSuffix.AUDIO_SUFFIX_MP3.suffix);
    }


    protected String handleUploadResult(RecordUploadDTO record, String relativePath, String saveFileName) {
        AudioCourseDetail detail = audioService.findDetail(record.getDetailId());

        detail.setAudioUrl(relativePath + saveFileName);
        detail.setDuration(FFMpegUtils.duration(fileUploadBase + detail.getAudioUrl()));
        if (record.getPlayType() == AudioCourse.PlayType.normal.getType()) {
            audioService.updateDetail(detail);
        }

        handleLiveOrRecord(record.getCourseId(), record.getPlayType(), record.getPageNum(), detail);

        Map<String, Object> result = new HashMap<>();
        result.put("audioUrl", fileBase + relativePath + saveFileName);
        result.put("duration", detail.getDuration());
        return success(result);
    }


    /**
     * 录播音频续传，合并
     * @param file
     * @param record
     * @param request
     * @return
     */
    @RequestMapping("/subsection/upload")
    @ResponseBody
    public String uploadAudio(@RequestParam(value = "file", required = false) MultipartFile file, RecordUploadDTO record, HttpServletRequest request){
        if (file == null) {
            return error(local("upload.error.null"));
        }
        if(record.getCourseId() == null || record.getDetailId() == null ||
                record.getPageNum() == null || record.getHasNext() == null || record.getAudioNum() == null){
            return error(local("user.param.empty"));
        }

        AudioCourse course = audioService.selectByPrimaryKey(record.getCourseId());
        if (course == null) {
            return error(local("source.not.exists"));
        }
        if (course.getDeleted() != null && course.getDeleted()) {
            return error(ERROR_CODE_COURSE_DELETED, local("source.has.deleted"));
        }
        String osType = LocalUtils.getOSType();
        String suffix = null;
        //小程序上传音频，格式为aac
        if(StringUtils.isEmpty(osType)){
            suffix = "." + FileTypeSuffix.AUDIO_SUFFIX_AAC.suffix;
        }else{
            suffix = "." + (OS_TYPE_ANDROID.equals(osType) ? FileTypeSuffix.AUDIO_SUFFIX_AMR.suffix : FileTypeSuffix.AUDIO_SUFFIX_AAC.suffix);
        }

        //未合并的音频存放路径
        String parentRelativePath = FilePath.COURSE.path + "/" + record.getCourseId() + "/audio/";
        String relativePath = parentRelativePath + record.getDetailId() + "/";
        File dir = new File(fileUploadBase + relativePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String saveFileName = record.getAudioNum() + "";
        String sourcePath = fileUploadBase + relativePath + saveFileName + suffix;
        //aac文件对应的MP3文件
        String mp3SourcePath = fileUploadBase + relativePath + saveFileName +  "." + FileTypeSuffix.AUDIO_SUFFIX_MP3.suffix;
        File saveFile = new File(sourcePath);
        File mp3File = new File(mp3SourcePath);
        try {
            if(mp3File.exists()){
                //如果aac文件对应的MP3文件存在，删除原来的MP3文件，防止后面aac转MP3时出错
                mp3File.delete();
            }
            file.transferTo(saveFile);
        } catch (IOException e) {
            return error(local("upload.error"));
        }

        //将音频转为MP3格式，并删除源文件
        FFMpegUtils.wavToMp3(sourcePath, fileUploadBase + relativePath + "/");
        FileUtils.deleteTargetFile(sourcePath);

        //没有下一个音频，开始合并音频
        if(!record.getHasNext()){
            //获取所有分段音频的路径
            List<String> list = FileUtils.getSubsectionAudioList(fileUploadBase + relativePath);
            //整合音频
            String saveName;
            try {
                saveName = mergeUploadAudio(list, parentRelativePath, record.getDetailId());
            } catch (SystemException e) {
                return error(e.getMessage());
            }
            return handleUploadResult(record, parentRelativePath, saveName);
        }
        return success();
    }




    /**
     * 录音上传 上传多个文件
     * 用于处理小程序续传的问题
     *
     * @param files
     * @param record
     * @param request
     * @return
     * @since csp1.1.5
     */
    @RequestMapping(value = "/upload/multiple")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile[] files, RecordUploadDTO record, HttpServletRequest request) {
        if (files == null || files.length == 0) {
            return error(local("upload.error.null"));
        }
        String relativePath = FilePath.COURSE.path + "/" + record.getCourseId() + "/audio/";

        File dir = new File(fileUploadBase + relativePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        List<String> filePathQueue;
        //上传所有的录音文件
        try {
            filePathQueue = saveRecordFiles(files, relativePath);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        //整合音频
        String saveFileName;
        try {
            saveFileName = mergeUploadAudio(filePathQueue, relativePath, record.getDetailId());
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        //删除缓存
        redisCacheUtils.delete(LiveService.SYNC_CACHE_PREFIX + record.getCourseId());

        return handleUploadResult(record, relativePath, saveFileName);
    }

    /**
     * 处理MP3合并
     *
     * @param filePathQueue
     * @param relativePath
     * @return
     * @throws SystemException
     */
    protected String mergeUploadAudio(List<String> filePathQueue, String relativePath, Integer detailId) throws SystemException {
        String saveFileName = StringUtils.nowStr() + MINI_PROGRAM_UPLOAD_AUDIO_FORMAT;

        if (CheckUtils.isEmpty(filePathQueue)) {
            throw new SystemException(local("upload.error"));
        }
        //只有单个文件的处理 不需要合并 直接返回第一个音频名称
        if (filePathQueue.size() == 1) {
            String firstAudioPath = filePathQueue.get(0);
            FileUtils.move(firstAudioPath, fileUploadBase + relativePath, saveFileName);
        } else {//多个音频文件需要合并成一个文件
            String mergePath = fileUploadBase + relativePath + saveFileName;
            FFMpegUtils.concatMp3(mergePath, true, filePathQueue.toArray(new String[filePathQueue.size()]));
        }
        return saveFileName;
    }


    protected List<String> saveRecordFiles(MultipartFile[] files, String relativePath) throws SystemException {
        List<String> filePathQueue = new ArrayList<>();
        for (MultipartFile file : files) {
            String saveFileName = UUIDUtil.getNowStringID();
            String sourcePath = fileUploadBase + relativePath + saveFileName + MINI_PROGRAM_UPLOAD_AUDIO_FORMAT;
            File saveFile = new File(sourcePath);
            try {
                file.transferTo(saveFile);
                filePathQueue.add(sourcePath);
            } catch (IOException e) {
                throw new SystemException(local("upload.error"));
            }
        }

        return filePathQueue;
    }


    protected void handleLiveDetail(Integer courseId, AudioCourseDetail detail) {
        //添加直播明细
        Integer maxSort = audioService.findMaxLiveDetailSort(courseId);
        if (maxSort == null) {
            maxSort = 0;
        }
        LiveDetail liveDetail = new LiveDetail();
        liveDetail.setCourseId(courseId);
        liveDetail.setVideoUrl(detail.getVideoUrl());
        liveDetail.setAudioUrl(detail.getAudioUrl());
        liveDetail.setImgUrl(detail.getImgUrl());
        liveDetail.setDuration(detail.getDuration());
        liveDetail.setSort(maxSort);
        audioService.addLiveDetail(liveDetail);

        //如果是直播  在上传音频完成之后发送直播指令
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(LiveOrderDTO.ORDER_LIVE);
        order.setDetailId(detail.getId());
        order.setCourseId(String.valueOf(courseId));
        order.setImgUrl(CheckUtils.isEmpty(detail.getImgUrl()) ? null : (fileBase + detail.getImgUrl()));
        order.setAudioUrl(CheckUtils.isEmpty(detail.getAudioUrl()) ? null : (fileBase + detail.getAudioUrl()));
        order.setVideoUrl(CheckUtils.isEmpty(detail.getVideoUrl()) ? null : (fileBase + detail.getVideoUrl()));
        order.setPageNum(maxSort);
        liveService.publish(order);


        //保存直播进度
        Live live = liveService.findByCourseId(courseId);
        if (live != null) {
            live.setLivePage(detail.getSort() - 1);
            if (live.getLiveState().intValue() == AudioCoursePlay.PlayState.init.ordinal()) {
                live.setStartTime(new Date());
                live.setExpireDate(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(MEET_AFTER_START_EXPIRE_HOURS)));
                live.setLiveState(AudioCoursePlay.PlayState.playing.ordinal());
            }
            liveService.updateByPrimaryKey(live);
        }
    }


    protected void handleLiveOrRecord(Integer courseId, Integer playType, Integer pageNum, AudioCourseDetail detail) {
        if (playType == null) {
            playType = AudioCourse.PlayType.normal.getType();
        }
        if (playType > AudioCourse.PlayType.normal.ordinal()) {
            handleLiveDetail(courseId, detail);
        } else {
            AudioCoursePlay play = audioService.findPlayState(courseId);
            if (play != null) {
                play.setPlayState(AudioCoursePlay.PlayState.playing.ordinal());
                play.setPlayPage(pageNum);
                audioService.updateAudioCoursePlay(play);
            }
        }
    }

    /**
     * 直播指令
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/live")
    @ResponseBody
    public String live(LiveOrderDTO dto) {
        dto.setOrder(LiveOrderDTO.ORDER_LIVE);
        liveService.publish(dto);
        return success();
    }

    /**
     * 同步指令 在扫码投屏同步的时候用到
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sync")
    @ResponseBody
    public String sync(LiveOrderDTO dto) {
        dto.setOrder(LiveOrderDTO.ORDER_SYNC);
        dto.setOrderFrom("app");
        liveService.publish(dto);
        return success();
    }

    /**
     * 扫码投屏直播|录播
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/scan/callback")
    @ResponseBody
    public String handleScan(Integer courseId, HttpServletRequest request) {
        Principal principal = SecurityUtils.get();
        AudioCourse course = audioService.findAudioCourse(courseId);
        if (!principal.getId().equals(course.getCspUserId())) {
            return error(local("meeting.error.not_mine"));
        }

        boolean hasDuplicate = LiveOrderHandler.hasDuplicate(String.valueOf(courseId), request.getHeader(Constants.TOKEN));
        if (hasDuplicate) {
            Map<String, Object> result = new HashMap<>();
            String wsUrl = genWsUrl(request, courseId);
            wsUrl += "&liveType=" + LiveOrderDTO.LIVE_TYPE_PPT;
            result.put("wsUrl", wsUrl);
            result.put("courseId", courseId);
            result.put("playType", course.getPlayType() == null ? 0 : course.getPlayType());
            result.put("duplicate", "1");
            return success(result);
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("courseId", courseId);
            result.put("playType", course.getPlayType() == null ? 0 : course.getPlayType());
            result.put("duplicate", "0");
            result.put("title", course.getTitle());
            result.put("coverUrl", !CheckUtils.isEmpty(course.getDetails()) ? course.getDetails().get(0).getImgUrl() : "");

            sendScreenOrder(courseId);
            return success(result);
        }

    }

    /**
     * 发送扫码投屏指令
     * @param courseId
     */
    protected void sendScreenOrder(Integer courseId){

        LiveOrderDTO order = new LiveOrderDTO();
        order.setCourseId(String.valueOf(courseId));
        order.setLiveType(LiveOrderDTO.LIVE_TYPE_PPT);
        order.setOrder(LiveOrderDTO.ORDER_SCAN_SUCCESS);
        order.setPageNum(0);
        liveService.publish(order);
    }


    protected String courseInfo(Integer courseId, HttpServletRequest request) throws SystemException {
        Principal principal = SecurityUtils.get();
        AudioCourse audioCourse = audioService.selectByPrimaryKey(courseId);
        audioCourse.setDetails(audioService.findDetailsByCourseId(courseId));
        if (audioCourse == null) {
            throw new SystemException(local("source.not.exists"));
        }

        if (audioCourse.getDeleted()) {
            throw new SystemException(local("course.error.api.deleted"));
        }
        if (audioCourse.getLocked() != null && audioCourse.getLocked()) {
            throw new SystemException(local("course.error.api.locked"));
        }

        audioService.handleHttpUrl(fileBase, audioCourse);
        //判断用户是否有权限使用此课件
        if (!principal.getId().equals(audioCourse.getCspUserId())) {
            throw new SystemException(local("course.error.author"));
        }

        String wsUrl = genWsUrl(request, courseId);
        wsUrl = UrlConverter.newInstance(wsUrl).put("liveType", LiveOrderDTO.LIVE_TYPE_PPT).convert();

        CspCourseInfoDTO dto = new CspCourseInfoDTO(audioCourse, wsUrl);

        if (audioCourse.getPlayType() == null) {
            audioCourse.setPlayType(0);
        }
        if (audioCourse.getPlayType().intValue() > AudioCourse.PlayType.normal.ordinal()) {
            //查询出直播信息
            Live live = liveService.findByCourseId(courseId);

            if (live != null) {
                //已经结束 抛出异常 不再判断开始时间
                if (live.getLiveState().intValue() == AudioCoursePlay.PlayState.deleted.ordinal()) {
                    return error(local("share.live.over"));
                }
            }
            dto.setLive(live);
        } else {//录播查询录播的进度信息
            AudioCoursePlay play = audioService.findPlayState(courseId);
            if (play != null) {
                play.setPlayState(AudioCoursePlay.PlayState.playing.ordinal());
                audioService.updateAudioCoursePlay(play);
            }
            dto.setRecord(play);
            //查询出讲本的背景音乐和皮肤
            AudioCourseTheme theme = courseThemeService.findByCourseId(courseId);
            if (theme != null) {
                AudioCourseTheme.handleUrl(theme, fileBase);
                dto.setTheme(theme);
            }
        }

        //发送扫码投屏指令
        sendScreenOrder(courseId);

        return success(dto);
    }


    @RequestMapping(value = "/video/next")
    @ResponseBody
    public String videoNext(Integer courseId, Integer detailId) {
        AudioCourseDetail detail = audioService.findDetail(detailId);
        handleLiveDetail(courseId, detail);
        return success();
    }

    @RequestMapping(value = "/join")
    @ResponseBody
    public String join(Integer courseId, HttpServletRequest request) {
        if (courseId == null || courseId == 0) {
            return error(local("error.param"));
        }
        try {
            return courseInfo(courseId, request);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
    }

    /**
     * ZeGo 直播流被创建的时候的回调
     *
     * @param callback
     * @return
     */
    @RequestMapping(value = "/live/create")
    @ResponseBody
    public String onCreate(ZeGoCallBack callback) {
        try {
            callback.signature();

            Integer channelId = Integer.valueOf(callback.getChannel_id());
            Live live = liveService.findByCourseId(channelId);
            if (live != null) {
                live.setHdlUrl(callback.getHdl_url()[0]);
                live.setRtmpUrl(callback.getRtmp_url()[0]);
                live.setHlsUrl(callback.getHls_url()[0]);
                live.setPicUrl(callback.getPic_url()[0]);
                live.setLiveState(Live.LiveState.usable.ordinal());
                liveService.updateByPrimaryKey(live);
            }

            return ZEGO_SUCCESS_CODE;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * ZeGo 直播流被关闭的时候的回调
     *
     * @param callback
     * @return
     */
    @RequestMapping(value = "/live/close")
    @ResponseBody
    public String onClose(ZeGoCallBack callback) {
        try {
            callback.signature();
            Integer channelId = Integer.valueOf(callback.getChannel_id());
            //liveService.publish(LiveOrderDTO.buildVideoLiveCloseOrder(String.valueOf()));
            Live live = liveService.findByCourseId(channelId);
            if (live != null) {
                live.setLiveState(Live.LiveState.closed.ordinal());
                live.setCloseType(callback.getType());
                liveService.updateByPrimaryKey(live);
            }
            return ZEGO_SUCCESS_CODE;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * ZeGo 直播流重播地址获取时回调
     *
     * @param callback
     * @return
     */
    @RequestMapping(value = "/live/replay")
    @ResponseBody
    public String onReplay(ZeGoCallBack callback) {
        try {
            callback.signature();
            if (callback.getReplay_url().endsWith(".m3u8")) {//对于m3u8格式不做处理 只处理mp4格式
                return success();
            }

            Integer channelId = Integer.valueOf(callback.getChannel_id());
            Live live = liveService.findByCourseId(channelId);
            if (live != null) {

                String replayUrl = callback.getReplay_url();

                //System.out.println("video live replay url = " + replayUrl);

                String videoName = FileUtils.getFileName(replayUrl);

                String finalReplayPath = FilePath.COURSE.path + "/" + channelId + "/replay/" + videoName;

                String videoDirPath = fileUploadBase + FilePath.COURSE.path + "/" + channelId + "/replay/";

                File dir = new File(videoDirPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileUtils.downloadNetWorkFile(replayUrl, videoDirPath, videoName);

                //检测是否有之前的直播视频
                if (CheckUtils.isNotEmpty(live.getReplayUrl())) {
                    String oldVideoName = FileUtils.getFileName(live.getReplayUrl());
                    File oldFile = new File(videoDirPath + oldVideoName);

                    if (!videoName.equals(oldVideoName) && oldFile.exists()) {//文件名不相同 需要将两端视频整合为一段
                        FFMpegUtils.concatMp4(videoDirPath + oldVideoName, true, videoDirPath + oldVideoName, videoDirPath + videoName);
                    } else {
                        live.setReplayUrl(fileBase + finalReplayPath);
                    }
                } else {
                    live.setReplayUrl(fileBase + finalReplayPath);
                }

                live.setExpireDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(expireDays)));
                live.setPlayCount(callback.getPlayer_count());
                live.setOnlineCount(callback.getOnline_nums());
                liveService.updateByPrimaryKey(live);
            }
            return ZEGO_SUCCESS_CODE;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(Pageable pageable) {
        Principal principal = SecurityUtils.get();
        String cspUserId = principal.getId();

        pageable.put("cspUserId", cspUserId);
        MyPage<CourseDeliveryDTO> page = audioService.findCspMeetingListForApp(pageable);

        if (!CheckUtils.isEmpty(page.getDataList())) {
            CourseDeliveryDTO.splitCoverUrl(page.getDataList(), fileBase);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", page.getDataList());
        CspPackage cspPackage = principal.getCspPackage();
        result.put("hideCount", cspPackage.getHiddenMeetCount() == null ? 0 : cspPackage.getHiddenMeetCount());
        result.put("packageId", cspPackage.getId());

        return success(result);
    }


    /**
     * 删除会议
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(Integer id) {
        Principal principal = SecurityUtils.get();
        if (id == null || id == 0) {
            return error(local("error.param"));
        }

        // 检查该会议是否是当前登录者的会议
        boolean isMine = audioService.checkCourseIsMine(principal.getId(), id);
        if (!isMine) {
            return error(local("course.error.author"));
        }
        try {
            audioService.deleteAble(id);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        //逻辑删除
        audioService.deleteCspCourse(id);
        AudioCourse course = audioService.selectByPrimaryKey(id);
        //当前删除的会议如果是锁定状态则不处理 否则需要解锁用户最早的一个锁定的会议
        if (course.getLocked() != null && course.getLocked() != true && course.getGuide() != true
                && course.getSourceType()!= AudioCourse.SourceType.QuickMeet.ordinal()) {
            //判断是否有锁定的会议
            if (principal.getPackageId().intValue() != CspPackage.TypeId.PROFESSIONAL.getId()) {
                audioService.doUnlockEarliestCourse(principal.getId());
            }
        }
        updatePackagePrincipal(principal.getId());
        return success();

    }

    /**
     * 退出录播
     *
     * @param courseId
     * @param pageNum
     * @param over
     * @return
     */
    @RequestMapping(value = "/record/exit")
    @ResponseBody
    public String exit(Integer courseId, Integer pageNum, Integer over) {
        if (over == null) {
            over = 0;
        }

        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        if (course != null) {
            if (course.getPlayType() == null) {
                course.setPlayType(AudioCourse.PlayType.normal.getType());
            }
            LiveOrderDTO overOrder = new LiveOrderDTO();
            overOrder.setCourseId(String.valueOf(courseId));
            overOrder.setOrder(LiveOrderDTO.ORDER_LIVE_OVER);
            if (course.getPlayType().intValue() > AudioCourse.PlayType.normal.getType()) {
                Live live = liveService.findByCourseId(courseId);

                if (live != null) {
                    if (live.getLiveState() != null && live.getLiveState().intValue() > AudioCoursePlay.PlayState.init.ordinal()) {
                        if(over == 1){
                            live.setLiveState(AudioCoursePlay.PlayState.over.ordinal());
                            live.setEndTime(new Date());
                            live.setExpireDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(CspConstants.LIVE_OVER_DELAY)));
                        } else {
                            if(live.getLiveState().intValue() == AudioCoursePlay.PlayState.playing.ordinal()){
                                live.setLiveState(AudioCoursePlay.PlayState.pause.ordinal());
                            }
                        }
                        liveService.updateByPrimaryKey(live);
                    }
                }
                //删除缓存中的同步指令
                redisCacheUtils.delete(LiveService.SYNC_CACHE_PREFIX + courseId);
            } else {
                AudioCoursePlay play = audioService.findPlayState(courseId);
                if (play != null) {
                    play.setPlayPage(pageNum);
                    play.setPlayState(over == 1 ? AudioCoursePlay.PlayState.over.ordinal() : play.getPlayState());
                    audioService.updateAudioCoursePlay(play);
                }
                //发送结束指定到投屏端
                if (over == 1) {
                    liveService.publish(overOrder);
                }
            }
        }

        return success();
    }

    /**
     * 根据课件ID获取推流地址
     * 如果已经存在则返回原有地址
     * 不存在则生成推流地址
     *
     * @param courseId
     * @return
     */
    protected String getPushUrl(Integer courseId) {
        Live live = liveService.findByCourseId(courseId);
        String pushUrl = null;
        if (live != null) {

            if (live.getVideoLive() != null && live.getVideoLive()) {//如果是视频直播
                if (CheckUtils.isEmpty(live.getPushUrl())) {
                    pushUrl = TXLiveUtils.genPushUrl(String.valueOf(courseId), System.currentTimeMillis() + TimeUnit.HOURS.toMillis(MEET_AFTER_START_EXPIRE_HOURS));
                    live.setPushUrl(pushUrl);
                    live.setRtmpUrl(TXLiveUtils.genStreamPullUrl(String.valueOf(courseId), TXLiveUtils.StreamPullType.rtmp));
                    live.setHlsUrl(TXLiveUtils.genStreamPullUrl(String.valueOf(courseId), TXLiveUtils.StreamPullType.hls));
                    live.setHdlUrl(TXLiveUtils.genStreamPullUrl(String.valueOf(courseId), TXLiveUtils.StreamPullType.flv));

                    liveService.updateByPrimaryKey(live);
                } else {
                    pushUrl = live.getPushUrl();
                }
            }
        }
        return pushUrl;
    }

    /**
     * 检测是否有重复登录
     *
     * @param courseId
     * @param request
     * @return
     */
    @RequestMapping(value = "/join/check")
    @ResponseBody
    public String joinCheck(Integer courseId, HttpServletRequest request, String liveType) {

        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        if (course == null) {
            return error(local("source.not.exists"));
        }

        if (course.getDeleted() != null && course.getDeleted()) {
            return error(local("course.error.api.deleted"));
        }

        if (course.getLocked() != null && course.getLocked()) {
            return error(local("course.error.api.locked"));
        }

        boolean hasDuplicate = LiveOrderHandler.hasDuplicate(String.valueOf(courseId), request.getHeader(Constants.TOKEN), liveType);
        Map<String, Object> result = new HashMap<>();

        if (liveType == null) {
            liveType = LiveOrderDTO.LIVE_TYPE_PPT;
        }

        if (liveType.equals(LiveOrderDTO.LIVE_TYPE_VIDEO)) {
            //返回视频推流地址
            String pushUrl = getPushUrl(courseId);
            result.put("pushUrl", pushUrl);
        }

        String wsUrl = genWsUrl(request, courseId);
        wsUrl += "&liveType=" + liveType;
        result.put("wsUrl", wsUrl);
        if (hasDuplicate) {
            result.put("duplicate", "1");
        } else {
            result.put("duplicate", "0");
        }

        //加入服务器当前时间
        result.put("serverTime", new Date());

        return success(result);
    }

    /**
     * 直播开始
     *
     * @param courseId
     * @param imgUrl
     * @param videoUrl
     * @param firstClk
     * @return
     */
    @RequestMapping(value = "/live/start")
    @ResponseBody
    public String startLive(Integer courseId, String imgUrl, String videoUrl, Integer firstClk, Integer pageNum) {
        if (firstClk == null) {
            firstClk = 0;
        }

        //首先判断是否存在缓存 如果存在缓存则不发送同步指令 否则发送同步指令
        if (firstClk == 1) {

            //发送直播开始指令 只用于投屏同步
            LiveOrderDTO liveStartOrder = new LiveOrderDTO();
            liveStartOrder.setOrder(LiveOrderDTO.ORDER_LIVE_START);
            liveStartOrder.setCourseId(String.valueOf(courseId));
            liveService.publish(liveStartOrder);

        }
        Live live = liveService.findByCourseId(courseId);
        //判断直播是否已经开始过 如果未开始过 设置开始时间和过期时间
        updateLiveState(live);

        sendSyncOrder(courseId, imgUrl, videoUrl, pageNum);

        //同步yaya医师的会议状态
        meetService.doStartMeet(courseId);

        return success();
    }


    @RequestMapping(value = "/live/over")
    @ResponseBody
    public String liveOver(Integer courseId) {
        //删除缓存中的同步指令
        redisCacheUtils.delete(LiveService.SYNC_CACHE_PREFIX + courseId);

        //这里不直接修改直播状态了 而是将直播的状态改为待结束状态 10分钟之后真正结束
        Live live = liveService.findByCourseId(courseId);
        Map<String, Object> result = new HashMap<>();
        if (live != null) {
            live.setLiveState(AudioCoursePlay.PlayState.over.ordinal());
            live.setExpireDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(CspConstants.LIVE_OVER_DELAY)));
            liveService.updateByPrimaryKey(live);
            result.put("expireDate", live.getExpireDate());
        }
        //同步yaya医师会议状态
        meetService.doEndMeet(courseId);
        result.put("serverTime", new Date());
        return success(result);
    }

    protected void sendSyncOrder(Integer courseId, String imgUrl, String videoUrl, Integer pageNum) {
        LiveOrderDTO order = new LiveOrderDTO();
        order.setCourseId(String.valueOf(courseId));
        order.setOrder(LiveOrderDTO.ORDER_SYNC);
        order.setImgUrl(imgUrl);
        order.setPageNum(pageNum == null ? 0 : pageNum);
        order.setVideoUrl(videoUrl);
        liveService.publish(order);
    }


    /**
     * 开始视频直播 保存推拉流信息
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/live/video/start")
    @ResponseBody
    public String videoLiveStart(Integer courseId) {
        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        if (course.getDeleted() != null && course.getDeleted() == true) {
            return error("source.has.deleted");
        }
        //增加会议是否被锁定判断
        if (course.getLocked() != null && course.getLocked() == true) {
            return error("course.error.locked");
        }

        Live live = liveService.findByCourseId(courseId);
        Map<String, Object> result = new HashMap<>();
        String pushUrl = null;

        if (live != null) {
            if (live.getLiveState().intValue() >= AudioCoursePlay.PlayState.over.ordinal()) {
                return error(local("share.live.over"));
            }
            updateLiveState(live);

            //发送直播开始指令 只用于投屏同步
            LiveOrderDTO liveStartOrder = new LiveOrderDTO();
            liveStartOrder.setOrder(LiveOrderDTO.ORDER_LIVE_START);
            liveStartOrder.setCourseId(String.valueOf(courseId));
            liveService.publish(liveStartOrder);

            pushUrl = getPushUrl(courseId);
            result.put("pushUrl", pushUrl);
        }

        meetService.doStartMeet(courseId);

        return success(result);
    }

    protected void updateLiveState(Live live){
        //改变直播状态
        if (live.getLiveState() == null || live.getLiveState().intValue() == AudioCoursePlay.PlayState.init.ordinal()){
            live.setStartTime(new Date());
            live.setExpireDate(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(MEET_AFTER_START_EXPIRE_HOURS)));
            live.setLiveState(AudioCoursePlay.PlayState.playing.ordinal());
            liveService.updateByPrimaryKey(live);
        }
    }


    @RequestMapping(value = "/report")
    @ResponseBody
    public String report(Integer type, Integer courseId, String shareUrl, HttpServletRequest request, HttpServletResponse response) {
        String reportCookieKey = "report_" + courseId;
        if (CheckUtils.isNotEmpty(CookieUtils.getCookieValue(request, reportCookieKey))) {
            return error(local("page.meeting.report.repeat"));
        }

        if (CheckUtils.isEmpty(shareUrl)) {
            shareUrl = audioService.getMeetShareUrl(appCspBase, LocalUtils.Local.zh_CN.name(), courseId, false);
        }

        EmailTemplate template = emailTempService.selectByPrimaryKey(EmailTempService.REPORT_TEMPLATE_ID);

        if (template != null) {
            MailBean mailBean = new MailBean();
            mailBean.setSubject(template.getSubject());
            mailBean.setToEmails(new String[]{cspEmailService.getEmailUserName()});
            //mailBean.setToEmails(new String[]{"584479243@qq.com"});
            mailBean.setFrom(cspEmailService.getEmailUserName());
            mailBean.setFromName(template.getSender());

            String content = template.getContent();
            content = handleReportContent(content, courseId, shareUrl, type);
            mailBean.setContext(content);

            cspEmailService.send(mailBean);
            //设置cookie 判断用户已经举报过该讲本
            CookieUtils.setCookie(response, reportCookieKey, "true");
        }

        return success();
    }


    protected String handleReportContent(String content, Integer courseId, String shareUrl, int reportType) {
        if (content != null) {
            AudioCourse course = audioService.selectByPrimaryKey(courseId);
            if (course != null) {
                CspUserInfo cspUserInfo = cspUserService.selectByPrimaryKey(course.getCspUserId() == null ? "" : course.getCspUserId());

                content = content.replaceAll("@title", course.getTitle());
                content = content.replaceAll("@link", shareUrl);
                content = content.replaceAll("@report_type", ReportType.values()[reportType].label);
                content = content.replaceAll("@user_name", cspUserInfo == null ? "未知" : cspUserInfo.getNickName());
                content = content.replaceAll("@user_id", cspUserInfo == null ? "未知" : cspUserInfo.getId());
                content = content.replaceAll("@Date", new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));
            }
        }

        return content;
    }

    /**
     * 游客获取新手指导课件
     *
     * @return
     */
    @RequestMapping(value = "/tourist/list")
    @ResponseBody
    public String getGuideCourse(Integer courseId) {
        Pageable pageable = new Pageable();
        pageable.put("id", courseId == null ? Constants.NUMBER_ONE : courseId);
        MyPage<CourseDeliveryDTO> page = audioService.findCspMeetingListForApp(pageable);
        if (!CheckUtils.isEmpty(page.getDataList())) {
            CourseDeliveryDTO.splitCoverUrl(page.getDataList(), fileBase);
        }
        return success(page.getDataList());
    }

    /**
     * 游客会议阅览
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/tourist/view")
    @ResponseBody
    public String touristView(Integer courseId) {
        return view(courseId);
    }

    /**
     * 设置课件密码
     *
     * @param course
     * @return
     */
    @RequestMapping(value = "/set/password")
    @ResponseBody
    public String meetPassword(AudioCourse course, Integer type) {
        if (course.getId() == null || type == null) {
            return error(local("user.param.empty"));
        }
        //判断会议是否存在
        AudioCourse update = audioService.selectByPrimaryKey(course.getId());
        if (update == null) {
            return error(local("source.not.exists"));
        }
        //密码大于4位
        if (course.getPassword() != null && course.getPassword().length() > 4) {
            return error(local("page.meeting.tips.watch.password.holder"));
        }
        //判断用户操作的是否是自己的会议
        Principal principal = SecurityUtils.get();
        if (!principal.getId().equalsIgnoreCase(update.getCspUserId())) {
            return error(local("meet.notmine"));
        }
        audioService.doModifyPassword(update, type == Constants.NUMBER_ONE ? course.getPassword() : null);
        return success();
    }

    /**
     * 获取课件密码
     *
     * @param course
     * @return
     */
    @RequestMapping(value = "/get/password")
    @ResponseBody
    public String getPassword(AudioCourse course) {
        if (course.getId() == null) {
            return error(local("user.param.empty"));
        }
        //判断会议是否存在
        AudioCourse update = audioService.selectByPrimaryKey(course.getId());
        if (update == null) {
            return error(local("source.not.exists"));
        }
        Principal principal = SecurityUtils.get();
        if (!principal.getId().equalsIgnoreCase(update.getCspUserId())) {
            return error(local("meet.notmine"));
        }
        String password = update.getPassword() == null ? "" : update.getPassword();
        Map<String, Object> map = new HashMap<>();
        map.put("password", password);
        return success(map);
    }

    /**
     * 获取星评状态和星评二维码
     *
     * @param courseId
     * @return
     */
    @RequestMapping("/star/code")
    @ResponseBody
    public String getStarAndCode(Integer courseId) {
        if (courseId == null) {
            return error(local("courseId.empty"));
        }
        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        if (course == null) {
            return error(local("source.not.exists"));
        }
        StarRateResultDTO dto = new StarRateResultDTO();

        dto.setStarStatus(course.getStarRateFlag());
        //开启了星评，生成二维码
        if (course.getStarRateFlag() != null && course.getStarRateFlag()) {
            String local = LocalUtils.getLocalStr();
            boolean abroad = LocalUtils.isAbroad();
            String shareUrl = audioService.getMeetShareUrl(appCspBase, local, courseId, abroad);
            //判断二维码是否存在 不存在则重新生成
            String qrCodePath = FilePath.QRCODE.path + "/share/" + courseId + "." + FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix;
            boolean qrCodeExists = FileUtils.exists(fileUploadBase + qrCodePath);
            if (!qrCodeExists) {
                QRCodeUtils.createQRCode(shareUrl, fileUploadBase + qrCodePath);
            }
            dto.setStartCodeUrl(fileBase + qrCodePath);
        }
        openStarRate(course, dto);
        return success(dto);
    }


    /**
     * 开启星评
     * @param course
     */
    public void openStarRate(AudioCourse course, StarRateResultDTO dto) {
        if (course != null) {
            if (course.getPlayType().intValue() == AudioCourse.PlayType.normal.getType()) {
                AudioCoursePlay play = audioService.findPlayState(course.getId());
                play.setPlayState(AudioCoursePlay.PlayState.rating.ordinal());
                audioService.updateAudioCoursePlay(play);
            } else {
                //修改直播状态为星评中状态
                Live live = liveService.findByCourseId(course.getId());
                if (live.getLiveState().intValue() < AudioCoursePlay.PlayState.rating.ordinal()) {
                    if (live.getLiveState() == AudioCoursePlay.PlayState.init.ordinal()) {
                        live.setStartTime(new Date());
                        live.setExpireDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(expireDays)));
                    }
                    live.setLiveState(AudioCoursePlay.PlayState.rating.ordinal());
                } else if (live.getLiveState().intValue() == AudioCoursePlay.PlayState.over.ordinal()){
                    dto.setExpireDate(live.getExpireDate());
                }
                liveService.updateByPrimaryKey(live);
            }
        }
        if (course.getStarRateFlag() != null && course.getStarRateFlag()) {
            //发送开启星评指令
            LiveOrderDTO order = new LiveOrderDTO();
            order.setCourseId(String.valueOf(course.getId()));
            order.setOrder(LiveOrderDTO.ORDER_STAR_RATE_START);
            liveService.publish(order);
        }
    }


    /**
     * 获取小程序二维码
     *
     * @param id 课程id
     * @return
     * @throws SystemException
     */
    @RequestMapping("/mini/qrcode")
    @ResponseBody
    public String getMiniQRCode(Integer id, String page) throws SystemException, IOException {
        if (id == null) {
            return error(local("courseId.empty"));
        }
        //获取access_token
        String accessToken = redisCacheUtils.getCacheObject(CSP_MINI_ACCESS_TOKEN_KEY);
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = wxTokenService.getMiniAccessToken(appId, secret);
            //有效时间 7200s
            redisCacheUtils.setCacheObject(CSP_MINI_ACCESS_TOKEN_KEY, accessToken, WeixinConfig.TOKEN_EXPIRE_TIME);
        }
        String codeUrl = audioService.getMiniQRCode(id, page, accessToken);
        return success(codeUrl);
    }


    /**
     * 小程序循环接收图片，创建课件
     * @param file
     * @param courseId 课件的id
     * @param sort 图片的排序号,第一张图sort=0
     * @param type 课件类型，为空或者0表示录播，1表示ppt直播，2表示视频直播
     * @return
     */
    @RequestMapping("/upload/picture")
    @ResponseBody
    public String savePicture(@RequestParam(required = false) MultipartFile file,Integer courseId,Integer sort,Integer type) throws SystemException {
        if(file == null){
            return error(local("upload.error.null"));
        }
        if(sort == null){
            return error(local("user.param.empty"));
        }

        if(type == null ){
            type = AudioCourse.PlayType.normal.ordinal();
        }else if(type < AudioCourse.PlayType.normal.ordinal() || type > AudioCourse.PlayType.live_video.ordinal() ){
            return error(local("meet.store.param.error"));
        }

        AudioCourse course = new AudioCourse();
        course.setCspUserId(SecurityUtils.get().getId());
        course.setId(courseId);


        //创建课件或者添加课件图片
        courseId = audioService.createAudioOrAddDetail(file, course,sort,type);
        Map<String,Integer> map = new HashMap<>();
        map.put("id",courseId);
        return success(map);
    }


    /**
     * 生成或更新课件标题, 课件主题，背景音乐
     * @param courseId
     * @param title
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/mini/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateAudio(Integer courseId,String title, Integer imgId, Integer musicId) throws SystemException {
        if(courseId == null){
            return error(local("courseId.empty"));
        }

        boolean isMine = audioService.checkCourseIsMine(SecurityUtils.get().getId(),courseId);
        if (!isMine) {
            return error(local("course.error.author"));
        }

        if("".equals(title.trim())){
            return error(local("meeting.title.not.none"));
        }

        AudioCourse course = new AudioCourse();
        course.setId(courseId);
        course.setTitle(title);
        course.setPublished(true);
        //生成或更新课件标题, 课件主题，背景音乐
        audioService.createOrUpdateCourseAndTheme(course,imgId,musicId);
        return success();
    }




    /**
     * 更新主题
     * @param courseId
     * @param imgId
     * @return
     */
    @RequestMapping("/update/img")
    @ResponseBody
    public String updateImg(Integer courseId, Integer imgId){
        if(courseId == null){
            return error(local("courseId.empty"));
        }
        boolean isMine = audioService.checkCourseIsMine(SecurityUtils.get().getId(),courseId);
        if (!isMine) {
            return error(local("course.error.author"));
        }
        AudioCourseTheme theme = new AudioCourseTheme();
        theme.setCourseId(courseId);
        theme = courseThemeService.selectOne(theme);
        if(theme != null){
            //删除主题
            if(imgId == NUMBER_ZERO){
                theme.setImageId(null);
                courseThemeService.updateByPrimaryKey(theme);
            }else{
                theme.setImageId(imgId);
                courseThemeService.updateByPrimaryKeySelective(theme);
            }
        }

        return success();
    }


    /**
     * 更新背景音乐
     * @param courseId
     * @param musicId
     * @return
     */
    @RequestMapping("/update/music")
    @ResponseBody
    public String updateMusic(Integer courseId, Integer musicId){
        if(courseId == null){
            return error(local("courseId.empty"));
        }
        boolean isMine = audioService.checkCourseIsMine(SecurityUtils.get().getId(),courseId);
        if (!isMine) {
            return error(local("course.error.author"));
        }
        AudioCourseTheme theme = new AudioCourseTheme();
        theme.setCourseId(courseId);
        theme = courseThemeService.selectOne(theme);
        if(theme != null){
            //删除主题
            if(musicId == NUMBER_ZERO){
                theme.setMusicId(null);
                courseThemeService.updateByPrimaryKey(theme);
            }else{
                theme.setMusicId(musicId);
                courseThemeService.updateByPrimaryKeySelective(theme);
            }
        }

        return success();
    }


//    /**
//     * 创建课件或者更新课件
//     * @param courseId
//     * @param files
//     * @param title
//     * @param imgId
//     * @param musicId
//     * @return
//     * @throws SystemException
//     */
//    @RequestMapping("/create/update")
//    @ResponseBody
//    public String createOrUpdateAudio(Integer courseId, @RequestParam(required = false) MultipartFile[] files , String title, Integer imgId,Integer musicId) throws SystemException {
//        String userId = SecurityUtils.get().getId();
//        AudioCourse course = new AudioCourse();
//        course.setId(courseId);
//        course.setTitle(title);
//        course.setCspUserId(userId);
//        course.setSourceType(AudioCourse.SourceType.QuickMeet.ordinal());
//
//        AudioCourseTheme theme = new AudioCourseTheme();
//        theme.setMusicId(musicId);
//        theme.setImageId(imgId);
//
//        //新建课件
//        if(courseId == null){
//            if(files == null){
//                return error(local("upload.error.null"));
//            }
//            if(StringUtils.isEmpty(title)){
//                return error(local("meeting.title.not.none"));
//            }
//            courseId = audioService.createAudioAndDetail(files, course, theme);
//            Map<String,Integer> map = new HashMap<>();
//            map.put("courseId",courseId);
//            return success(map);
//
//        }else{  //修改课件
//            theme.setCourseId(courseId);
//            audioService.updateMiniCourse(course,theme);
//            return success();
//        }
//    }

    /**
     * 小程序活动贺卡模板列表
     *
     * @return
     */
    @RequestMapping("/mini/template/list")
    @ResponseBody
    public String templateList() {
        Principal principal = SecurityUtils.get();
        List<AudioCourseDTO> templateList = audioService.findMiniTemplate();
        AudioCourseDTO.HandelCoverUrl(templateList, null, fileBase);
        return success(templateList);
    }


    /**
     * 通过小程序二维码 获取贺卡模板
     *
     * @param id 模板id即课程id
     * @return
     */
    @RequestMapping("/mini/template")
    @ResponseBody
    public String getTemplate(Integer id) {
        Principal principal = SecurityUtils.get();
        if (id == null) {
            // 随机返回贺卡模板
            id = 0;
        }
        AudioCourseDTO courseDTO = audioService.findMiniTemplateByIdOrRand(id);
        HandelCoverUrl(null, courseDTO, fileBase);
        return success(courseDTO);
    }

    /**
     * 选择贺卡模板后生成讲本（即课件）
     *
     * @param id 贺卡模板id即courseId
     * @return
     */
    @RequestMapping("/mini/create/course")
    @ResponseBody
    public String createCourse(Integer id) {
        Principal principal = SecurityUtils.get();
        if (id == null) {
            return error("user.param.empty");
        }

        String cspUserId = principal.getId();
        // 查找选择的贺卡模板讲本内容 并复制创建当前用户的讲本
        Integer courseId = audioService.doCopyCourseTemplate(id, cspUserId);

        return view(courseId);
    }

    /**
     * 进入讲本编辑页面
     * @since csp1.2.0
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(Integer courseId){
        Map<String, Object> result = new HashMap<>();
        if (courseId != null && courseId != 0) {
            AudioCourse course = audioService.selectByPrimaryKey(courseId);
            String coverUrl = audioService.getCoverUrl(courseId);
            if (CheckUtils.isNotEmpty(coverUrl)){
                coverUrl = fileBase + coverUrl;
            }
            course.setCoverUrl(coverUrl);

            AudioCourseTheme theme = courseThemeService.findByCourseId(courseId);
            if(theme != null){
                Integer duration = theme.getDuration();
                theme.setTimeStr(CalendarUtils.secToTime(duration == null ? 0 : duration));
            }
            AudioCourseTheme.handleUrl(theme, fileBase);
            result.put("course", course);
            result.put("theme", theme);
        }
        //查询出推荐的皮肤
        List<BackgroundImage> imageList = courseThemeService.findImageList(0);
        BackgroundImage.HandelImgUrl(imageList,fileBase);
        result.put("imageList",imageList);
        return success(result);
    }

    /**
     * 查看更多皮肤
     * @since csp1.2.0
     * @return
     */
    @RequestMapping(value = "/theme/image/more")
    @ResponseBody
    public String moreImage(){
        Map<String, Object> result = new HashMap<>();
        List<BackgroundImage> imageList = courseThemeService.findImageList(1);
        BackgroundImage.HandelImgUrl(imageList,fileBase);
        result.put("list",imageList);
        return success(result);
    }

    /**
     * 查看更多背景音乐
     * @since csp1.2.0
     * @return
     */
    @RequestMapping(value = "/theme/music/more")
    @ResponseBody
    public String moreMusic(){
        Map<String, Object> result = new HashMap<>();
        List<BackgroundMusic> musicList = courseThemeService.findMusicList(1);
        BackgroundMusic.HandelMusicUrl(musicList,fileBase);
        BackgroundMusic.tranTimeStr(musicList);
        result.put("list",musicList);
        return success(result);
    }


    /**
     * 小程序接口
     * 获取主题和背景音乐
     * @param courseId
     * @param type type为null或者0时，获取主题，为1时获取背景音乐
     * @param showType {@link AudioCourseTheme.ShowType} showType=null或者0时，获取主题排序，为1时获取更多排序
     * @return
     */
    @RequestMapping("/mini/image/music")
    @ResponseBody
    public String getImageAndMusic(Integer type, Integer showType, Integer courseId){
        if(type == null){
            type = AudioCourseTheme.ImageMusic.IMAGE.ordinal();
        }

        if (showType == null) {
            showType = AudioCourseTheme.ShowType.RECOMList.ordinal();
        }
        Map<String,Object> map = new HashMap<>();

        if (courseId != null) {
            AudioCourse course = audioService.selectByPrimaryKey(courseId);
            AudioCourseTheme theme = courseThemeService.findByCourseId(courseId);
            map.put("course", course);
            map.put("theme", theme);
        }
        //获取主题
        if(type == AudioCourseTheme.ImageMusic.IMAGE.ordinal()){
            List<BackgroundImage> imageList = courseThemeService.findImageList(showType);
            BackgroundImage.HandelImgUrl(imageList,fileBase);
            map.put("imageList",imageList);
            return success(map);
        }else{
            //获取背景音乐
            List<BackgroundMusic> musicList = courseThemeService.findMusicList(showType);
            BackgroundMusic.HandelMusicUrl(musicList,fileBase);
            map.put("musicList",musicList);
            return success(map);
        }
    }


    /**
     * 用户评分
     * @param history
     * @param request
     * @return
     */
    @RequestMapping(value = "/share/rate")
    @ResponseBody
    public String doRate(CspStarRateHistory history, HttpServletRequest request, HttpServletResponse response){
        String ticket = CookieUtils.getCookieValue(request, COURSE_RATE_TICKET_KEY);
        if (CheckUtils.isEmpty(ticket)) {
            ticket = StringUtils.uniqueStr();
            CookieUtils.setCookie(response, COURSE_RATE_TICKET_KEY, ticket, (int)TimeUnit.DAYS.toSeconds(30));
        }
        history.setTicket(ticket);
        cspStarRateService.doScore(history);
        return success();
    }

    @RequestMapping(value = "/share/live/duration")
    @ResponseBody
    public String liveDuration(Integer courseId){
        Live live = liveService.findByCourseId(courseId);
        Map<String, Object> result = new HashMap<>();
        long duration = 0;
        if (live != null && live.getEndTime() != null) {
            duration = (live.getEndTime().getTime() - live.getStartTime().getTime()) / 1000;
        }
        result.put("duration", CalendarUtils.formatTimesDiff(duration));
        return success(result);
    }


    /**
     * 根据会议来源和会议类型筛选出会议列表，供小程序或app调用
     * @param playType  如果为null，默认为录播类型
     * @param sourceType  如果为null，默认来源为YaYa
     * @param pageable
     * @return
     */
    @RequestMapping("/mini/list")
    @ResponseBody
    public String miniMeetingList(Integer playType, Integer sourceType,Pageable pageable){
        if(playType == null){
            playType = AudioCourse.PlayType.normal.getType();
        }
        if(sourceType == null){
            sourceType = AudioCourse.SourceType.YaYa.ordinal();
        }
        String userId = SecurityUtils.get().getId();
        pageable.put("cspUserId",userId);
        pageable.put("playType",playType);
        pageable.put("sourceType",sourceType);
        MyPage<CourseDeliveryDTO> page = audioService.findMiniMeetingListByType(pageable);
        if (!CheckUtils.isEmpty(page.getDataList())) {
            CourseDeliveryDTO.splitCoverUrl(page.getDataList(), fileBase);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", page.getDataList());
        CspPackage cspPackage = SecurityUtils.get().getCspPackage();
        result.put("hideCount", cspPackage.getHiddenMeetCount() == null ? 0 : cspPackage.getHiddenMeetCount());
        result.put("packageId", cspPackage.getId());
        return success(result);
    }


    /**
     * 逻辑删除音频
     * @param detailId
     * @return
     */
    @RequestMapping("/delete/audio")
    @ResponseBody
    public String deleteAudio(Integer detailId){
        AudioCourseDetail detail = audioService.findDetail(detailId);
        if(detail != null){
            if(!audioService.checkCourseIsMine(SecurityUtils.get().getId(), detail.getCourseId())){
                return error(local("course.error.author"));
            }
            detail.setAudioUrl("");
            detail.setDuration(0);
            audioService.updateDetail(detail);
        }
        return success();
    }


    /**
     * 访问新手引导讲本
     * @since csp1.2.0
     * @return
     */
    @RequestMapping("/guide")
    @ResponseBody
    public String guide(){
        Integer guideId;
        if (LocalUtils.getLocalStr().equalsIgnoreCase(LocalUtils.Local.zh_CN.name())) {
            guideId = AudioService.GUIDE_SOURCE_ID;
        } else {
            guideId = AudioService.ABROAD_GUIDE_SOURCE_ID;
        }
        AudioCourse course = audioService.findAudioCourse(guideId);
        return success(course);
    }

}
