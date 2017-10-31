package cn.medcn.csp.controller.web;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.sys.model.SystemNotify;
import cn.medcn.sys.service.SysNotifyService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 平台通知
 * Created by LiuLP on 2017/10/17.
 */
@Controller
@RequestMapping("/mgr/message")
public class MessageController extends CspBaseController {

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected SysNotifyService sysNotifyService;


    /**
     * 通知列表
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Pageable pageable,Model model){
        String userId = getWebPrincipal().getId();
        pageable.put("acceptId",userId);
        MyPage<SystemNotify> myPage = sysNotifyService.findNotifyList(pageable);
        model.addAttribute("page",myPage);
        int unreadCount = sysNotifyService.findUnreadMsgCount(userId);
        model.addAttribute("unreadCount",unreadCount);
        return localeView("/notify/list");
    }

    /**
     * 消息内容
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public String detail(String id,Model model) throws SystemException {
        if(StringUtils.isEmpty(id)){
            throw new SystemException("非法操作");
        }
        SystemNotify notify = sysNotifyService.selectByPrimaryKey(id);
        //改为已读状态
        if(notify != null){
            notify.setIsRead(true);
            sysNotifyService.updateByPrimaryKeySelective(notify);
        }
        model.addAttribute("notify",notify);
        String userId = getWebPrincipal().getId();
        int unreadCount = sysNotifyService.findUnreadMsgCount(userId);
        model.addAttribute("unreadCount",unreadCount);
        return localeView("/notify/detail");
    }


    /**
     * 是否有未读通知
     * @return
     */
    @RequestMapping("/status")
    @ResponseBody
    public String notifyStatus(){
        String userId = getWebPrincipal().getId();
        int unreadCount = sysNotifyService.findUnreadMsgCount(userId);
        return success(unreadCount);
    }
}
