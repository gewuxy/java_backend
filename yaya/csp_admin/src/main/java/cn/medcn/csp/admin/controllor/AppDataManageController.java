package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @Author：jianliang
 * @Date: Create in 15:29 2017/11/20
 */
@Controller
@RequestMapping("/csp/data")
public class AppDataManageController extends BaseController{

    @Autowired
    private AppUserService appUserService;

    /**
     * 数据统计详情
     * @return
     */
    @RequestMapping("/list")
    @Log(name = "数据统计详情")
    public String dataList(){
        return "/dataCount/dataManageList";
    }

    /**
     * 查询年份数据
     * @param registDate 注册时间
     * @param model
     * @return
     */
    @RequestMapping(value = "/select")
    @ResponseBody
    @Log(name = "查询年份数据")
    public String selectData(String registDate, Model model){
        if(!StringUtils.isEmpty(registDate)){
            model.addAttribute("registDate",registDate);
        }
        List<AppUser> appUsers= appUserService.selectByRegistDate(registDate);
        String jsonObject = JSON.toJSONString(appUsers);
        return jsonObject;
    }

}
