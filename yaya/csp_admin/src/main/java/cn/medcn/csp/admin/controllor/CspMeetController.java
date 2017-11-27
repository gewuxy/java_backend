package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.CourseReprintDTO;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * by create HuangHuibin 2017/11/9
 */
@Controller
@RequestMapping(value="/csp/meet")
public class CspMeetController extends BaseController{

    @Value("${app.file.base}")
    protected String fileBase;

    @Autowired
    private AudioService audioService;

    @RequestMapping(value="/list")
    @Log(name = "获取会议列表")
    public String searchMeetList(Pageable pageable, String keyword, Model model){
        if (!StringUtils.isEmpty(keyword)) {
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword",keyword);
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
    public String view(@PathVariable Integer courseId, Model model) {
        AudioCourse course = audioService.findAudioCourse(courseId);
        handleHttpPath(course);
        return success(course);
    }

    protected void handleHttpPath(AudioCourse course) {
        handleHttpUrl(fileBase, course);
    }


    @RequestMapping(value="/delete")
    @Log(name = "删除会议")
    public String delete(Integer id,RedirectAttributes redirectAttributes){
        AudioCourse audioCourse = new AudioCourse();
        audioCourse.setId(id);
        audioCourse.setDeleted(true);
        audioService.updateByPrimaryKeySelective(audioCourse);
        audioService.deleteAllDetails(id);
        addFlashMessage(redirectAttributes, "删除成功");
        return "redirect:/csp/meet/list";
    }

    protected void handleHttpUrl(String fileBase, AudioCourse course){
        if (course != null && !CheckUtils.isEmpty(course.getDetails())) {
            for (AudioCourseDetail detail : course.getDetails()) {
                if (CheckUtils.isNotEmpty(detail.getAudioUrl()) && !detail.getAudioUrl().startsWith("http")) {
                    detail.setAudioUrl(fileBase + detail.getAudioUrl());
                }
                if (CheckUtils.isNotEmpty(detail.getImgUrl()) && !detail.getImgUrl().startsWith("http")) {
                    detail.setImgUrl(fileBase + detail.getImgUrl());
                }
                if (CheckUtils.isNotEmpty(detail.getVideoUrl()) && !detail.getVideoUrl().startsWith("http")) {
                    detail.setVideoUrl(fileBase + detail.getVideoUrl());
                }
            }
        }
    }
}
