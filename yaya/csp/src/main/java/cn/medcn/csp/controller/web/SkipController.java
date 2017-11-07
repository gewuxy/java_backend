package cn.medcn.csp.controller.web;

import cn.medcn.article.model.CspArticle;
import cn.medcn.article.service.CspArticleService;
import cn.medcn.common.utils.CookieUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by lixuan on 2017/10/17.
 */
@Controller
public class SkipController extends CspBaseController {
    // CSP 服务协议id
    public static final String SERVICE_PROTOCOL_ID = "17103116062545591360";
    // CSP 关于我们id
    public static final String ABOUT_US_ID = "17103116215880292674";

    @Autowired
    protected CspArticleService articleService;

    @RequestMapping(value = "/")
    public String index(){
        return localeView("/index/index");
    }

    @RequestMapping(value = "/login")
    public String login() {
        return localeView("/login/login");
    }

    @RequestMapping(value = "/index/{id}")
    public String index(@PathVariable String id, Model model) {
        if (StringUtils.isNotEmpty(id)) {
            CspArticle article = articleService.selectByPrimaryKey(id);
            model.addAttribute("article", article);
        }
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



}
