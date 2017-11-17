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

    /**
     * AJAX获取
     * 公司动态
     * @param pageable
     * @return
     */
    @RequestMapping(value="/ajaxTrends")
    @ResponseBody
    public String ajaxTrends(Pageable pageable){
        pageable.getParams().put("categoryId", News.NEWS_CATEGORY.CATEGORY_GSDT.categoryId);
        MyPage<News> page = newsService.pageNews(pageable);
        for(News news:page.getDataList()){
            news.replaceJSPTAG(editorMediaPath);
        }
        return APIUtils.success(page);
    }

    /**
     * AJAX 加载首页新闻列表
     * @param pageable
     * @return
     */
    @RequestMapping(value="/pagenews")
    @ResponseBody
    public String pagenews(Pageable pageable){
        MyPage<News> page = newsService.findAllNews(pageable);
        for(News news:page.getDataList()){
            news.replaceJSPTAG(editorMediaPath);
        }
        return APIUtils.success(page);
    }
}
