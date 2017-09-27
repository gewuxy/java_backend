package cn.medcn.csp.controller.api;

import cn.jmessage.api.resource.UploadResult;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.service.AudioService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by lixuan on 2017/9/27.
 */
@Controller
@RequestMapping(value = "/api/meeting")
public class MeetingController extends BaseController {


    @Autowired
    protected AudioService audioService;

    @Autowired
    protected FileUploadService fileUploadService;

    @RequestMapping(value = "/view")
    @ResponseBody
    public String view(Integer courseId){
        AudioCourse audioCourse = audioService.findAudioCourse(courseId);
        return success(audioCourse);
    }


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
}
