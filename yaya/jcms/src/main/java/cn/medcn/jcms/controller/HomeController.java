package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.CourseReprintDTO;
import cn.medcn.meet.dto.MeetListInfoDTO;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.model.AppMenu;
import cn.medcn.user.service.AppMenuService;
import cn.medcn.user.service.DoctorService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lixuan on 2017/5/8.
 */
@Controller
@RequestMapping(value="/home")
public class HomeController extends BaseController{

    @Autowired
    private AppMenuService appMenuService;

    @Value("${app.file.base}")
    private String appFileBase;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private AudioService audioService;

    @RequestMapping(value="/")
    public String main(Model model){
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("testUnitAccount", principal.getUsername().endsWith("@medcn.cn"));
        List<AppMenu> menuList = appMenuService.findMenuByRole(principal.getRoleId());
        List<AppMenu> list = AppMenu.sort(menuList);
        model.addAttribute("menuList", list);
        return "/index";
    }


    @RequestMapping(value="/index")
    public String index(Model model){
        Principal principal = SubjectUtils.getCurrentUser();
        model.addAttribute("nickname", principal.getNickname());
        Credits credits = creditsService.doFindMyCredits(principal.getId());
        model.addAttribute("headimg", appFileBase+principal.getHeadimg());
        model.addAttribute("credits", credits);
        //查询出关注人数
        Integer attentionCount = doctorService.findAllDocCount(principal.getId());
        model.addAttribute("attention", attentionCount);
        //查询出我已经发布的会议
        Pageable pageable = new Pageable(1, 6);
        pageable.getParams().put("masterId", principal.getId());
        MyPage<MeetListInfoDTO> page = meetService.findPublished(pageable);
        model.addAttribute("meetList", page.getDataList());
        //查询出最新资源
        pageable = new Pageable(1, 6);
        pageable.getParams().put("userId", principal.getId());
        MyPage<CourseReprintDTO> resPage = audioService.findResource(pageable);
        model.addAttribute("resList", resPage.getDataList());
        return "/index/index";
    }

}
