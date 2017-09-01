package cn.medcn.api.controller;

import cn.medcn.api.dto.Principal;
import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.meet.dto.MeetMessageDTO;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.service.MeetMessageService;
import cn.medcn.meet.service.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by lixuan on 2017/4/28.
 */
@Controller
@RequestMapping(value="/api/meet/message")
public class MessageController {

    @Autowired
    private MeetMessageService meetMessageService;

    @Autowired
    private MeetService meetService;

    @Value("${app.file.base}")
    private String appFileBase;

//    @RequestMapping(value="/join")
//    public String join(String meetId, Model model){
//        model.addAttribute(Constants.MEET_ID_KEY, meetId);
//        model.addAttribute(Constants.TOKEN, SecurityUtils.getCurrentUserInfo().getToken());
//        model.addAttribute("uid",SecurityUtils.getCurrentUserInfo().getId());
//        return "/message";
//    }


    @RequestMapping(value="/histories")
    @ResponseBody
    public String histories(Pageable pageable, String meetId){
        pageable.getParams().put(Constants.MEET_ID_KEY, meetId);
        MyPage<MeetMessageDTO> page = meetMessageService.findLastMessage(pageable);
        if(page.getDataList() != null && page.getDataList().size() > 0){
            for(MeetMessageDTO message : page.getDataList()){
                if(!StringUtils.isEmpty(message.getHeadimg())){
                    message.setHeadimg(appFileBase + message.getHeadimg());
                }else{
                    message.setHeadimg("");
                }
            }
        }
        return APIUtils.success(page);
    }

    @RequestMapping(value="/send", method = RequestMethod.POST)
    @ResponseBody
    public String sendMsg(MeetMessageDTO message){
        Principal principal = SecurityUtils.getCurrentUserInfo();
        message.setSender(principal.getNickname());
        message.setHeadimg(appFileBase+principal.getHeadimg());
        message.setSenderId(principal.getId());
        message.setSendTime(new Date());
        if(!StringUtils.isEmpty(message.getMeetId())){
            Meet meet = meetService.selectByPrimaryKey(message.getMeetId());
            message.setMeetName(meet == null ? "" : meet.getMeetName());
        }
        meetMessageService.publish(message);
        return APIUtils.success();
    }

}
