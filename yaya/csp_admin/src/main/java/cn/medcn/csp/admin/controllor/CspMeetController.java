package cn.medcn.csp.admin.controllor;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
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
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.model.UserFluxUsage;
import cn.medcn.user.service.UserFluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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

    @Autowired
    private AudioService audioService;

    @Autowired
    private LiveService liveService;


    @Autowired
    protected UserFluxService userFluxService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

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
    public String delete(Integer id,RedirectAttributes redirectAttributes){
        AudioCourse audioCourse = new AudioCourse();
        audioCourse.setId(id);
        audioCourse.setDeleted(true);
        audioService.updateByPrimaryKeySelective(audioCourse);
        audioService.deleteAllDetails(id);
        addFlashMessage(redirectAttributes, "关闭成功");
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




}
