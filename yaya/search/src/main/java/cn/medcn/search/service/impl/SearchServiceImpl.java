package cn.medcn.search.service.impl;

import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.SearchResult;
import cn.medcn.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/8/7.
 */
@Service
public abstract class SearchServiceImpl implements SearchService {

    /**
     * 基本的OR查询
     * @param pageable
     * @return
     */
    @Override
    public SearchResult baseSearch( Pageable pageable) {
        String url = getSolrUrl();
        HttpSolrClient client = new HttpSolrClient(url);
        SolrQuery query = new SolrQuery();
        Map<String,Object> map = pageable.getParams();
        if(map.size() == 0){
            query.setQuery("*:*");
        }else{
            StringBuffer buffer = new StringBuffer();
            int index = 0;
            for(Map.Entry<String,Object> entry:map.entrySet()){
                buffer.append(entry.getKey()).append(":").append(entry.getValue());
                buffer.append(index == map.size() - 1 ? "" : " OR ");
                index ++;
            }
            query.setQuery(buffer.toString());
            query.setStart((pageable.getPageNum()-1) * pageable.getPageNum());
            query.setRows(pageable.getPageSize());
        }

        SearchResult result = new SearchResult();
        try {
            QueryResponse response = client.query(query);
            SolrDocumentList list = response.getResults();
            result.setCount(list.getNumFound());
            List dtoList = response.getBeans(getEntityClass());
            result.setList(dtoList);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }




}
