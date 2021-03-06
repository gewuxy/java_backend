package cn.medcn.csp.admin.controllor;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.meet.service.AudioService;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.*;
import cn.medcn.user.service.CspPackageService;
import cn.medcn.user.service.CspUserPackageHistoryService;
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

   @Autowired
   protected CspUserPackageHistoryService cspUserPackageHistoryService;

   @Autowired
   protected CspUserService cspUserService;

   @Autowired
   protected CspPackageService cspPackageService;

   @Autowired
   protected RedisCacheUtils redisCacheUtils;

   @Autowired
   protected AudioService audioService;

    @RequestMapping(value = "/list")
    @Log(name = "csp用户列表")
    public String cspUserSearch(Pageable pageable, @RequestParam(required = false, defaultValue = "0")Integer listType, String keyWord, Model model) {
        if (!StringUtils.isEmpty(keyWord)) {
            pageable.put("keyWord", keyWord);
            model.addAttribute("keyWord",keyWord);
        }

        if(listType == null || listType == 0){  //国内
            pageable.put("abroad",0);
        }else if( listType == 1){  // 海外
           pageable.put("abroad",1);
        }else{  // 冻结用户列表
            pageable.put("frozen",1);
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
                if(userInfo.getCity() != null){
                    List<SystemRegion> districts = systemRegionService.findRegionByPreName(userInfo.getCity());
                    model.addAttribute("district", districts);
                }
            }
        }
        return "/user/userInfo";
    }

//    /**
//     * 删除、禁用、更新、注册用户
//     * @param actionType
//     * @param redirectAttributes
//     * @return
//     */
//    @RequestMapping(value = "/update")
//    @Log(name="更新csp用户信息")
//    public String stopOrActive(CspUserInfo user,Integer actionType,Integer listType,Integer isReset, RedirectAttributes redirectAttributes) {
//        if(actionType == Constants.NUMBER_ONE){ //停用
//            user.setActive(false);
//            cspUsersService.updateByPrimaryKeySelective(user);
//        }else if(actionType == Constants.NUMBER_TWO){   //删除
//            cspUsersService.deleteByPrimaryKey(user);
//        }else{   //修改用户信息
//            if(isReset == Constants.NUMBER_ONE){   //密码重置
//                user.setPassword(MD5Utils.MD5Encode(Constants.RESET_PASSWORD));
//            }
//            cspUsersService.updateByPrimaryKeySelective(user);
//        }
//        addFlashMessage(redirectAttributes, actionType == 1 || actionType == 3?"更新成功": "删除成功");
//        StringBuffer buffer = new StringBuffer("redirect:/csp/user/list");
//        if (listType != null) {
//            buffer.append("?listType=").append(listType);
//        }
//        return buffer.toString();
//    }

    @RequestMapping(value = "/package")
    @Log(name="更新套餐信息")
    public String packages(CspUserPackage packageInfo, Integer listType,String frozenReason,Integer actionType, RedirectAttributes redirectAttributes) {
        if(actionType == 4 || actionType == 5){ // 冻结或者解冻
            CspUserInfo user = new CspUserInfo();
            user.setId(packageInfo.getUserId());
            user.setUpdateTime(new Date());
            if(actionType == 4){  //冻结
                user.setFrozenReason(frozenReason);
                user.setFrozenState(true);
            }else{  //解冻
                user.setFrozenReason("");
                user.setFrozenState(false);
            }
            cspUserService.updateByPrimaryKeySelective(user);
        }else {  //升级降级修改时间
            Integer currentId = packageInfo.getPackageId();
            // 更新开始时间
            packageInfo.setPackageStart(CalendarUtils.nextDateStartTime());
            packageInfo.setUnlimited(false);
            if (currentId == Constants.NUMBER_ONE) { //标准版
                packageInfo.setPackageStart(null);
                packageInfo.setPackageEnd(null);
                packageInfo.setUnlimited(true);
            }
            packageInfo.setUpdateTime(new Date());
            packageInfo.setSourceType(Constants.NUMBER_TWO);
            CspUserPackage oldPackage = cspUserPackageService.selectByPrimaryKey(packageInfo.getUserId());
            Integer oldpackageId = null;
            if (oldPackage == null) {   // 未有套餐信息
                cspUserPackageService.insertSelective(packageInfo);
            } else {
                //更新套餐版本信息
                cspUserPackageService.updateByPrimaryKey(packageInfo);
                oldpackageId = oldPackage.getPackageId();
            }
            //更新会议状态
            audioService.doModifyAudioCourseByPackageId(packageInfo.getUserId(), currentId);
            //添加版本历史
            cspUserPackageHistoryService.addUserHistoryInfo(packageInfo.getUserId(), oldpackageId, packageInfo.getPackageId(), Constants.NUMBER_TWO);
        }
        //更新缓存信息
        cspUserService.updatePackagePrincipal(packageInfo.getUserId());
        addFlashMessage(redirectAttributes, "更新成功");
        StringBuffer buffer = new StringBuffer("redirect:/csp/user/list");
        if (listType != null) {
            buffer.append("?listType=").append(listType);
        }
        return buffer.toString();
    }

    @RequestMapping(value = "/remark")
    @ResponseBody
    @Log(name="修改csp备注")
    public String remark(CspUserInfo info) {
        Integer result = cspUserService.updateByPrimaryKeySelective(info);
        if(result == 0) {
            return error();
        }
        return success();
    }

    @RequestMapping(value = "/bind/view")
    @ResponseBody
    @Log(name="查询用户绑定信息")
    public String bindView(String userId) {
        List<BindInfo> bindInfoList = cspUserService.findBindListByUserId(userId);
        return success(bindInfoList);
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
