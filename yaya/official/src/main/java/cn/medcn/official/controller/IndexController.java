package cn.medcn.official.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * by create HuangHuibin 2017/11/14
 */
@Controller
public class IndexController {

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String jcms(){
        return "index";
    }

    @RequestMapping(value="/user/userInfo")
    public String test(){
        return "/user/userInfo";
    }
}
