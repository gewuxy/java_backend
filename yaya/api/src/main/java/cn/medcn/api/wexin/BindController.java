package cn.medcn.api.wexin;

import cn.medcn.api.wexin.security.Principal;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.supports.Validate;
import cn.medcn.common.utils.*;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.dto.OAuthDTO;
import cn.medcn.weixin.model.WXUserInfo;
import cn.medcn.weixin.service.WXOauthService;
import cn.medcn.weixin.service.WXUserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lixuan on 2017/7/25.
 */
@Controller
@RequestMapping(value = "/weixin")
public class BindController extends BaseController {

    private final static Log log = LogFactory.getLog(BindController.class);

    @Autowired
    private WXOauthService wxOauthService;

    @Autowired
    private WXUserInfoService wxUserInfoService;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private JSmsService jSmsService;

    @Value("${app.file.upload.base}")
    protected String appFileUploadBase;

    @Value("${app.ios.download.url}")
    protected String iosDownloadUrl;

    @Value("${app.and.download.url}")
    protected String andDownloadUrl;

    @RequestMapping(value = "/bind", method = RequestMethod.GET)
    public String bind(String scene_id, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.setCookie(response, WeixinConfig.SCENE_ID_KEY, scene_id);
        //String unionId = CookieUtils.getCookieValue(request, WeixinConfig.COOKIE_NAME_UNION_ID);
        CookieUtils.clearCookie(request, WeixinConfig.COOKIE_NAME_OPEN_ID);
        CookieUtils.clearCookie(request, WeixinConfig.COOKIE_NAME_UNION_ID);
        //如果已经绑定 直接跳转到个人中心
        return "/weixin/bind";
    }


    protected void cacheUserInfo(AppUser appUser){
        Principal principal = Principal.build(appUser);
        redisCacheUtils.setCacheObject(Constants.WX_TOKEN_KEY_SUFFIX+appUser.getUnionid(), principal, Constants.TOKEN_EXPIRE_TIME);
    }

