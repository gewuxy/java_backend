package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.csp.admin.service.CspSysLogService;
import cn.medcn.csp.admin.model.CspSysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * by create HuangHuibin 2017/11/3
 */

@Controller
@RequestMapping(value="/csp/sys")
public class CspSysLogController extends BaseController {

   @Autowired
   private CspSysLogService cspSysLogService;


    @RequestMapping(value = "/log/list")
    public String cspSysUserSearch(Pageable pageable, String account, Model model) {
        if (!StringUtils.isEmpty(account)) {
            pageable.getParams().put("account", account);
            model.addAttribute("account",account);
        }
        MyPage<CspSysLog> page = cspSysLogService.findCspSysLog(pageable);
        model.addAttribute("page", page);
        return "/sys/logList";
    }
}
