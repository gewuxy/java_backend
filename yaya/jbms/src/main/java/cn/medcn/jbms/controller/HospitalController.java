package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.dto.HospitalLevelDTO;
import cn.medcn.user.model.Hospital;
import cn.medcn.user.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lixuan on 2017/5/8.
 */
@Controller
@RequestMapping(value="/sys/hospital")
public class HospitalController extends BaseController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private SystemRegionService systemRegionService;


    @RequestMapping(value="/list")
    public String list(Pageable pageable,String level, String province,String city,Integer preid, String keyword, Model model){
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        if(!StringUtils.isEmpty(level)){
            pageable.getParams().put("level", level);
            model.addAttribute("level", level);
        }
        if(!StringUtils.isEmpty(province)){
            pageable.getParams().put("province", province);
            model.addAttribute("province", province);
        }
        if(preid != null){
            List<SystemRegion> cities = systemRegionService.findRegionByPreid(preid);
            model.addAttribute("preid", preid);
            model.addAttribute("cities", cities);
        }
        if(!StringUtils.isEmpty(city)){
            pageable.getParams().put("city", city);
            model.addAttribute("city", city);
        }
        List<SystemRegion> provinces = systemRegionService.findRegionByPreid(0);
        model.addAttribute("provinces", provinces);

        List<HospitalLevelDTO> levels = hospitalService.findAllLevels();
        model.addAttribute("levels", levels);

        MyPage<Hospital> page = hospitalService.findByPage(pageable);
        model.addAttribute("page", page);
        return "/sys/hospitalList";
    }


    @RequestMapping(value="/cities")
    @ResponseBody
    public String cities(Integer preid){
        if(preid == null){
            return APIUtils.success();
        }
        List<SystemRegion> cities = systemRegionService.findRegionByPreid(preid);
        return APIUtils.success(cities);
    }

}
