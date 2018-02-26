package cn.medcn.search.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.ArticleDTO;
import cn.medcn.search.dto.SearchResult;

import java.util.Map;

/**
 * huanghuibin 2018/02/06
 */
public interface SearchArticleService extends SearchService{

    void addOrUpdate(ArticleDTO dto) throws Exception;

    MyPage<ArticleDTO> searchArticle(Map<String, String> orMap, Map<String, String> andMap, Map<String, String> filterMap, Map<String, String> sortMap, Pageable pageable);
}
