package cn.medcn.api.wexin;

import cn.medcn.api.wexin.security.Principal;
import cn.medcn.api.wexin.security.SecurityUtils;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CookieUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.goods.dto.TradeDetailDTO;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.model.AppDoctor;
import cn.medcn.user.model.AppRole;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.pay.WXPayUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lixuan on 2017/7/25.
 */
@Controller
@RequestMapping(value = "/weixin/user")
public class UserInfoController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @Value("${app.file.base}")
    protected String appFileBase;

    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected CreditsService creditsService;

    @Value("${WeChat.Server.app_id}")
    protected String appId;

    /**
     * 用户个人中心
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/info")
    public String info(HttpServletRequest request, Model model) {
        String unionId = CookieUtils.getCookieValue(request, WeixinConfig.COOKIE_NAME_UNION_ID);
        AppUser appUser = appUserService.findUserByUnoinId(unionId);
        model.addAttribute("appFileBase", appFileBase);
        model.addAttribute("appUser", appUser);
        AppDoctor userDetail = (AppDoctor) appUserService.findUserDetail(appUser.getId(), appUser.getRoleId() == null ? AppRole.AppRoleType.DOCTOR.getId() : appUser.getRoleId());
        model.addAttribute("userDetail", userDetail);
        return "/weixin/userInfo";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modify(Model model) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        AppUser appUser = appUserService.selectByPrimaryKey(principal.getId());
        AppDoctor detail = (AppDoctor) appUserService.findUserDetail(principal.getId(), principal.getRoleId());
        appUser.setUserDetail(detail);
        AppUserDTO appUserDTO = AppUserDTO.buildFromDoctor(appUser);
        model.addAttribute("appFileBase", appFileBase);
        model.addAttribute("appUser", appUserDTO);
        return "/weixin/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(AppUserDTO appUserDTO) {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        AppUser appUser = AppUserDTO.rebuildToDoctor(appUserDTO);
        appUser.setId(principal.getId());
        try {
            appUserService.updateDoctor(appUser);
        } catch (Exception e) {
            e.printStackTrace();
            return error("修改用户信息失败");
        }
        //重新计算完整度
        AppUser user = appUserService.selectByPrimaryKey(principal.getId());
        AppDoctor detail = (AppDoctor) appUserService.findUserDetail(principal.getId(), principal.getRoleId());
        user.setUserDetail(detail);
        AppUserDTO dto = AppUserDTO.buildFromDoctor(user);
        return success(dto.getIntegrity());
    }

    /**
     * 修改用户头像
     *
     * @return
     */
    @RequestMapping(value = "/modify/head", method = RequestMethod.POST)
    @ResponseBody
    public String uploadHead(@RequestParam(value = "file", required = false) MultipartFile file) {
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, FilePath.PORTRAIT.path);
        } catch (SystemException e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        //更新用户头像
        AppUser user = new AppUser();
        user.setId(SecurityUtils.getCurrentUserInfo().getId());
        user.setHeadimg(result.getRelativePath());
        appUserService.updateByPrimaryKeySelective(user);
        return success(result);
    }


    @RequestMapping(value = "/credits")
    public String credits(Model model){
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Credits credits = creditsService.doFindMyCredits(principal.getId());
        model.addAttribute("credits", credits);
        return "/weixin/credits/credits";
    }



    @RequestMapping(value = "/credits/details")
    public String details(Pageable pageable, Model model){
        Principal principal = SecurityUtils.getCurrentUserInfo();
        pageable.put("userId", principal.getId());
        MyPage<TradeDetailDTO> page = creditsService.findTradeInfo(pageable);
        model.addAttribute("page", page);
        return "/weixin/credits/details";
    }


    @RequestMapping(value = "/credits/details/page")
    @ResponseBody
    public String details(Pageable pageable){
        Principal principal = SecurityUtils.getCurrentUserInfo();
        pageable.put("userId", principal.getId());
        MyPage<TradeDetailDTO> page = creditsService.findTradeInfo(pageable);

        return success(page.getDataList());
    }



}
