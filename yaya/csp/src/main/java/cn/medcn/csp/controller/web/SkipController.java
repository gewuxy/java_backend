package cn.medcn.csp.controller.web;

import cn.medcn.article.model.CspArticle;
import cn.medcn.article.service.CspArticleService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CookieUtils;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.user.model.AppVersion;
import cn.medcn.user.service.AppVersionService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static cn.medcn.common.Constants.LOGIN_USER_ID_KEY;
import static cn.medcn.common.Constants.LOGIN_USER_KEY;


/**
 * Created by lixuan on 2017/10/17.
 */
@Controller
public class SkipController extends CspBaseController {
    @Value("${csp.file.base}")
    protected String appFileBase;


    // CSP 服务协议id
    public static final String SERVICE_PROTOCOL_ID = "17103116062545591360";
    // CSP 关于我们id
    public static final String ABOUT_US_ID = "17103116215880292674";

    @Autowired
    protected CspArticleService articleService;

    @Autowired
    protected AppVersionService appVersionService;


    protected void getCookieUser(HttpServletRequest request, Model model){
        String userName = CookieUtils.getCookieValue(request, LOGIN_USER_KEY);
        try {
            if (StringUtils.isNotEmpty(userName)) {
                userName = URLDecoder.decode(userName,"UTF-8");
                model.addAttribute("username", userName);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 首页 检查缓存中是否有账号，如果有账号 显示登录账号
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, Model model){
        // 获取缓存是否存在用户数据
        getCookieUser(request, model);

        return "/index/index";
    }


    /**
     * 点击用户名登录 检查缓存是否有账号，有账号直接进入后台，没有账号需进入登录界面
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request,HttpServletResponse response) {
        // 从缓存获取是否有用户id
        String userId = CookieUtils.getCookieValue(request, LOGIN_USER_ID_KEY);
        if (StringUtils.isNotEmpty(userId)) {
            try {
                // 缓存中用户id不为空 直接登录
                UsernamePasswordToken token = new UsernamePasswordToken();
                token.setHost("thirdParty");
                token.setUsername(userId);

                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
                return defaultRedirectUrl();

            } catch (AuthenticationException e) {
                CookieUtils.clearCookie(response, LOGIN_USER_ID_KEY);
                CookieUtils.clearCookie(response, LOGIN_USER_KEY);
                // 登录异常: 缓存过期，账号未认证，跳转登录主界面
                return localeView("/login/login");
            }
        }

        return localeView("/login/login");
    }


    @RequestMapping(value = "/index/{id}")
    public String index(@PathVariable String id, Model model, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(id)) {
            CspArticle article = articleService.selectByPrimaryKey(id);
            model.addAttribute("article", article);
        }

        // 获取缓存是否存在用户数据
        getCookieUser(request, model);

        if (id.equals(SERVICE_PROTOCOL_ID) || id.equals(ABOUT_US_ID)) {
            return localeView("/index/about");
        }  else {
            return localeView("/index/view");
        }
    }


    /**
     * app端静态页面显示
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String apiIndex(@PathVariable String id, Model model) {
        CspArticle article = articleService.selectByPrimaryKey(id);
        model.addAttribute("article", article);
        return localeView("/index/app_view");
    }



    @RequestMapping(value = "/cookie/modify")
    @ResponseBody
    public String modifyCookie(String cookieName, String cookieValue, HttpServletResponse response){
        CookieUtils.setCookie(response, cookieName, cookieValue);
        return success();
    }


    @RequestMapping(value = "/cookie/get")
    @ResponseBody
    public String cookie(String cookieName, HttpServletRequest request){
        String cookie = CookieUtils.getCookieValue(request, cookieName);
        return cookie;
    }



    /**
     * 官网首页 微信扫描二维码下载app 跳转提示页面
     * @return
     */
    @RequestMapping(value = "/scan/qrcode")
    public String downloadApp(String local, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isWeChat(request)){
            // 微信扫描
            return localeView("/index/download");
        } else {
            AppVersion appVersion = null;
            // 手机浏览器扫描 再判断ios或安卓手机
            if (isIOSDevice(request) ) { // ios系统
                // 海外版 下载
                if (local.equals(LocalUtils.Local.en_US.name())) {
                    appVersion = appUrl(AppVersion.DRIVE_TAG.IOS.type, AppVersion.APP_TYPE.CSPMeeting_US.type);
                } else { // 国内版 下载
                    appVersion = appUrl(AppVersion.DRIVE_TAG.IOS.type, AppVersion.APP_TYPE.CSPMeeting_CN.type);
                }

                if (appVersion != null ) {
                    response.sendRedirect(appVersion.getDownLoadUrl());
                }

            } else { // 安卓系统
                // 海外版 下载
                if (local.equals(LocalUtils.Local.en_US.name())) {
                    appVersion = appUrl(AppVersion.DRIVE_TAG.ANDROID.type, AppVersion.APP_TYPE.CSPMeeting_US.type);
                } else { // 国内版 下载
                    appVersion = appUrl(AppVersion.DRIVE_TAG.ANDROID.type, AppVersion.APP_TYPE.CSPMeeting_CN.type);
                }

                if (appVersion != null ) {
                    response.sendRedirect(appFileBase + appVersion.getDownLoadUrl());
                }
            }

        }
        return null;
    }


    public AppVersion appUrl(String driveTag, String appType) {
        AppVersion appVersion = appVersionService.findNewly(appType, driveTag);
        return appVersion;
    }
}
