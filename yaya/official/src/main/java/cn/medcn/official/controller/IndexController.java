package cn.medcn.official.controller;

import cn.medcn.article.model.News;
import cn.medcn.article.service.NewsService;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * by create HuangHuibin 2017/11/14
 */
@Controller
public class IndexController {
    @Autowired
    private NewsService newsService;

    @Value("${editor_media_path}")
    private String editorMediaPath;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String jcms(){
        return "index";
    }

    @RequestMapping(value="/index")
    public String index(){
        return "index";
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
}
