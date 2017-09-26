package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixuan on 2017/9/26.
 */
@Controller
@RequestMapping(value = "/func/meet/delivery")
public class DeliveryController extends BaseController {

    @RequestMapping(value = "/accept")
    @ResponseBody
    public String accept(){

        return success();
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String users(Pageable pageable){

        return success();
    }
}
