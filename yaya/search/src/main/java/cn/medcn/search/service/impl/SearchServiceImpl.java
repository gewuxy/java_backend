package cn.medcn.search.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.SearchResult;
import cn.medcn.search.service.SearchService;
import cn.medcn.search.supports.Searchable;
import org.apache.solr.client.solrj.SolrClient;
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
     *
     * @param orMap  or查询需要传入的map，key为要查询的字段，value为要查询的值
     * @param andMap and查询需要传入的map，key为要查询的字段，value为要查询的值
     * @param filterMap 对查询的结果进行过滤，key为需要过滤的条件字段，value为要滤的条件字段的值
     * @param sortMap 对查询的结果进行排序，key为排序条件的字段，value为排序的方法，传入desc表示降序，asc表示升序
     * @param pageable pageNum为开始查询的位置，pageSize为要查询的条数
     * @return  查询结果对象
     * @throws SystemException  当sortMap不为null,又有多个值时，抛出异常
     */
    @Override
    public SearchResult search( Map<String,String> orMap,Map<String,String> andMap,Map<String,String> filterMap,Map<String,String> sortMap,Pageable pageable) throws SystemException {
        String url = getSolrUrl();
        HttpSolrClient client = new HttpSolrClient(url);
        SolrQuery query = new SolrQuery();
        //没有搜索条件
        if((orMap == null && andMap == null) || (orMap.size() == 0 && andMap.size() == 0)){
            query.setQuery("*:*");
        }else{
            StringBuffer buffer = new StringBuffer();
            //同时有or查询和and查询,两个查询中间用or连接
            if((orMap != null && orMap.size() != 0) && (andMap != null && andMap.size() != 0)){
                buffer = getBuffer(orMap, andMap, buffer);
            }else if(orMap != null && orMap.size() != 0){  //or查询
                int index = 0;
                for(Map.Entry<String,String> entry:orMap.entrySet()){
                    buffer.append(entry.getKey()).append(":").append(entry.getValue());
                    buffer.append(index == orMap.size() - 1 ? "" : " || ");
                    index ++;
                }
            }else if(andMap != null && andMap.size() != 0){
                int index = 0;
                for(Map.Entry<String,String> entry:andMap.entrySet()){
                    buffer.append(entry.getKey()).append(":").append(entry.getValue());
                    buffer.append(index == orMap.size() - 1 ? "" : " && ");
                    index ++;
                }
            }
            query.setQuery(buffer.toString());
        }

        //对搜索结果过滤
        if(filterMap != null && filterMap.size() != 0){
            int index = 0;
            StringBuffer buffer = new StringBuffer();
            for(Map.Entry<String,String> entry:filterMap.entrySet()){
                buffer.append(entry.getKey()).append(":").append(entry.getValue());
                buffer.append(index == orMap.size() - 1 ? "" : " && ");
                index ++;
            }
            query.addFilterQuery(buffer.toString());
        }

        //对搜索结果排序
        if(sortMap != null && sortMap.size() == 1){
            for(Map.Entry<String,String> entry:sortMap.entrySet()){
                if("desc".equals(entry.getValue())){
                    query.setSort(entry.getKey(), SolrQuery.ORDER.desc);
                }else if("asc".equals(entry.getValue())){
                    query.setSort(entry.getKey(), SolrQuery.ORDER.asc);
                }
            }
        }else if(sortMap != null && sortMap.size() != 1){
            throw new SystemException("请输入正确的排序方式");
        }

        query.setStart((pageable.getPageNum()-1) * pageable.getPageNum());
        query.setRows(pageable.getPageSize());

        return doSearch(client, query);
    }


    protected SearchResult doSearch(SolrClient client, SolrQuery query){
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


    /**
     * 根据 searchable 对象进行检索
     *
     * @param searchable
     * @return
     */
    @Override
    public SearchResult search(Searchable searchable) {
        String url = getSolrUrl();
        HttpSolrClient client = new HttpSolrClient(url);
        SolrQuery query = new SolrQuery();
        query.setStart(searchable.getStart());
        query.setRows(searchable.getPageSize());

        query.setQuery(searchable.getQuery());
        return doSearch(client, query);
    }

    private StringBuffer getBuffer(Map<String, String> orMap, Map<String, String> andMap, StringBuffer buffer) {
        int orIndex = 0;
        buffer.append("(");
        for(Map.Entry<String,String> entry:orMap.entrySet()){
            buffer.append(entry.getKey()).append(":").append(entry.getValue());
            buffer.append(orIndex == orMap.size() - 1 ? ")" : " || ");
            orIndex ++;
        }
        buffer.append(" || (");
        int andIndex = 0;
        for(Map.Entry<String,String> entry:andMap.entrySet()){
            buffer.append(entry.getKey()).append(":").append(entry.getValue());
            buffer.append(andIndex == orMap.size() - 1 ? ")" : " && ");
            andIndex ++;
        }
        return buffer;
    }


}
