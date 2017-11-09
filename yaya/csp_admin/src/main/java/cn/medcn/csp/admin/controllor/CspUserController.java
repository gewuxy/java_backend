package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.csp.admin.model.CspUserInfo;
import cn.medcn.csp.admin.service.CspUserService;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

/**
 * by create HuangHuibin 2017/11/3
 */
@Controller
@RequestMapping(value="/csp/user")
public class CspUserController extends BaseController {

   @Autowired
   private CspUserService cspUserService;

   @Autowired
   private SystemRegionService systemRegionService;

    /**
     * 获取csp账户列表
     * @param pageable
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String cspUserSearch(Pageable pageable, String userName, Model model) {
        if (!StringUtils.isEmpty(userName)) {
            pageable.getParams().put("userName", userName);
            model.addAttribute("userName",userName);
        }
        MyPage<CspUserInfo> page = cspUserService.findCspUserList(pageable);
        model.addAttribute("page", page);
        return "/user/cspUserList";
    }

    /**
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewOrRegister")
    public String viewOrRegister(Integer actionType,CspUserInfo user,Model model) {
        Pageable pageable = new Pageable();
        pageable.setPageSize(50);
        pageable.put("level",1);
        MyPage<SystemRegion> page = systemRegionService.findByPage(pageable);
        model.addAttribute("province", page);
        model.addAttribute("actionType",actionType);
        if(actionType == 3){ //更新或者查看
            CspUserInfo userInfo = cspUserService.selectByPrimaryKey(user);
            model.addAttribute("user",userInfo);
            String province = userInfo.getProvince();
            if(province != null){
                List<SystemRegion> citys = systemRegionService.findRegionByPreName(userInfo.getProvince());
                model.addAttribute("city", citys);
                if(!province.equals("北京") || !province.equals("天津")){   //此此省份没有2级
                    List<SystemRegion> districts = systemRegionService.findRegionByPreName(userInfo.getCity());
                    model.addAttribute("district", districts);
                }
            }
        }
        return "/user/userInfo";
    }


    /**
     * 删除、禁用、更新、注册用户
     * @param actionType
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/update")
    public String stopOrActive(CspUserInfo user,Integer actionType,Integer isReset, RedirectAttributes redirectAttributes) {
        if(actionType == 1){ //停用
            user.setActive(false);
            cspUserService.updateByPrimaryKeySelective(user);
        }else if(actionType == 2){   //删除
            cspUserService.deleteByPrimaryKey(user);
        }else if(actionType == 3){   //修改用户信息
            if(isReset == 1){   //密码重置
                user.setPassword(MD5Utils.MD5Encode("111111"));
            }
            cspUserService.updateByPrimaryKeySelective(user);
        }else{  //注册新用户
            user.setId(null);   //
            user.setRegisterTime(new Date());
            cspUserService.insertSelective(user);
        }
        addFlashMessage(redirectAttributes, actionType == 1 || actionType == 3?"更新成功": actionType == 2?"删除成功":"注册成功");
        return "redirect:/csp/user/list";
    }

    /**
     * 获取到子级列表
     * @param name
     * @return
     */
    @RequestMapping(value="/searchOption")
    @ResponseBody
    public String searchOption(String name){
        if(name == null){
            return APIUtils.success();
        }
        List<SystemRegion> options = systemRegionService.findRegionByPreName(name);
        return APIUtils.success(options);
    }

}
