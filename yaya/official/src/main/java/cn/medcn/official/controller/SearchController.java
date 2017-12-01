package cn.medcn.official.controller;

import cn.medcn.article.model.Article;
import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.model.News;
import cn.medcn.article.service.ArticleService;
import cn.medcn.article.service.NewsService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.official.model.OffSearch;
import cn.medcn.official.service.OffiSearchService;
import cn.medcn.official.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
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
     * @param searchType 搜索类型，医师，药师，对症查询
     * @param model
     * @return
     */
    @RequestMapping(value="/view")
    public String search(String searchType,Model model){
        model.addAttribute("searchType",searchType);
        String categoryId = News.NEWS_CATEGORY.valueOf("CATEGORY_" + searchType).categoryId;
        //获取对应类型的热搜词查询
        List<OffSearch> hotList = offiSearchService.findTopHost(categoryId);
        model.addAttribute("hotList",hotList);
        //获取到分类查询
        List<ArticleCategory> list = articleService.findCategoryByPreid(categoryId);
        model.addAttribute("list",list);
        return "/search/search";
    }

    /**
     * 搜索关键词返回列表
     * @param keyWord
     * @param model
     * @return
     */
    @RequestMapping(value="/searchList")
    public String searchList(String keyWord, String searchType,String classify,Pageable pageable,Model model){
        String categoryId = News.NEWS_CATEGORY.valueOf("CATEGORY_" + searchType).categoryId;
        //添加查询记录
        OffSearch search = new OffSearch();
        search.setSearch(keyWord);
        search.setSearchTime(new Date());
        search.setSearchType(categoryId);
        search.setSearchUser(SubjectUtils.getCurrentUserid());
        offiSearchService.insertSelective(search);
        model.addAttribute("searchType",searchType);
        //根据分类查询
        pageable.setPageSize(10);
        model.addAttribute("keyWord",keyWord);
        pageable.getParams().put("categoryId",categoryId);
        if(!StringUtils.isEmpty(classify)){
            pageable.getParams().put("classify",classify);
            pageable.getParams().remove("categoryId");
        }
        pageable.getParams().put("keyword","%" + keyWord + "%");
        MyPage<Article> page  = articleService.findArticles(pageable);
        model.addAttribute("page",page);
        return "/search/searchList";
    }

    /**
     * 返回详细内容
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/detail/{id}")
    public String detailView(@PathVariable String id,Integer searchType,Model model){
        Article article = new Article();
        article.setId(id);
        if(searchType == OffSearch.SearchType.YSJY.getSearchType()){ //药师建议
            article  = articleService.selectOne(article);
            model.addAttribute("detail",article);
            return "/search/searchDetail";
        }else if(searchType == OffSearch.SearchType.YISJY.getSearchType()){ //医师建议
            article  = articleService.selectOne(article);
            model.addAttribute("detail",article);
            return "/search/searchDetail";
        }else{    //对症下药
            article  = articleService.selectOne(article);
            model.addAttribute("detail",article);
            return "/search/searchDetail";
        }
    }


    public void setClassify(){

    }
}
