package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lixuan on 2017/5/23.
 */
@RequestMapping(value="/region")
@Controller
public class RegionController extends BaseController {

    @Autowired
    private SystemRegionService systemRegionService;

    @RequestMapping(value ="/list")
    @ResponseBody
    public String list(Integer preId){
        if(preId == null){
            preId = 0;
        }
        List<SystemRegion> list = systemRegionService.findRegionByPreid(preId);
        return APIUtils.success(list);
    }
}
