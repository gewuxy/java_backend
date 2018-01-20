package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.user.model.ActiveCode;
import cn.medcn.user.service.ActiveCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String activeCodeList(Pageable pageable, String nickname, Integer used, Integer actived, Model model) {
        if (CheckUtils.isNotEmpty(nickname)) {
            pageable.put("nickname", nickname);
            model.addAttribute("nickname", nickname);
        }
        if (used != null) {
            pageable.put("used", used);
            model.addAttribute("used", used);
        }
        if (actived != null) {
            pageable.put("actived", actived);
            model.addAttribute("actived", actived);
        }

        MyPage<ActiveCode> page = activeCodeService.findActiveCodeList(pageable);
        model.addAttribute("page", page);
        return "/yaya/code/list";
    }


    @RequestMapping("/create")
    public String createCode() {

        return "/yaya/code/list";
    }
}
