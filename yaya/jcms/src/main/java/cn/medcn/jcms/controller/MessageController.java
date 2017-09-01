package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.JpushMessageDTO;
import cn.medcn.meet.dto.MeetListInfoDTO;
import cn.medcn.meet.model.JpushMessage;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.model.MeetNotify;
import cn.medcn.meet.service.JpushMessageService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.model.Group;
import cn.medcn.user.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lixuan on 2017/5/26.
 */
@Controller
@RequestMapping(value = "/func/msg")
public class MessageController extends BaseController {

    @Autowired
    private MeetService meetService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private JpushMessageService jpushMessageService;


    @RequestMapping(value = "/add")
    public String add(Model model){
        Principal principal = SubjectUtils.getCurrentUser();
        List<Group> groupList = doctorService.findGroupList(principal.getId());
        model.addAttribute("groupList", groupList);
        Pageable pageable = new Pageable();
        pageable.getParams().put("masterId", principal.getId());
        MyPage<MeetListInfoDTO> page = meetService.findMeetForSend(pageable);
        model.addAttribute("meets", page.getDataList());
        return "/msg/send";
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(JpushMessage message, Integer[] flats, Model model) throws SystemException {
        if (flats == null || flats.length == 0){
            throw new SystemException("请选择要发送到的平台");
        }
        if (message.getGroupId() == null && message.getReceiver() == null){
            throw new SystemException("参数错误:群组ID和用户ID不能同时为空！");
        }
        if (flats.length == 2){
            message.setFlat(JpushMessage.SendFlat.all.ordinal());
        } else {
            message.setFlat(flats[0]);
        }
        message.setMsgType(JpushMessage.MessageType.meetNotify.ordinal());
        message.setTitle(MeetNotify.NotifyType.values()[message.getNotifyType()].title);

        Principal principal = SubjectUtils.getCurrentUser();
        message.setSender(principal.getId());
        jpushMessageService.appendToQueue(message);
        return success();
    }

    @RequestMapping(value = "/list")
    public String list(Pageable pageable, Model model){
        pageable.setPageSize(10);
        Principal principal = SubjectUtils.getCurrentUser();
        MyPage<JpushMessageDTO> page = jpushMessageService.findHistories(pageable, principal.getId());
        model.addAttribute("page", page);
        return "/msg/list";
    }
}
