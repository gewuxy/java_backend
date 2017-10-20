package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.DeliveryAccepterDTO;
import cn.medcn.meet.dto.DeliveryHistoryDTO;
import cn.medcn.meet.model.CourseDelivery;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.CourseDeliveryService;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/9/26.
 */
@Controller("webDeliveryController")
@RequestMapping(value = "/mgr/delivery")
public class DeliveryController extends CspBaseController {

    @Autowired
    protected CourseDeliveryService courseDeliveryService;

    @Autowired
    protected AppUserService appUserService;

    @Autowired
    protected AudioService audioService;

    @Value("${app.file.base}")
    protected String fileBase;



    /**
     * 投稿
     * @param courseId
     * @param accepts
     * @return
     */
    @RequestMapping("/contribute")
    @ResponseBody
    public String contribute(Integer courseId,Integer[] accepts)  {
        if(courseId == null){
            return error("courseId不能为空");
        }
        if(accepts.length == 0){
            return error("请指定投稿单位号");
        }
        String authorId = getWebPrincipal().getId();
        try {
            //投稿
            courseDeliveryService.contribute(courseId,accepts,authorId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }


    @RequestMapping("/history")
    public String history(Model model){
        //接收者列表
        Pageable pageable1 = new Pageable();
        String authorId = SecurityUtils.get().getId();
        pageable1.put("authorId",authorId);
        MyPage<AppUser> acceptPage = appUserService.findAccepterList(pageable1);
        AppUser.splitUserAvatar(acceptPage.getDataList(),fileBase);
        model.addAttribute("acceptList",acceptPage.getDataList());

        //投给第一个接收者的会议列表
        Pageable pageable2 = new Pageable();
        Integer firstAcceptId = acceptPage.getDataList().get(0).getId();
        pageable2.put("acceptId",firstAcceptId);
        MyPage<CourseDeliveryDTO> meetPage = audioService.findCspMeetingList(pageable2);
        CourseDeliveryDTO.splitCoverUrl(meetPage.getDataList(),fileBase);
        model.addAttribute("page",meetPage);
        return localeView("/meeting/history");
    }
}
