package cn.medcn.jcms.controller.manage;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.goods.dto.TradeDetailDTO;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuLP on 2017/5/16/016.
 */
@Controller
@RequestMapping("mng/account")
public class AccountController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CreditsService creditsService;

    @Value("${app.file.base}")
    private String appFileBase;

    /**
     * 账号设置页面
     * @param model
     * @return
     */
    @RequestMapping("/info")
    public String info(Model model){
        Integer userId = SubjectUtils.getCurrentUserid();
        AppUser appUser = appUserService.selectByPrimaryKey(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("id",appUser.getId());
        map.put("headimg", StringUtils.isEmpty(appUser.getHeadimg())?null:appFileBase+appUser.getHeadimg());
        map.put("linkman",appUser.getNickname());
        map.put("address",appUser.getAddress());
        map.put("sign",appUser.getSign());
        model.addAttribute("user",map);
        return "/manage/account";
    }

    /**
     * 修改用户简介信息
     * @return
     */
    @RequestMapping(value="/modifySign",method = RequestMethod.POST)
    @ResponseBody
    public String modifySign(String sign){
        Integer userId = SubjectUtils.getCurrentUserid();
        AppUser user = appUserService.selectByPrimaryKey(userId);
        user.setSign(sign);
        appUserService.updateByPrimaryKeySelective(user);
        return APIUtils.success();
    }

    /**
     * 修改用户头像
     * @param headimg
     * @return
     */
    @RequestMapping("/modifyHeadimg")
    @ResponseBody
    public String modifyHeadimg(String headimg){
        Integer userId = SubjectUtils.getCurrentUserid();
        AppUser user = appUserService.selectByPrimaryKey(userId);
        user.setHeadimg(headimg);
        appUserService.updateByPrimaryKeySelective(user);
        return APIUtils.success();
    }


    /**
     * 修改登录密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("/modifyPwd")
    @ResponseBody
    public String modifyPwd(String oldPassword,String newPassword){
        Integer userId = SubjectUtils.getCurrentUserid();
        AppUser user = appUserService.selectByPrimaryKey(userId);
        if(!user.getPassword().equals(MD5Utils.MD5Encode(oldPassword))){
            return APIUtils.error("您输入的旧密码不正确");
        }
        user.setPassword(MD5Utils.MD5Encode(newPassword));
        appUserService.updateByPrimaryKeySelective(user);
        return APIUtils.success();
    }

    /**
     * 象数管理页面
     * @param model
     * @return
     */
    @RequestMapping("/xsInfo")
    public String xsInfo(Model model){
        Integer userId = SubjectUtils.getCurrentUserid();
            //象数值
        Credits credits = creditsService.doFindMyCredits(userId);
        model.addAttribute("credit",credits);
        //象数交易历史
        Pageable pageable = new Pageable(1,4);
        MyPage<TradeDetailDTO> myPage = getTradeInfo(pageable);
        model.addAttribute("history",myPage);
        model.addAttribute("userId",userId);
        return "/manage/xsInfo";
    }

    @RequestMapping("/history")
    @ResponseBody
    public MyPage<TradeDetailDTO>  getTradeInfo(Pageable pageable){
        pageable.getParams().put("userId",SubjectUtils.getCurrentUserid());
        MyPage<TradeDetailDTO> myPage = creditsService.findTradeInfo(pageable);
        return myPage;

    }





}
