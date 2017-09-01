package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by lixuan on 2017/5/5.
 */
@Controller
@RequestMapping(value="/sys/region")
public class RegionController extends BaseController {

    @Autowired
    private SystemRegionService systemRegionService;

    @RequestMapping(value="/list")
    public String list(Pageable pageable, Integer preid, Integer level, String keyword, Model model){
        if(preid != null){
            pageable.getParams().put("preId", preid);
        }
        if(level != null){
            pageable.getParams().put("level", level);
            model.addAttribute("level", level);
        }
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword",keyword);
            model.addAttribute("keyword", keyword);
        }
        MyPage<SystemRegion> page = systemRegionService.findByPage(pageable);
        model.addAttribute("page", page);

        return "/sys/regionList";
    }


    @RequestMapping(value="/edit")
    public String edit(Integer id, Integer preid, Model model){
        if(id != null){
            SystemRegion region = systemRegionService.selectByPrimaryKey(id);
            model.addAttribute("region", region);
        }
        if(preid!=null){
            SystemRegion parent = systemRegionService.selectByPrimaryKey(preid);
            SystemRegion region = new SystemRegion();
            region.setPreId(preid);
            region.setLevel(parent.getLevel()+1);
            model.addAttribute("region", region);
            model.addAttribute("parent", parent);
        }
        return "/sys/regionForm";
    }


    @RequestMapping(value="/save")
    public String save(SystemRegion region, RedirectAttributes redirectAttributes){
        systemRegionService.updateByPrimaryKeySelective(region);
        addFlashMessage(redirectAttributes, "操作成功");
        return "redirect:/sys/region/list";
    }


    @RequestMapping(value="/treeData")
    @ResponseBody
    public String treeData(Integer preid){
        if(preid == null){
            preid = 0;
        }
        List<SystemRegion> regions = systemRegionService.findRegionByPreid(preid);
        return APIUtils.success(regions);
    }


    @RequestMapping(value="/tree")
    public String tree(Integer preid, Model model){
        if(preid == null){
            preid = 0;
        }
        List<SystemRegion> regions = systemRegionService.findRegionByPreid(preid);
        model.addAttribute("regions",regions);
        return "/sys/regionTree";
    }
}
