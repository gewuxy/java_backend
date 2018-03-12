package cn.medcn.official.controller;

import cn.medcn.article.model.Article;
import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.model.HotSearch;
import cn.medcn.article.model.News;
import cn.medcn.article.service.ArticleService;
import cn.medcn.article.service.HotSearchService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.official.utils.SubjectUtils;
import cn.medcn.search.dto.ArticleDTO;
import cn.medcn.search.service.SearchArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * by create HuangHuibin 2017/11/15
 */
@Controller
@RequestMapping(value="/search")
public class SearchController extends BaseController{

    @Autowired
    private HotSearchService hotSearchService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SearchArticleService searchArticleService;

    @Value("${editor_media_path}")
    private String editorMediaPath;

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
        List<HotSearch> hotList = hotSearchService.findTopHost(categoryId);
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
        insertHistory(keyWord, categoryId);
        pageable.setPageSize(10);
        if(categoryId.equals(News.NEWS_CATEGORY.CATEGORY_YISJY.categoryId)){
            pageable.setPageSize(4);
        }
        model.addAttribute("searchType",searchType);
        model.addAttribute("categoryId",classify);
        model.addAttribute("keyWord",keyWord);
        //获取到对应子级目录
        List<ArticleCategory> list = articleService.findCategoryByPreid(categoryId);
        model.addAttribute("list",list);
        boolean hasNext = false;
        if(StringUtils.isNotEmpty(classify)){
            List<ArticleCategory> result = articleService.findCategoryByPreid(categoryId);
            if(result != null){
                hasNext = true;
            }
        }
        Map<String, String> orMap = new HashMap<>();
        Map<String, String> filterMap = new HashMap<>();
        Map<String, String> sortMap = new HashMap<>();
        if(StringUtils.isNotEmpty(keyWord)){
            orMap.put("title",keyWord);
            orMap.put("xfrom",keyWord);
            orMap.put("keywords",keyWord);
            orMap.put("history_id",categoryId);
        }
        if(hasNext){  //有第三级或者更多
            orMap.put("history_id",classify);
        }else {  //只有二级
            if(StringUtils.isNotEmpty(classify)){  //选了二级
                filterMap.put("category_id",classify);
            }
        }
        sortMap.put("create_time","desc");
        MyPage<ArticleDTO> page = searchArticleService.searchArticle(orMap,null,filterMap,sortMap,pageable);
        if(categoryId.equals(News.NEWS_CATEGORY.CATEGORY_YISJY.categoryId)){
            for(ArticleDTO dto:page.getDataList()){
                ArticleDTO.replaceJSPTAG(editorMediaPath,dto);
            }
        }
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
        if(searchType == HotSearch.SearchType.YSJY.getSearchType()){ //药师建议
            article  = articleService.selectOne(article);
            model.addAttribute("detail",article);
            return "/search/searchDetail";
        }else if(searchType == HotSearch.SearchType.YISJY.getSearchType()){ //医师建议
            article  = articleService.selectOne(article);
            model.addAttribute("detail",article);
            return "/search/searchDetail";
        }else{    //对症下药
            article  = articleService.selectOne(article);
            model.addAttribute("detail",article);
            return "/search/searchDetail";
        }
    }

    /**
     * 插入查询历史
     *
     * @param keyWord
     * @param categoryId
     */
    public void insertHistory(String keyWord, String categoryId) {
        if(StringUtils.isEmpty(keyWord)) return;
        HotSearch search = new HotSearch();
        search.setSearch(keyWord);
        search.setSearchTime(new Date());
        search.setSearchType(categoryId);
        search.setSearchUser(SubjectUtils.getCurrentUserid());
        hotSearchService.insertSelective(search);
    }
}
