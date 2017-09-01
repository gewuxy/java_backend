package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.user.model.Department;
import cn.medcn.user.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lixuan on 2017/5/9.
 */
@Controller
@RequestMapping(value="/sys/depart")
public class DepartController extends BaseController {

    @Autowired
    private HospitalService hospitalService;

    @RequestMapping(value="/list")
    public String list(String category, Model model){
        List<String> categoryList = hospitalService.findDepartCategory();
        model.addAttribute("categoryList", categoryList);
        List<Department> list = null;
        if(!StringUtils.isEmpty(category)){
            list = hospitalService.findDepartByCondition(category);
            model.addAttribute("category", category);
        }else{
            list = hospitalService.findAllDepart();
        }
        model.addAttribute("list", list);
        return "/sys/departList";
    }
}
