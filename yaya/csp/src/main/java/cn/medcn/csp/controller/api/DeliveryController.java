package cn.medcn.csp.controller.api;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.model.CourseDelivery;
import cn.medcn.meet.service.CourseDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixuan on 2017/9/26.
 */
@Controller
@RequestMapping(value = "/api/delivery")
public class DeliveryController extends BaseController {

    @Autowired
    protected CourseDeliveryService courseDeliveryService;

    @RequestMapping(value = "/paginate")
    @ResponseBody
    public String paginate(Pageable pageable, Integer accept_id){
        if (accept_id != null) {
            pageable.put("acceptId", accept_id);
        }
        pageable.put("authorId", SecurityUtils.get().getId());
        MyPage<CourseDelivery> page = courseDeliveryService.page(pageable);
        return success(page.getDataList());
    }


    @RequestMapping(value = "/push")
    @ResponseBody
    public String push(Integer courseId, Integer[] acceptIds){

        return success();
    }
}
