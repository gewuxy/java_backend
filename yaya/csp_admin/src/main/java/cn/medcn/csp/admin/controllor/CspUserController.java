package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.Constants;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
   private CspUserService cspUsersService;

   @Autowired
   protected CspUserPackageService cspUserPackageService;

   @Autowired
   private SystemRegionService systemRegionService;

    @RequestMapping(value = "/list")
    @Log(name = "csp用户列表")
    public String cspUserSearch(Pageable pageable, @RequestParam(required = false, defaultValue = "0")Integer listType, String keyWord, Model model) {
        if (!StringUtils.isEmpty(keyWord)) {
            pageable.put("keyWord", keyWord);
            model.addAttribute("keyWord",keyWord);
        }
        pageable.put("active",1);
        if(listType == null || listType == 0){  //国内
            pageable.put("abroad",0);
        }else if( listType == 1){  // 海外
           pageable.put("abroad",1);
        }else{  //封号=未激活
            pageable.put("active",0);
        }
        model.addAttribute("listType",listType);
        MyPage<CspUserInfoDTO> page = cspUsersService.findCspUserList(pageable);
        model.addAttribute("page", page);
        return listType == 2 ? "/user/frozenList":"/user/cspUserList";
    }

    /**
     * 跳转到查看或者修改页面
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewOrRegister")
    @Log(name="查看csp用户信息")
    public String viewOrRegister(Integer actionType,Integer listType,CspUserInfo user,Model model) {
        Pageable pageable = new Pageable();
        pageable.setPageSize(50);
        pageable.put("level",1);
        MyPage<SystemRegion> page = systemRegionService.findByPage(pageable);
        model.addAttribute("province", page);
        model.addAttribute("listType", listType);
        model.addAttribute("actionType",actionType);
        if(actionType == 3){ //更新或者查看
            CspUserInfo userInfo = cspUsersService.selectByPrimaryKey(user);
            model.addAttribute("user",userInfo);
            String province = userInfo.getProvince();
            if(!StringUtils.isEmpty(province)){
                List<SystemRegion> citys = systemRegionService.findRegionByPreName(province);
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
    @Log(name="更新csp用户信息")
    public String stopOrActive(CspUserInfo user,Integer actionType,Integer listType,Integer isReset, RedirectAttributes redirectAttributes) {
        if(actionType == 1){ //停用
            user.setActive(false);
            cspUsersService.updateByPrimaryKeySelective(user);
        }else if(actionType == 2){   //删除
            cspUsersService.deleteByPrimaryKey(user);
        }else{   //修改用户信息
            if(isReset == 1){   //密码重置
                user.setPassword(MD5Utils.MD5Encode(Constants.RESET_PASSWORD));
            }
            cspUsersService.updateByPrimaryKeySelective(user);
        }
        addFlashMessage(redirectAttributes, actionType == 1 || actionType == 3?"更新成功": "删除成功");
        StringBuffer buffer = new StringBuffer("redirect:/csp/user/list");
        if (listType != null) {
            buffer.append("?listType=").append(listType);
        }
        return buffer.toString();
    }

    @RequestMapping(value = "/package")
    @Log(name="更新套餐信息")
    public String packages(CspUserPackage packageInfo, String updateTimes,Integer actionType, Integer listType, RedirectAttributes redirectAttributes) {
        System.out.println(updateTimes);
        cspUserPackageService.updateByPrimaryKey(packageInfo);
        addFlashMessage(redirectAttributes, "更新成功");
        StringBuffer buffer = new StringBuffer("redirect:/csp/user/list");
        if (listType != null) {
            buffer.append("?listType=").append(listType);
        }
        return buffer.toString();
    }

    /**
     * 根据上级名称获取到子级列表
     * @param name
     * @return
     */
    @RequestMapping(value="/searchOption")
    @ResponseBody
    public String searchOption(String name){
        if(name == null){
            return success();
        }
        List<SystemRegion> options = systemRegionService.findRegionByPreName(name);
        return success(options);
    }

}
