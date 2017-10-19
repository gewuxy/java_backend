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
@Controller("webDeliveryController")
@RequestMapping(value = "/mgr/delivery")
public class DeliveryController extends BaseController {

    @Autowired
    protected CourseDeliveryService courseDeliveryService;

    @Autowired
    protected AppUserService appUserService;

    @Value("${app.file.base}")
    protected String fileBase;



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
        return localeView("redirect:/mgr/meet/list");
    }

    @RequestMapping("/history")
    public String history(Pageable pageable){
        String authorId = SecurityUtils.get().getId();
        pageable.put("authorId",authorId);
        MyPage<AppUser> myPage = appUserService.findAccepterList(pageable);

        return "";
    }
}
