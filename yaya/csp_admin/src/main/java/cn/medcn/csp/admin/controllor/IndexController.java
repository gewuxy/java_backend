package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lixuan on 2017/11/2.
 */
@Controller
public class IndexController extends BaseController {


    @RequestMapping(value = "/")
    public String index(){
        return "/login";
    }


    @RequestMapping(value = "/example/table")
    public String table(){

        return "/example/table";
    }

    @RequestMapping(value = "/example/form")
    public String form(){

        return "/example/form";
    }

    @RequestMapping(value = "/user/statistics")
    public String statistic(){
        return "/statistics/register";
    }


}
