package cn.medcn.csp.admin.controllor.yaya;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.user.model.ActiveCode;
import cn.medcn.user.service.ActiveCodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Liuchangling on 2018/1/20.
 * 激活码管理
 */
@Controller
@RequestMapping("/yaya/code")
public class ActiveCodeController extends BaseController{
    @Autowired
    protected ActiveCodeService activeCodeService;

    @RequestMapping("/list")
    public String activeCodeList(Pageable pageable, String nickname, Integer used, Model model) {
        if (CheckUtils.isNotEmpty(nickname)) {
            pageable.put("nickname", nickname);
            model.addAttribute("nickname", nickname);
        }
        if (used != null) {
            pageable.put("used", used);
            model.addAttribute("used", used);
        }

        MyPage<ActiveCode> page = activeCodeService.findActiveCodeList(pageable);
        model.addAttribute("page", page);
        return "/yaya/code/list";
    }


    @RequestMapping("/batch/create")
    @ResponseBody
    @RequiresPermissions("yaya:code:add")
    @Log(name = "批量生成激活码")
    public String createCode(Integer[] unitIds, Integer codeNum) {
        if (unitIds == null || unitIds.length == 0) {
            return error("请选择需要生成激活码的单位号");
        }
        if (codeNum == null || codeNum == 0) {
            return error("请输入需要生成的激活码数量");
        }

        activeCodeService.doCreateActiveCode(unitIds, codeNum);
        return success();
    }
}
