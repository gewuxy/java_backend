package cn.medcn.search.service;

import cn.medcn.article.model.Article;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/8/7.
 */
public interface SearchService {

    /**
     * 基本的OR查询
     * @param pageable
     * @return
     */
    SearchResult search(Map<String,String> orMap,Map<String,String> andMap,Map<String,String> filterMap,Map<String,String> sortMap,Pageable pageable) throws SystemException;

    /**
     * solr core地址
     * @return
     */
    String getSolrUrl();

    /**
     * 查询结果对象的class
     * @return
     */
    Class getEntityClass();

}
