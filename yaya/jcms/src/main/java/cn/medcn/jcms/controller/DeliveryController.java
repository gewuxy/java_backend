package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.AudioCourseDetailDTO;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.service.CourseDeliveryService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.SchemaOutputResolver;

/**
 * Created by lixuan on 2017/9/26.
 */
@Controller
@RequestMapping(value = "/func/meet/delivery")
public class DeliveryController extends BaseController {



    @Autowired
    private CourseDeliveryService courseDeliveryService;


    @RequestMapping(value = "/accept")
    @ResponseBody
    public String accept(){

        return success();
    }


    /**
     * 点击引用资源显示csp投稿列表
     * @param pageable
     * @param meetId
     * @param moduleId
     * @param model
     * @return
     */
    @RequestMapping("/forCSP")
    public String forCSP(Pageable pageable,String meetId, Integer moduleId, Model model){
        pageable.setPageSize(6);
        model.addAttribute("meetId", meetId);
        model.addAttribute("moduleId", moduleId);
        pageable.getParams().put("userId", SubjectUtils.getCurrentUser().getId());
        MyPage<CourseDeliveryDTO> myPage = courseDeliveryService.findCSPList(pageable);
        model.addAttribute("page",myPage);
        return "/res/forCSP";
    }
}
