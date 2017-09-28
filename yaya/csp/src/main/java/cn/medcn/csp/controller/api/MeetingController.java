package cn.medcn.csp.controller.api;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.csp.dto.CloseCallback;
import cn.medcn.csp.dto.CreateCallback;
import cn.medcn.csp.dto.ReplayCallback;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.LiveService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

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
    public String onCreate(CreateCallback callback){
        try {
            callback.signature();
            // todo 修改对应的直播信息
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @RequestMapping(value = "/live/close")
    @ResponseBody
    public String onClose(CloseCallback callback){
        try {
            callback.signature();
            // todo 修改对应的直播信息
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    @RequestMapping(value = "/live/replay")
    @ResponseBody
    public String onReplay(ReplayCallback callback){
        try {
            callback.signature();
            // todo 修改对应的直播信息
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
}
