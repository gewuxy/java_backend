package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.DeliveryAccepterDTO;
import cn.medcn.meet.dto.DeliveryHistoryDTO;
import cn.medcn.meet.model.CourseDelivery;
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
@Controller
@RequestMapping(value = "/mgr/delivery")
public class DeliveryMgrController extends BaseController {

    @Autowired
    protected CourseDeliveryService courseDeliveryService;

    @Autowired
    protected AppUserService appUserService;

    @Value("${app.file.base}")
    protected String fileBase;


    /**
     * 打开了投稿箱的公众号列表
     * @return
     */
    @RequestMapping("/accepterList")
    public String accepterList(Pageable pageable,Integer courseId,Model model) throws SystemException {
        if(courseId == null){
            throw new SystemException("courseId不能为空");
        }
        model.addAttribute("courseId",courseId);
        MyPage<AppUser> myPage = appUserService.findAccepterList(pageable);
        model.addAttribute("page",myPage);
        return "";
    }

    /**
     * 投稿
     * @param courseId
     * @param accepts
     * @return
     */
    @RequestMapping("/contribute")
    public String contribute(Integer courseId,Integer[] accepts) throws SystemException {
        if(courseId == null){
            throw new SystemException("courseId不能为空");
        }
        if(accepts.length == 0){
            throw new SystemException("请指定投稿单位号");
        }
        String authorId = SecurityUtils.get().getId();
        for(Integer acceptId:accepts){
            CourseDelivery delivery = new CourseDelivery();
            delivery.setId(StringUtils.nowStr());
            delivery.setAcceptId(acceptId);
            delivery.setAuthorId(authorId);
            delivery.setSourceId(courseId);
            delivery.setDeliveryTime(new Date());
            courseDeliveryService.insert(delivery);
        }
        return "";
    }
}
