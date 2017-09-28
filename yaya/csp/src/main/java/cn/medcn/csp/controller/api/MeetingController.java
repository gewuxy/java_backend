package cn.medcn.csp.controller.api;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.csp.dto.ZeGoCallBack;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.LiveService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 会议控制器
 * Created by lixuan on 2017/9/27.
 */
@Controller
@RequestMapping(value = "/api/meeting")
public class MeetingController extends BaseController {


    @Autowired
    protected AudioService audioService;

    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected LiveService liveService;

    @Value("${ZeGo.replay.expire.days}")
    protected int expireDays;

    /**
     * 会议阅览
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/view")
    @ResponseBody
    public String view(Integer courseId){
        AudioCourse audioCourse = audioService.findAudioCourse(courseId);
        return success(audioCourse);
    }

    /**
     * 上传音频
     * @param file
     * @param courseId
     * @param detailId
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false)MultipartFile file, Integer courseId, Integer detailId){
        StringBuffer buffer = new StringBuffer(FilePath.COURSE.path);
        buffer.append("/").append(courseId).append("/audio");
        String relativePath = buffer.toString();
        try {
            FileUploadResult result = fileUploadService.upload(file, relativePath);
            Map<String, Object> map = new HashedMap();
            map.put("audioUrl", result.getAbsolutePath());
            return success(result);
        } catch (SystemException e) {
            e.printStackTrace();
            return error();
        }

    }

    /**
     * 直播指令
     * @param dto
     * @return
     */
    @RequestMapping(value = "/live")
    @ResponseBody
    public String live(LiveOrderDTO dto){
        dto.setOrder(LiveOrderDTO.ORDER_LIVE);
        liveService.publish(dto);
        return success();
    }

    /**
     * 同步指令 在扫码投屏同步的时候用到
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sync")
    @ResponseBody
    public String sync(LiveOrderDTO dto){
        dto.setOrder(LiveOrderDTO.ORDER_SYNC);
        liveService.publish(dto);
        return success();
    }

    /**
     * 扫码投屏直播|录播
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/scan/callback")
    @ResponseBody
    public String handleScan(Integer courseId){
        Principal principal = SecurityUtils.get();
        AudioCourse audioCourse = audioService.findAudioCourse(courseId);
        //判断用户是否有权限使用此课件
        if (!principal.getId().equals(audioCourse.getCspUserId())) {
            return error(local("course.error.author"));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("course", audioCourse);
        if (audioCourse.getPlayType().intValue() > AudioCourse.PlayType.normal.ordinal()) {
            //查询出直播信息
            Live live = liveService.findByCourseId(courseId);
            result.put("live", live);
        }
        return success(result);
    }

    @RequestMapping(value = "/live/create")
    @ResponseBody
    public String onCreate(ZeGoCallBack callback){
        try {
            callback.signature();

            Integer channelId = Integer.valueOf(callback.getChannel_id());
            Live live = liveService.findByCourseId(channelId);
            if (live != null) {
                live.setHdlUrl(callback.getHdl_url()[0]);
                live.setRtmpUrl(callback.getRtmp_url()[0]);
                live.setHlsUrl(callback.getHls_url()[0]);
                live.setLiveState(Live.LiveState.usable.ordinal());
                liveService.updateByPrimaryKey(live);
            }

            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @RequestMapping(value = "/live/close")
    @ResponseBody
    public String onClose(ZeGoCallBack callback){
        try {
            callback.signature();
            Integer channelId = Integer.valueOf(callback.getChannel_id());
            Live live = liveService.findByCourseId(channelId);
            if (live != null) {
                live.setLiveState(Live.LiveState.closed.ordinal());
                live.setCloseType(callback.getType());
                liveService.updateByPrimaryKey(live);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    @RequestMapping(value = "/live/replay")
    @ResponseBody
    public String onReplay(ZeGoCallBack callback){
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
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
}
