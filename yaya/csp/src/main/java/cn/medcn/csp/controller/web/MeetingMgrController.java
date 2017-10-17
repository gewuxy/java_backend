package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.FileUtils;
import cn.medcn.common.utils.QRCodeUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.Principal;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.service.AudioService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 查询当前用户的课件列表
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Pageable pageable, Model model){
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
     * 根据请求生成ws地址
     * @param request
     * @return
     */
    protected String genWsUrl(HttpServletRequest request, Integer courseId){
        StringBuffer buffer = new StringBuffer();
        buffer.append(request.getScheme().toLowerCase().equals("https") ? "wss" : "ws");
        buffer.append("://").append(request.getServerName()).append(":").append(request.getServerPort());
        buffer.append("/live/order?courseId=").append(courseId
        );
        return buffer.toString();
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
        model.addAttribute("course", course);
        return localeView("/meeting/edit");
    }
}
