package cn.medcn.search.service.impl;

import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.ArticleDTO;
import cn.medcn.search.dto.SearchResult;
import cn.medcn.search.service.SearchService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/8/7.
 */
@Service
public abstract  class SearchServiceImpl implements SearchService<T> {



    @Override
    public SearchResult<T> search(Map<String,String> queryMap, Pageable pageable) {
        String url = getSolrUrl();
        HttpSolrClient client = new HttpSolrClient(url);
        SolrQuery query = new SolrQuery();
        for(Map.Entry<String,String> entry:queryMap.entrySet()){
            query.setQuery(entry.getKey() + ":" + entry.getValue());
        }
        query.setStart((pageable.getPageNum()-1) * pageable.getPageNum());
        query.setRows(pageable.getPageSize());
        SearchResult result = new SearchResult();
        try {
            QueryResponse response = client.query(query);
            SolrDocumentList list = response.getResults();
            result.setCount(list.getNumFound());
            List<T> dtoList = response.getBeans(T.class);
            result.setList(dtoList);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
