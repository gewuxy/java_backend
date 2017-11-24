package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.csp.admin.model.CspAdminMessage;
import cn.medcn.csp.admin.service.MessageService;
import cn.medcn.csp.admin.utils.SubjectUtils;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SystemUserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * 公告消息 Created by jianliang
 */

@Controller
@RequestMapping(value = "/csp/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SystemUserService systemUserService;

    @RequestMapping(value = "/list")
    public String list(Pageable pageable, Model model) {
        MyPage<CspAdminMessage> myPage = messageService.findMessageListByPage(pageable);
        model.addAttribute("page", myPage);
        return "/message/messageList";
    }

    /**
     * 发布公告
     */
    @RequestMapping(value = "/messageInfo")
    public String messageInfo() {

        return "/message/messageInfo";
    }

    @RequestMapping(value = "/sendMessage")
    public String sendMessage(CspAdminMessage message, Model model) {
        Integer userId = SubjectUtils.getCurrentUserid();
        if (message != null) {
            message.setId(UUIDUtil.getNowStringID());
            message.setUserId(userId);
            SystemUser user = systemUserService.selectByPrimaryKey(userId);
            message.setUsername(user.getUserName());
            message.setCreatTime(new Date());
            message.setUpdateTime(message.getCreatTime());
            messageService.insert(message);
        }
        return "redirect:/csp/message/list";
    }

    /**
     * 回显
     */
    @RequestMapping(value = "/edit")
    public String messageEdit(@RequestParam(value = "id", required = true, defaultValue = "1") String messageId, Model models) {
        CspAdminMessage message = messageService.selectByPrimaryKey(messageId);
        models.addAttribute("message", message);
        return "/message/messageInfoEdit";
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    public String update(@RequestParam(value = "id", required = true, defaultValue = "1") String messageId,
                         String messageTitle, String messageContent, String sendMessageName) {
        CspAdminMessage message = messageService.selectByPrimaryKey(messageId);
        message.setMessageTitle(messageTitle);
        message.setMessageContent(messageContent);
        message.setUsername(sendMessageName);
        message.setUpdateTime(new Date());
        messageService.updateByPrimaryKey(message);
        return "redirect:/csp/message/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam(value = "id", required = true, defaultValue = "1") String messageId) {
        CspAdminMessage message = messageService.selectByPrimaryKey(messageId);
        int count = messageService.deleteByPrimaryKey(messageId);
        return "redirect:/csp/message/list";
    }
}
