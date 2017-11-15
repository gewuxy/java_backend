package cn.medcn.csp.controller.web;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.CourseDeliveryService;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            return error(local("courseId.empty"));
        }
        if(accepts.length == 0){
            return error(local("institution.account.empty"));
        }
        String authorId = getWebPrincipal().getId();
        try {
            //投稿
            courseDeliveryService.executeDelivery(courseId,accepts,authorId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     * 投稿历史
     * @param pageable
     * @param acceptId
     * @param model
     * @return
     */
    @RequestMapping("/history")
    public String history(Pageable pageable,Integer acceptId,Model model){
        pageable.setPageSize(6);
        //接收者列表
        Pageable regular = new Pageable();
        List<AppUser> userList = addAcceptList(regular,model);
        if(!CheckUtils.isEmpty(userList) && acceptId == null){
            //投给第一个接收者的会议列表
            acceptId = userList.get(0).getId();
        }
        addMeetList(acceptId,pageable,model);
        model.addAttribute("current",acceptId);
        return localeView("/meeting/history");
    }




    private List<AppUser> addAcceptList(Pageable pageable,Model model){
        String authorId = getWebPrincipal().getId();
        pageable.put("authorId",authorId);
        MyPage<AppUser> acceptPage = appUserService.findAccepterList(pageable);
        List<AppUser> userList = acceptPage.getDataList();
        AppUser.splitUserAvatar(userList,fileBase);
        model.addAttribute("acceptList",userList);
        return userList;
    }

    private void addMeetList(Integer acceptId,Pageable pageable,Model model){
        String authorId = getWebPrincipal().getId();
        pageable.put("authorId",authorId);
        pageable.put("acceptId",acceptId);
        MyPage<CourseDeliveryDTO> meetPage = audioService.findHistoryDeliveryByAcceptId(pageable);
        CourseDeliveryDTO.splitCoverUrl(meetPage.getDataList(),fileBase);
        model.addAttribute("page",meetPage);
    }
}
