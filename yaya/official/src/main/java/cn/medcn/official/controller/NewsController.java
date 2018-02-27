package cn.medcn.official.controller;

import cn.medcn.article.model.News;
import cn.medcn.article.service.NewsService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixuan on 2017/3/6.
 */
@Controller
@RequestMapping(value="/news")
public class NewsController extends BaseController {
    @Autowired
    private NewsService newsService;

    @Value("${editor_media_path}")
    private String editorMediaPath;

    @Value("${app.file.base}")
    private String fileBasePath;

    /**
     * AJAX获取
     * 公司动态
     * @param pageable
     * @return
     */
    @RequestMapping(value="/ajaxTrends")
    @ResponseBody
    public String ajaxTrends(Pageable pageable,String type){
        String category = "CATEGORY_" + type;
        pageable.put("categoryId", News.NEWS_CATEGORY.valueOf(category).categoryId);
        MyPage<News> page = newsService.pageNews(pageable);
        for(News news:page.getDataList()){
            news.replaceJSPTAG(editorMediaPath);
        }
        return APIUtils.success(page);
    }

    /**
     * 跳转到公司动态新闻列表页
     * @param pageable
     * @return
     */
    @RequestMapping(value="/trends")
    public String trends(Pageable pageable, Model model){
        pageable.setPageSize(10);
        pageable.getParams().put("categoryId", News.NEWS_CATEGORY.CATEGORY_GSDT.categoryId);
        MyPage<News> page = newsService.pageNews(pageable);
        for(News news:page.getDataList()){
            news.replaceJSPTAG(fileBasePath);
        }
        model.addAttribute("page", page);
        return "/news/trends";
    }

    /**
     * AJAX 加载首页新闻列表
     * @param pageable
     * @return
     */
    @RequestMapping(value="/pagenews")
    @ResponseBody
    public String pagenews(Pageable pageable){
        //pageable.getParams().put("categoryId", News.NEWS_CATEGORY.CATEGORY_YYXW.categoryId);
        MyPage<News> page = newsService.findAllNews(pageable);
        for(News news:page.getDataList()){
            news.replaceJSPTAG(fileBasePath);
        }
        return APIUtils.success(page);
    }

    @RequestMapping(value="/view/{nid}")
    public String view(@PathVariable String nid, Model model) throws Exception{
        if (StringUtils.isEmpty(nid)){
            throw new Exception("参数不正确");
        }
        News news = newsService.selectByPrimaryKey(nid);
        if (news == null){
            throw new Exception("您查看的新闻id=["+nid+"] 不存在");
        }
        news.replaceJSPTAG(fileBasePath);
        model.addAttribute(news);
        return "/news/view";
    }

    @RequestMapping(value="/viewtrend/{nid}")
    public String viewtrend(@PathVariable String nid, Model model) throws Exception {
        if (StringUtils.isEmpty(nid)){
            throw new Exception("参数不正确");
        }
        News news = newsService.selectByPrimaryKey(nid);
        if (news == null){
            throw new Exception("您查看的新闻id=["+nid+"] 不存在");
        }
        news.replaceJSPTAG(fileBasePath);
        model.addAttribute(news);
        return "/news/view_trend";
    }

    /**
     * 医药用药跟安全用药获取数据
     * @param pageable
     * @return
     */
    @RequestMapping(value="/list")
    public String dtList(Pageable pageable, String type,Model model){
        pageable.setPageSize(4);
        String category = "CATEGORY_" + type;
        pageable.put("categoryId", News.NEWS_CATEGORY.valueOf(category).categoryId);
        MyPage<News> page = newsService.pageNews(pageable);
        for(News news:page.getDataList()){
            news.replaceJSPTAG(editorMediaPath);
        }
        model.addAttribute("page", page);
        model.addAttribute("type",type);
        model.addAttribute("title", News.NEWS_CATEGORY.valueOf(category).label);
        return "/show/newList";
    }

    /**
     * 查看详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/detail/{id}")
    public String detail(@PathVariable String id, Model model){
        News news = new News();
        news.setId(id);
        news = newsService.selectByPrimaryKey(news);
        model.addAttribute("fileBase", fileBasePath);
        model.addAttribute("news",news);
        return "/show/detailView";
    }

}
