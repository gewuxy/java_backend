package cn.medcn.csp.controller.web;

import cn.medcn.csp.controller.CspBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LiuLP on 2017/10/17.
 */
@Controller
@RequestMapping("/mgr")
public class MessageController extends CspBaseController {

    @RequestMapping(value = "/")
    public String index(){
        return localeView("/login/login");
    }
}
