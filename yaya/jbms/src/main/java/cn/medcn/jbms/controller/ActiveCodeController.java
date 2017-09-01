package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.user.model.ActiveCode;
import cn.medcn.user.service.ActiveCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lixuan on 2017/5/5.
 */
@Controller
@RequestMapping(value="/custom/code")
public class ActiveCodeController extends BaseController {

    @Autowired
    private ActiveCodeService activeCodeService;

    @RequestMapping(value="/list")
    public String list(Pageable pageable, Boolean used, String code, Model model){
        if(used != null){
            pageable.getParams().put("used", used);
            model.addAttribute("used", used?"1":"0");
        }
        if(!StringUtils.isEmpty(code)){
            pageable.getParams().put("code", code);
            model.addAttribute("code", code);
        }
        MyPage<ActiveCode> page = activeCodeService.page(pageable);
        model.addAttribute("page", page);
        return "/custom/codeList";
    }
}
