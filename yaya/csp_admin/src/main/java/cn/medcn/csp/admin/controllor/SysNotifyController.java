package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.csp.admin.utils.SubjectUtils;
import cn.medcn.sys.model.SystemNotify;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SysNotifyService;
import cn.medcn.sys.service.SystemUserService;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 公告消息 Created by jianliang
 */

@Controller
@RequestMapping(value = "/csp/notify")
public class SysNotifyController extends BaseController {

    @Autowired
    private SysNotifyService sysNotifyService;

    @Autowired
    private SystemUserService cspSysUserService;

    @Autowired
    private CspUserService cspUserService;

    /**
     * list页面
     *
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name = "list页面")
    public String list(Pageable pageable, Model model) {
        MyPage<SystemNotify> myPage = sysNotifyService.findMessageListByPage(pageable);
        model.addAttribute("page", myPage);
        return "/notify/notifyList";
    }

    /**
     * 发布公告跳转
     *
     * @return
     */
    @RequestMapping(value = "/notifyInfo")
    @Log(name = "发布公告跳转")
    public String messageInfo() {
        return "/notify/notifyInfo";
    }

    /**
     * 发布公告
     *
     * @param notify
     * @param model
     * @return
     */
    @RequestMapping(value = "/insert")
    @Log(name = "发布公告")
    public String sendMessage(SystemNotify notify, Model model, String userName) {
        Integer userId = SubjectUtils.getCurrentUserid();
        SystemUser systemUser = cspSysUserService.selectByPrimaryKey(userId);
        if (notify != null) {
            if (userName != null) {
                CspUserInfo cspUserInfo = cspUserService.selectByUserName(userName);
                if (cspUserInfo!=null){
                    notify.setAcceptId(cspUserInfo.getId());
                }else{
                    model.addAttribute("err","不存在的用户");
                    return "/notify/notifyInfo";
                }
            }
            notify.setId(UUIDUtil.getNowStringID());
            notify.setSenderId(String.valueOf(userId));
            notify.setSenderName(systemUser.getUserName());
            notify.setSendTime(new Date());
            int insert = sysNotifyService.insert(notify);
            System.out.println(insert);
            return "redirect:/csp/notify/list";
        } else {
            return "/notify/notifyInfo";
        }

    }

    /**
     * 接受者校验
     * @param userName 接受者
     * @return
     */
    @RequestMapping(value = "/checkout")
    @ResponseBody
    @Log(name = "接受者校验")
    public CspUserInfo checkout(String userName){
        CspUserInfo cspUserInfo = cspUserService.selectByUserName(userName);
        return cspUserInfo;
    }

    /**
     * 编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    @Log(name = "编辑页面")
    public String notifyEdit(@RequestParam(value = "id", required = true) String id, Model model) {
        SystemNotify notify = sysNotifyService.selectByPrimaryKey(id);
        CspUserInfo cspUserInfo = cspUserService.selectByPrimaryKey(notify.getAcceptId());
        model.addAttribute("userName",cspUserInfo.getUserName());
        model.addAttribute("notify", notify);
        return "/notify/notifyInfoEdit";
    }

    /**
     * 修改页面
     * @param notify
     * @param userName 接收人
     * @return
     */
    @RequestMapping(value = "/update")
    @Log(name = "修改页面")
    public String update( SystemNotify notify,String userName) {
        if (notify != null){
            if (userName != null){
                CspUserInfo cspUserInfo = cspUserService.selectByUserName(userName);
                notify.setAcceptId(cspUserInfo.getId());
            }else {
                return "/notify/notifyInfoEdit";
            }
            notify.setSendTime(new Date());
            sysNotifyService.updateByPrimaryKey(notify);
            return "redirect:/csp/notify/list";
        }else{
            return "/notify/notifyInfoEdit";
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @Log(name = "删除")
    public String delete(@RequestParam(value = "id", required = true) String id) {
        sysNotifyService.deleteByPrimaryKey(id);
        return "redirect:/csp/notify/list";
    }
}
