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
import cn.medcn.meet.dto.*;
import cn.medcn.meet.service.*;
import cn.medcn.user.model.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.csp.utils.TXLiveUtils;
import cn.medcn.meet.model.*;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.service.WXTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.medcn.common.Constants.*;
import static cn.medcn.csp.CspConstants.COURSE_RATE_TICKET_KEY;
import static cn.medcn.csp.CspConstants.MEET_AFTER_START_EXPIRE_HOURS;
import static cn.medcn.csp.CspConstants.ZEGO_SUCCESS_CODE;

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
            if (address != null && address.isAbroad() && !isAbroad || isAbroad && !address.isAbroad()) {
                model.addAttribute("error", abroadError);
                return localeView("/meeting/share_error");
            }

            Integer courseId = Integer.valueOf(id);
            AudioCourse course = audioService.selectByPrimaryKey(courseId);

            if (course == null) {
                model.addAttribute("error", linkError);
                return localeView("/meeting/share_error");
            }

            if (course.getDeleted() != null && course.getDeleted() == true) {
                model.addAttribute("error", local("source.has.deleted"));
                return localeView("/meeting/share_error");
            }
            if (course.getPlayType() == null) {
                course.setPlayType(0);
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
            if (CheckUtils.isNotEmpty(publisher.getAvatar()) && !publisher.getAvatar().toLowerCase().startsWith("http")){
                publisher.setAvatar(fileBase + publisher.getAvatar());
            }
            model.addAttribute("publisher", publisher);

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

                //TODO 缺少星评页面
                Live live = liveService.findByCourseId(courseId);
                if (live.getLiveState().intValue() == AudioCoursePlay.PlayState.over.ordinal()) {
                    model.addAttribute("totalLiveTime", CalendarUtils.formatTimesDiff(live.getStartTime(), live.getEndTime(), 0));
                }
                model.addAttribute("live", live);
            } else {
                course.setDetails(audioService.findDetailsByCourseId(courseId));
                AudioCoursePlay play = audioService.findPlayState(courseId);
                model.addAttribute("play", play);
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

        return handleUploadResult(record, relativePath, saveFileName);
    }


    protected String handleUploadResult(RecordUploadDTO record, String relativePath, String saveFileName) {
        AudioCourseDetail detail = audioService.findDetail(record.getDetailId());

        detail.setAudioUrl(relativePath + saveFileName + "." + FileTypeSuffix.AUDIO_SUFFIX_MP3.suffix);
        detail.setDuration(FFMpegUtils.duration(fileUploadBase + detail.getAudioUrl()));
        if (record.getPlayType() == AudioCourse.PlayType.normal.getType()) {
            audioService.updateDetail(detail);
        }

        handleLiveOrRecord(record.getCourseId(), record.getPlayType(), record.getPageNum(), detail);

        Map<String, String> result = new HashMap<>();
        result.put("audioUrl", fileBase + relativePath + saveFileName + "." + FileTypeSuffix.AUDIO_SUFFIX_MP3.suffix);
        return success(result);
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
            saveFileName = mergeUploadAudio(filePathQueue, relativePath);
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
    protected String mergeUploadAudio(List<String> filePathQueue, String relativePath) throws SystemException {
        String saveFileName = StringUtils.nowStr() + MINI_PROGRAM_UPLOAD_AUDIO_FORMAT;

        if (CheckUtils.isEmpty(filePathQueue)) {
            throw new SystemException(local("upload.error"));
        }
        //只有单个文件的处理 不需要合并 直接返回第一个音频名称
        if (filePathQueue.size() == 1) {
            String firstAudioPath = filePathQueue.get(0);
            saveFileName = FileUtils.getFileName(firstAudioPath);
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
            liveService.updateByPrimaryKeySelective(live);
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
            result.put("duplicate", "1");
            return success(result);
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("courseId", courseId);
            result.put("playType", course.getPlayType() == null ? 0 : course.getPlayType());
            result.put("duplicate", "0");
            result.put("title", course.getTitle());
            result.put("coverUrl", !CheckUtils.isEmpty(course.getDetails()) ? course.getDetails().get(0).getImgUrl() : "");

            if (course.getPlayType() != null && course.getPlayType() > AudioCourse.PlayType.normal.getType()) {
                Live live = liveService.findByCourseId(courseId);
                if (live != null) {
                    if (live.getLiveState().intValue() == AudioCoursePlay.PlayState.over.ordinal()) {//直播已经结束
                        return error(local("share.live.over"));
                    }
                    result.put("startTime", live.getStartTime());
                }
            }

            //发送ws同步指令
            LiveOrderDTO order = new LiveOrderDTO();
            order.setCourseId(String.valueOf(courseId));
            order.setLiveType(LiveOrderDTO.LIVE_TYPE_PPT);
            order.setOrder(LiveOrderDTO.ORDER_SCAN_SUCCESS);
            order.setPageNum(0);
            liveService.publish(order);
            return success(result);
        }

    }


    protected String courseInfo(Integer courseId, HttpServletRequest request) throws SystemException {
        Principal principal = SecurityUtils.get();
        AudioCourse audioCourse = audioService.findAudioCourse(courseId);
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
                if (live.getLiveState().intValue() == AudioCoursePlay.PlayState.over.ordinal()) {
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
        }

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

        if (!audioService.editAble(id)) {
            return error(courseNonDeleteAble());
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
                        live.setLiveState(over == 0 ? AudioCoursePlay.PlayState.pause.ordinal() : AudioCoursePlay.PlayState.over.ordinal());
                        if (over == 1) {
                            live.setEndTime(new Date());
                            liveService.publish(overOrder);
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

        return success();
    }


    @RequestMapping(value = "/live/over")
    @ResponseBody
    public String liveOver(Integer courseId) {
        //删除缓存中的同步指令
        redisCacheUtils.delete(LiveService.SYNC_CACHE_PREFIX + courseId);

        Live live = liveService.findByCourseId(courseId);
        if (live != null) {
            liveService.doModifyLiveState(live);
            LiveOrderDTO order = new LiveOrderDTO();
            order.setOrder(LiveOrderDTO.ORDER_LIVE_OVER);
            liveService.publish(order);
        }

        return success();
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
            if (live.getLiveState().intValue() == AudioCoursePlay.PlayState.over.ordinal()) {
                return error(local("share.live.over"));
            }
            updateLiveState(live);

            pushUrl = getPushUrl(courseId);
            result.put("pushUrl", pushUrl);
        }

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
    public String report(Integer type, Integer courseId, String shareUrl) {
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
    public String getGuideCourse(Integer sourceId) {
        Pageable pageable = new Pageable();
        pageable.put("id", sourceId == null ? Constants.NUMBER_ONE : sourceId);
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
        Integer result = 0;
        if (type == Constants.NUMBER_TWO) {
            AudioCourse update = audioService.selectByPrimaryKey(course.getId());
            update.setPassword(null);
            result = audioService.updateByPrimaryKey(update);
        } else {
            if (StringUtils.isEmpty(course.getPassword())) {
                return error(local("user.param.empty"));
            }
            String regEx = "[a-zA-Z0-9]{4,8}";
            Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(course.getPassword());
            boolean rs = matcher.matches();
            if (!rs) return error(local("user.subscribe.paramError"));
            result = audioService.updateByPrimaryKeySelective(course);
        }
        if (result > 0) {
            return success();
        } else {
            return error();
        }
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

        openStarRate(course);

        return success(dto);
    }


    /**
     * 开启星评
     * @param courseId
     */
    public void openStarRate(AudioCourse course) {
        if (course != null) {
            if (course.getPlayType().intValue() == AudioCourse.PlayType.normal.getType()) {
                AudioCoursePlay play = audioService.findPlayState(course.getId());
                play.setPlayState(AudioCoursePlay.PlayState.rating.ordinal());
                audioService.updateAudioCoursePlay(play);
            } else {
                //修改直播状态为星评中状态
                Live live = liveService.findByCourseId(course.getId());
                live.setLiveState(AudioCoursePlay.PlayState.rating.ordinal());
                liveService.updateByPrimaryKey(live);
            }
        }

        //发送开启星评指令
        LiveOrderDTO order = new LiveOrderDTO();
        order.setCourseId(String.valueOf(course.getId()));
        order.setOrder(LiveOrderDTO.ORDER_STAR_RATE_START);
        liveService.publish(order);

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
     * 小程序创建课件或者更新课件
     * @param courseId
     * @param files
     * @param title
     * @param imgId
     * @param musicId
     * @return
     * @throws SystemException
     */
    @RequestMapping("/mini/create/update")
    @ResponseBody
    public String createOrUpdateAudio(Integer courseId, @RequestParam("files") MultipartFile[] files, String title, Integer imgId,Integer musicId) throws SystemException {

        String userId = SecurityUtils.get().getId();
        AudioCourse course = new AudioCourse();
        course.setId(courseId);
        course.setTitle(title);
        course.setUserId(userId);
        course.setSourceType(AudioCourse.SourceType.QuickMeet.ordinal());

        AudioCourseTheme theme = new AudioCourseTheme();
        theme.setMusicId(musicId);
        theme.setImageId(imgId);

        //新建课件
        if(courseId == null){
            if(files == null){
                return error(local("upload.error.null"));
            }
            if(StringUtils.isEmpty(title)){
                return error(local("meeting.title.not.none"));
            }
            courseId = audioService.createAudioAndDetail(files, course, theme);
            Map<String,Integer> map = new HashMap<>();
            map.put("courseId",courseId);
            return success(map);

        }else{  //修改课件
            theme.setCourseId(courseId);
            audioService.updateMiniCourse(course,theme);
            return success();
        }
    }

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
     * 小程序接口
     * 获取主题和背景音乐
     * @param type type为null或者0时，获取主题，为1时获取背景音乐
     * @return
     */
    @RequestMapping("/mini/image/music")
    @ResponseBody
    public String getImageAndMusic(Integer type){
        if(type == null){
            type = AudioCourseTheme.ImageMusic.IMAGE.ordinal();
        }
        Map<String,Object> map = new HashMap<>();
        //获取主题
         if(type == AudioCourseTheme.ImageMusic.IMAGE.ordinal()){
            List<BackgroundImage> imageList = courseThemeService.findImageList();
            map.put("imageList",imageList);
             return success(map);
         }else{
             //获取背景音乐
             List<BackgroundMusic> musicList = courseThemeService.findMusicList();
             map.put("musicList",musicList);
             return success(map);
         }
    }

    private enum ImageMusic{
        IMAGE,
        MUSIC;
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


}
