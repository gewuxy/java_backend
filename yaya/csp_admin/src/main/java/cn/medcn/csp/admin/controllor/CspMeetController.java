package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.service.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * by create HuangHuibin 2017/11/9
 */
@Controller
@RequestMapping(value="/csp/meet")
public class CspMeetController extends BaseController{

    @Autowired
    private MeetService meetService;

    /**
     * 获取会议列表
     * @param pageable
     * @param meetName
     * @param model
     * @return
     */
    @RequestMapping(value="/list")
    public String searchMeetList(Pageable pageable, String meetName, Model model){
        if (!StringUtils.isEmpty(meetName)) {
            pageable.getParams().put("keyword", meetName);
            model.addAttribute("meetName",meetName);
        }
        MyPage<MeetInfoDTO> page = meetService.searchMeetInfo(pageable);
        model.addAttribute("page",page);
        return "/meet/meetList";
    }

    /**
     * 查询会议信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/info")
    public String searchMeetInfo(String id,Model model){
        MeetInfoDTO info = meetService.findMeetInfo(id);
        model.addAttribute("meet",info);
        return "/meet/meetInfo";
    }

    /**
     * 更新会议信息
     * @param meet
     * @param model
     * @return
     */
    @RequestMapping(value="/update")
    public String update(Meet meet, Model model,RedirectAttributes redirectAttributes){
        if (!StringUtils.isEmpty(meet.getMeetName())) {
            model.addAttribute("meetName",meet.getMeetName());
        }
        meetService.updateByPrimaryKeySelective(meet);
        addFlashMessage(redirectAttributes, "更新成功");
        return "redirect:/csp/meet/list";
    }


}
