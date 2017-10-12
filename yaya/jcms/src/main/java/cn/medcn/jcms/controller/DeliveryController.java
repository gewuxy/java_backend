package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.service.CourseDeliveryService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixuan on 2017/9/26.
 */
@Controller
@RequestMapping(value = "/func/meet/delivery")
public class DeliveryController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CourseDeliveryService courseDeliveryService;

    @RequestMapping(value = "/accept")
    @ResponseBody
    public String accept(){

        return success();
    }

    /**
     * 关闭或开启投稿功能,页面重新加载
     * @return
     */
    @RequestMapping("/change")
    @ResponseBody
    public String close(Integer flag){
        Integer userId = SubjectUtils.getCurrentUserid();
        appUserService.doChangeDelivery(userId,flag);
        return success();
    }

    /**
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public String users(Pageable pageable, Model model){
        Integer userId = SubjectUtils.getCurrentUserid();
        //查询用户是否开启投稿功能
        if(pageable.getPageNum() == 1){
            int flag = appUserService.findDeliveryFlag(userId);
            if(flag == 0){  //没有开启投稿功能
                return success();
            }
        }

        pageable.put("userId",userId);
        MyPage<CourseDeliveryDTO> myPage = courseDeliveryService.findDeliveryList(pageable);
        model.addAttribute("list",myPage.getDataList());
        return "/res/";
    }
}
