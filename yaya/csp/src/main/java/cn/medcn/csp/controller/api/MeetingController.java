package cn.medcn.csp.controller.api;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.AddressDTO;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.*;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.dto.ZeGoCallBack;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.medcn.common.Constants.ABROAD_KEY;
import static cn.medcn.common.Constants.LOCAL_KEY;
import static cn.medcn.csp.CspConstants.ZEGO_SUCCESS_CODE;

/**
 * 会议控制器
 * Created by lixuan on 2017/9/27.
 */
@Controller
@RequestMapping(value = "/api/meeting")
public class MeetingController extends CspBaseController {


    @Autowired
    protected AudioService audioService;

    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected LiveService liveService;

    @Value("${ZeGo.replay.expire.days}")
    protected int expireDays;

    @Value("${app.file.upload.base}")
    protected String fileUploadBase;

    @Value("${app.file.base}")
    protected String fileBase;

    /**
     * 会议阅览
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/view")
    @ResponseBody
    public String view(Integer courseId) {
        AudioCourse audioCourse = audioService.findAudioCourse(courseId);
        return success(audioCourse);
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
    public String share(String signature, Model model, HttpServletRequest request) throws SystemException {

        Map<String, Object> params = parseParams(signature);
        String id = (String) params.get("id");
        String local = (String) params.get(LOCAL_KEY);
        LocalUtils.set(LocalUtils.getByKey(local));
        LocalUtils.setLocalStr(local);

        String abroad = (String) params.get(ABROAD_KEY);
        boolean isAbroad = CheckUtils.isEmpty(abroad) ? false : ("0".equals(abroad) ? false : true);

        AddressDTO address = AddressUtils.parseAddress(request.getRemoteHost());
        if (address.isAbroad() && !isAbroad || isAbroad && !address.isAbroad()) {
            throw new SystemException(local("source.access.deny"));
        }

        Integer courseId = Integer.valueOf(id);
        AudioCourse course = audioService.findAudioCourse(courseId);
        if (course == null) {
            throw new SystemException(local("source.not.exists"));
        }

        model.addAttribute("course", course);
        if (course.getPlayType() == null) {
            course.setPlayType(0);
        }
        if (AudioCourse.PlayType.normal.ordinal() < course.getPlayType()) {//直播
            Live live = liveService.findByCourseId(courseId);
            model.addAttribute("live", live);
        }

        return localeView("/meeting/course_" + course.getPlayType().intValue());
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
     * @param courseId
     * @param detailId
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, Integer courseId, Integer detailId) {
        StringBuffer buffer = new StringBuffer(FilePath.COURSE.path);
        buffer.append("/").append(courseId).append("/audio");
        String relativePath = buffer.toString();
        try {
            FileUploadResult result = fileUploadService.upload(file, relativePath);
            Map<String, Object> map = new HashMap<>();
            AudioCourseDetail detail = audioService.findDetail(detailId);
            if (detail != null) {
                detail.setAudioUrl(result.getRelativePath());
                detail.setDuration(FFMpegUtils.duration(fileUploadBase + result.getRelativePath()));
                audioService.updateDetail(detail);
            }
            map.put("audioUrl", result.getAbsolutePath());
            return success(result);
        } catch (SystemException e) {
            e.printStackTrace();
            return error(e.getMessage());
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

        String result = null;
        try {
            result = courseInfo(courseId, request);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        //发送ws同步指令
        LiveOrderDTO order = new LiveOrderDTO();
        order.setCourseId(String.valueOf(courseId));
        order.setOrder(LiveOrderDTO.ORDER_SYNC);
        order.setPageNum(0);
        liveService.publish(order);

        return success(result);
    }


    protected String courseInfo(Integer courseId, HttpServletRequest request) throws SystemException{
        Principal principal = SecurityUtils.get();
        AudioCourse audioCourse = audioService.findAudioCourse(courseId);
        //判断用户是否有权限使用此课件
        if (!principal.getId().equals(audioCourse.getCspUserId())) {
            throw new SystemException(local("course.error.author"));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("course", audioCourse);
        result.put("wsUrl", genWsUrl(request, courseId));
        if (audioCourse.getPlayType().intValue() > AudioCourse.PlayType.normal.ordinal()) {
            //查询出直播信息
            Live live = liveService.findByCourseId(courseId);
            result.put("live", live);
        } else {//录播查询录播的进度信息

        }

        return success(result);
    }

    @RequestMapping(value = "/join")
    @ResponseBody
    public String join(Integer courseId, HttpServletRequest request){
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

            Integer channelId = Integer.valueOf(callback.getChannel_id());
            Live live = liveService.findByCourseId(channelId);
            if (live != null) {
                live.setReplayUrl(callback.getReplay_url());
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
        MyPage<CourseDeliveryDTO> page = audioService.findCspMeetingList(pageable);

        if (!CheckUtils.isEmpty(page.getDataList())) {
            for (CourseDeliveryDTO deliveryDTO : page.getDataList()) {
                if (StringUtils.isNotEmpty(deliveryDTO.getCoverUrl())) {
                    deliveryDTO.setCoverUrl(fileBase + deliveryDTO.getCoverUrl());
                }

                // 录播会议
                if (deliveryDTO.getPlayType().intValue() == AudioCourse.PlayType.normal.getType().intValue()) {
                    // 录播 当前播放第几页
                    if (deliveryDTO.getPlayPage() == null) {
                        deliveryDTO.setPlayPage(0);
                    }
                    // 转换录播音频时长格式
                    if (StringUtils.isNotEmpty(deliveryDTO.getPlayTime())) {
                        long secondTime = Long.parseLong(deliveryDTO.getPlayTime());
                        deliveryDTO.setPlayTime(CalendarUtils.formatTimesDiff(null,null, secondTime));
                    }
                } else {
                    // 直播 当前播放第几页
                    if (deliveryDTO.getLivePage() == null) {
                        deliveryDTO.setLivePage(0);
                    }
                    // 如果是直播会议 需计算直播时长
                    int liveState = deliveryDTO.getLiveState().intValue();
                    if (liveState == Live.LiveState.init.ordinal()) {
                        // 直播未开始 设置直播开始时间
                        deliveryDTO.setPlayTime(CalendarUtils.transferLongToDate(deliveryDTO.getStartTime().getTime(),"MM月dd日 HH:mm"));
                    } else if (liveState == Live.LiveState.usable.ordinal()) {
                        // 直播中 计算直播开始时间和当前时间的时间差
                        deliveryDTO.setPlayTime(CalendarUtils.formatTimesDiff(deliveryDTO.getStartTime(), new Date(),0));
                    } else {
                        // 直播结束 计算直播开始和结束的时间差
                        deliveryDTO.setPlayTime(CalendarUtils.formatTimesDiff(deliveryDTO.getStartTime(), deliveryDTO.getEndTime(),0));
                    }
                }

            }
        }
        return success(page.getDataList());
    }


}
