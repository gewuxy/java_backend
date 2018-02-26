package cn.medcn.search.service.impl;


import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.ArticleDTO;
import cn.medcn.search.dto.SearchResult;
import cn.medcn.search.service.SearchArticleService;
import cn.medcn.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Article
 *
 * Created by huanghuibin on 2017/8/7.
 */
@Service
public class SearchArticleServiceImpl extends SearchServiceImpl implements SearchArticleService {

    @Value("${solr.article.core}")
    private String solrCore;

    @Autowired
    protected SearchService searchService;


    /**
     * 新增或者更新索引
     * @param dto
     * @throws Exception
     */
    @Override
    public void addOrUpdate(ArticleDTO dto) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("id",dto.getId());
        map.put("history_id",dto.getHistory_id());
        map.put("title",dto.getTitle());
        map.put("author",dto.getAuthor());
        map.put("keywords",dto.getKeywords());
        map.put("summary",dto.getSummary());
        map.put("content",dto.getContent());
        map.put("article_img_s",dto.getArticle_img_s());
        searchService.add(map);
    }

    /**
     * 搜索
     * @param orMap
     * @param andMap
     * @param filterMap
     * @param sortMap
     * @param pageable
     * @return
     */
    @Override
    public MyPage<ArticleDTO> searchArticle(Map<String, String> orMap, Map<String, String> andMap, Map<String, String> filterMap, Map<String, String> sortMap, Pageable pageable) {
        SearchResult result = null;
        try {
             result = search(orMap,andMap,filterMap,sortMap,pageable);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        MyPage<ArticleDTO> page = new MyPage<>();
        page.setDataList(result.getList());
        page.setPages((int) (result.getCount() / pageable.getPageSize()));
        page.setPageNum(pageable.getPageNum());
        return page;
    }

    @Override
    public String getCore() {
        return solrCore;
    }


    @Override
    public Class getEntityClass() {
        return ArticleDTO.class;
    }
}
