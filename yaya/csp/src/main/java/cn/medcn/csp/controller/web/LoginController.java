package cn.medcn.csp.controller.web;

import cn.medcn.common.ctrl.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lixuan on 2017/10/16.
 */
@Controller
@RequestMapping(value = "/web")
public class LoginController extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Integer thirdPartyId){
        if (thirdPartyId == null || thirdPartyId == 0) {
            return localeView("/login/login");
        } else {
            return localeView("/login/login_"+thirdPartyId);
        }
    }
}
