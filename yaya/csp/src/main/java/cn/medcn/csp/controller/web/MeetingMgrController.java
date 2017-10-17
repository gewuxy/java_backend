package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.csp.security.Principal;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.service.AudioService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lixuan on 2017/10/17.
 */
@Controller
@RequestMapping(value = "/web/meet")
public class MeetingMgrController extends BaseController{

    @Autowired
    protected AudioService audioService;

    @RequestMapping(value = "/list")
    public String list(Pageable pageable, Model model){
        //web获取当前用户信息
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        pageable.put("cspUserId", principal.getId());
        MyPage<CourseDeliveryDTO> page = audioService.findCspMeetingList(pageable);
        model.addAttribute("page", page);
        return localeView("/meeting/list");
    }
}
