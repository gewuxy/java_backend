package cn.medcn.jcms.controller.front;

import cn.medcn.article.model.News;
import cn.medcn.article.service.NewsService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.user.model.AppVersion;
import cn.medcn.user.service.AppVersionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lixuan on 2017/3/6.
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private NewsService newsService;

    @Value("${editor_media_path}")
    private String editorMediaPath;

    @Autowired
    protected AppVersionService appVersionService;

    @Value("${csp.file.base}")
    protected String appFileBase;

    @RequestMapping(value="/")
    public String index(){
        return "/index/index1";
    }

    /**
     * 关于我们
     * @param model
     * @return
     */
    @RequestMapping(value="/about")
    public String about(Model model){
        Pageable pageable = new Pageable(1, 3);
        pageable.put("categoryId", News.NEWS_CATEGORY.CATEGORY_GSDT.categoryId);
        MyPage<News> page = newsService.pageNews(pageable);
        for(News news:page.getDataList()){
            news.replaceJSPTAG(editorMediaPath);
        }
        model.addAttribute("page", page);
        return "/index/about";
    }

    @RequestMapping(value="/team")
    public String team(){
        return "/index/team";
    }

    @RequestMapping(value="/joinus")
    public String joinus(){
        return "/index/joinus";
    }

    @RequestMapping(value="/mc/{type}")
    public String mc(@PathVariable String type, Model model){
        if(StringUtils.isBlank(type)){
            type = MobileCenter.HLYY.title;
        }
        model.addAttribute("type", type);
        return "/index/mc_"+type;
    }


    /**
     * yaya医师
     * @return
     */
    @RequestMapping(value = "/scan/qrcode")
    public String downloadApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isWeChat(request)){
            // 微信扫描
            return localeView("/index/download");
        } else {
            AppVersion appVersion = null;
            // 手机浏览器扫描 再判断ios或安卓手机
            if (isIOSDevice(request) ) { // ios系统
                 appVersion = appVersionService.findNewly(AppVersion.APP_TYPE.YAYA_YISHI.type, AppVersion.DRIVE_TAG.IOS.type);

                if (appVersion != null ) {
                    response.sendRedirect(appVersion.getDownLoadUrl());
                }

            } else { // 安卓系统
                appVersion = appVersionService.findNewly(AppVersion.APP_TYPE.YAYA_YISHI.type, AppVersion.DRIVE_TAG.ANDROID.type);
                if (appVersion != null ) {
                    response.sendRedirect(appFileBase + appVersion.getDownLoadUrl());
                }
            }
        }
        return null;
    }
}
