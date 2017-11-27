package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.sys.model.SystemLog;
import cn.medcn.sys.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * by create HuangHuibin 2017/11/23
 */
@Controller
@RequestMapping(value="/sys/logs")
public class LogController extends BaseController{

    @Autowired
    private SystemLogService systemLogService;

    /**
     * 获取日志列表
     * @param pageable
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name="查看日志")
    public String cspSysUserSearch(Pageable pageable, String userName, Model model) {
        if (!StringUtils.isEmpty(userName)) {
            pageable.getParams().put("userName", userName);
            model.addAttribute("userName",userName);
        }
        MyPage<SystemLog> page = systemLogService.findLogByPage(pageable);
        model.addAttribute("page", page);
        return "/sys/logList";
    }
}
