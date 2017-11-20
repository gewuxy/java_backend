package cn.medcn.official.controller;

import cn.medcn.article.model.Article;
import cn.medcn.article.model.News;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.official.model.OffSearch;
import cn.medcn.official.service.OffiSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * by create HuangHuibin 2017/11/15
 */
@Controller
@RequestMapping(value="/search")
public class SearchController extends BaseController{

    @Autowired
    private OffiSearchService offiSearchService;

    @Autowired
    private ArticleService articleService;

    /**
     * 搜索主页
     * @param searchType
     * @param model
     * @return
     */
    @RequestMapping(value="/view")
    public String search(Integer searchType,Model model){
        model.addAttribute("searchType",searchType);
        List<OffSearch> list = offiSearchService.findTopHost(searchType);
        model.addAttribute("hotList",list);
        return "/search/search";
    }


    /**
     * 搜索关键词返回列表
     * @param keyWord
     * @param model
     * @return
     */
    @RequestMapping(value="/searchList/{keyWord}")
    public String searchList(@PathVariable String keyWord, Pageable pageable,Model model){
        model.addAttribute("keyWords",keyWord);
        pageable.setPageSize(10);
        pageable.getParams().put("keyword","%" + keyWord + "%");
        MyPage<Article> page  = articleService.findArticles(pageable);
        model.addAttribute("page",page);
        return "/search/searchList";
    }

}
