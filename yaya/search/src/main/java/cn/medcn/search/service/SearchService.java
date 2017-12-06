package cn.medcn.search.service;

import cn.medcn.article.model.Article;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.SearchResult;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/8/7.
 */
public interface SearchService<T> {

    SearchResult<T> search(Map<String,String> queryMap, Pageable pageable);

    String getSolrUrl();
}