    /**
     * 认证 并判定用户是否已经绑定微信
     *
     * @param request
     * @param response
     * @param code
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/oauth")
    public String oauth(HttpServletRequest request, HttpServletResponse response, String code) throws SystemException {
        if (StringUtils.isEmpty(code)) {
            throw new SystemException("非法操作");
        }
        OAuthDTO oAuthDTO = wxOauthService.serverOauth(code);
        if (oAuthDTO != null) {
            CookieUtils.setCookie(response, WeixinConfig.COOKIE_NAME_OPEN_ID, oAuthDTO.getOpenid());
            CookieUtils.setCookie(response, WeixinConfig.COOKIE_NAME_UNION_ID, oAuthDTO.getUnionid());
            CookieUtils.setCookie(response, WeixinConfig.SCENE_ID_KEY, request.getParameter(WeixinConfig.SCENE_ID_KEY));
        }
        String historyRedirectUrl = CookieUtils.getCookieValue(request, WeixinConfig.REDIRECT_HISTORY);
        if (historyRedirectUrl != null && historyRedirectUrl.endsWith("/bind")) {
            //如果已经绑定 直接跳转到个人中心
            AppUser appUser = appUserService.findUserByUnoinId(oAuthDTO.getUnionid());
            if (appUser != null) {
                return "redirect:/weixin/user/info";
            }
        }
        return "redirect:" + historyRedirectUrl;
    }

    /***
     * 用户输入用户名和密码 执行绑定操作
     * @param request
     * @param username
     * @param password
     * @param model
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public String bind(HttpServletRequest request, String username, String password, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        if (CheckUtils.isEmpty(username)) {
            model.addAttribute(APIUtils.MESSAGE_KEY, "用户名不能为空");
            return "/weixin/bind";
        }
        if (CheckUtils.isEmpty(password)) {
            model.addAttribute(APIUtils.MESSAGE_KEY, "密码不能为空");
            return "/weixin/bind";
        }
        AppUser appUser = appUserService.findAppUserByLoginName(username);
        if (appUser == null) {
            model.addAttribute(APIUtils.MESSAGE_KEY, "用户不存在");
            return "/weixin/bind";
        }
        if (!appUser.getPassword().equals(MD5Utils.MD5Encode(password))){
            model.addAttribute(APIUtils.MESSAGE_KEY, "密码不正确");
            return "/weixin/bind";
        }
        String openId = CookieUtils.getCookieValue(request, WeixinConfig.COOKIE_NAME_OPEN_ID);
        String sceneId = CookieUtils.getCookieValue(request, WeixinConfig.SCENE_ID_KEY);
        WXUserInfo wxUserInfo = null;
        try {
            wxUserInfo = wxOauthService.getWXUserInfo(openId);
        } catch (SystemException e) {
            model.addAttribute(APIUtils.MESSAGE_KEY, e.getMessage());
            return "/weixin/bind";
        }
        appUser.setUnionid(wxUserInfo.getUnionid());
        appUser.setHeadimg(copyHead(wxUserInfo.getHeadimgurl()));
        appUser.setWxUserInfo(wxUserInfo);
        appUserService.doBindUserAndAttention(appUser, CheckUtils.isEmpty(sceneId) ? null : Integer.parseInt(sceneId));
        //cacheUserInfo(appUser);
        clearSceneCookie(request);
        addFlashMessage(redirectAttributes, "绑定YaYa医师账号成功");
        return "redirect:/weixin/user/info";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, Model model) throws SystemException {
        String sceneId = CookieUtils.getCookieValue(request, WeixinConfig.SCENE_ID_KEY);
        if (!CheckUtils.isEmpty(sceneId)) {
            AppUser unitUser = appUserService.selectByPrimaryKey(Integer.valueOf(sceneId));
            model.addAttribute("pubUserName", unitUser == null ? null : unitUser.getNickname());
            model.addAttribute("masterId", unitUser == null ? null : unitUser.getId());
        }
        return "/weixin/register";
    }


    private String registerError(Model model, AppUserDTO appUserDTO, HttpServletRequest request, String error) {
        String captchaValue = request.getParameter("captcha");
        String invite = request.getParameter("invite");
        model.addAttribute("captcha", captchaValue);
        model.addAttribute("invite", invite);
        model.addAttribute(APIUtils.MESSAGE_KEY, error);
        model.addAttribute("appUser", appUserDTO);
        String sceneId = CookieUtils.getCookieValue(request, WeixinConfig.SCENE_ID_KEY);
        if (!CheckUtils.isEmpty(sceneId)) {
            AppUser unitUser = appUserService.selectByPrimaryKey(Integer.valueOf(sceneId));
            model.addAttribute("pubUserName", unitUser == null ? null : unitUser.getNickname());
            model.addAttribute("masterId", unitUser == null ? null : unitUser.getId());
        }
        return "/weixin/register";
    }

    protected String copyHead(String wxHeadUrl){
        String fileName = UUIDUtil.getNowStringID()+"."+ FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix;
        String returnFileName = FilePath.PORTRAIT.path+ File.separator+fileName;
        HttpUtils.copyFromNetwork(wxHeadUrl, appFileUploadBase+ returnFileName);
        return returnFileName;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Validated AppUserDTO appUserDTO, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        try {
            resolveValidateResult(bindingResult);
        } catch (SystemException e) {
            return registerError(model, appUserDTO, request, e.getMessage());
        }
        AppUser existedUser = appUserService.findAppUserByLoginName(appUserDTO.getUsername());
        if (existedUser != null) {
            return registerError(model, appUserDTO, request, "该用户已经存在");
        }
        appUserDTO.setNickname(appUserDTO.getLinkman());
        String captchaValue = request.getParameter("captcha");
        String invite = request.getParameter("invite");
        if (CheckUtils.isEmpty(invite)) {
            return registerError(model, appUserDTO, request, "激活码不能为空");
        }
        String sceneId = CookieUtils.getCookieValue(request, WeixinConfig.SCENE_ID_KEY);
        AppUser appUser = AppUserDTO.rebuildToDoctor(appUserDTO);
        if (!checkCaptcha(appUserDTO.getMobile(), captchaValue)) {
            return registerError(model, appUserDTO, request, "验证码不正确");
        }
        Integer[] masterIds = null;
        if (!CheckUtils.isEmpty(sceneId)) {
            masterIds = new Integer[]{Integer.parseInt(sceneId)};
        }
        appUser.setRegistDate(new Date());
        appUser.setPubFlag(false);
        try {
            if (hadCheckInvite(appUserDTO.getHospital(), invite)) {
                appUserService.executeRegist(appUser, invite, masterIds);
            } else {
                appUserService.executeRegist(appUser, null, null);
            }
        } catch (SystemException e) {
            return registerError(model, appUserDTO, request, e.getMessage());
        }
        return bind(request, appUser.getMobile(), appUserDTO.getPassword(), model, redirectAttributes);
    }

    private boolean hadCheckInvite(String hosName, String invite) {
        if (Constants.DEFAULT_HOS_NAME.equals(hosName) && Constants.DEFAULT_INVITE.equals(invite)) {
            return false;
        }
        return true;
    }


    private boolean checkCaptcha(String mobile, String captchaValue) {
        Captcha captcha = (Captcha) redisCacheUtils.getCacheObject(mobile);
        try {
            if (captcha != null) {
                boolean isValid = jSmsService.verify(captcha.getMsgId(), captchaValue);
                return isValid;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 清除场景cookie
     *
     * @param request
     */
    private void clearSceneCookie(HttpServletRequest request) {
        CookieUtils.clearCookie(request, WeixinConfig.SCENE_ID_KEY);
    }


    @RequestMapping(value = "/app/download")
    public String appDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userAgent = request.getHeader("User-Agent");
        if(userAgent!=null && (userAgent.contains("iPhone")|| userAgent.contains("iPod")||userAgent.contains("iPad"))){
            response.sendRedirect(iosDownloadUrl);
        }else {
            response.sendRedirect(andDownloadUrl);
        }
        return null;
    }
}
