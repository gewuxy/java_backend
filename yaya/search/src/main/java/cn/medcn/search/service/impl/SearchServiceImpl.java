package cn.medcn.search.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.SearchResult;
import cn.medcn.search.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/8/7.
 */
@Service
public abstract class SearchServiceImpl implements SearchService {

    @Value("${solr.url}")
    private String solrUrl;

    /**
     * @param orMap     or查询需要传入的map，key为要查询的字段，value为要查询的值
     * @param andMap    and查询需要传入的map，key为要查询的字段，value为要查询的值
     * @param filterMap 对查询的结果进行过滤，key为需要过滤的条件字段，value为要滤的条件字段的值
     * @param sortMap   对查询的结果进行排序，key为排序条件的字段，value为排序的方法，传入desc表示降序，asc表示升序
     * @param pageable  pageNum为开始查询的位置，pageSize为要查询的条数
     * @return 查询结果对象
     * @throws SystemException 当sortMap不为null,又有多个值时，抛出异常
     */
    @Override
    public SearchResult search(Map<String, String> orMap, Map<String, String> andMap, Map<String, String> filterMap, Map<String, String> sortMap, Pageable pageable) throws SystemException {
        HttpSolrClient client = new HttpSolrClient(solrUrl + getCore());
        SolrQuery query = new SolrQuery();
        //没有搜索条件
        if ((orMap == null || orMap.size() < 1) && (andMap == null || andMap.size() < 1)) {
            query.setQuery("*:*");
        } else {
            StringBuffer buffer = new StringBuffer();
            //同时有or查询和and查询,两个查询中间用or连接
            if ((orMap != null && orMap.size() != 0) && (andMap != null && andMap.size() != 0)) {
                buffer = getBuffer(orMap, andMap, buffer);
            } else if (orMap != null && orMap.size() != 0) {  //or查询
                getAndOrBuffer(orMap, Constants.NUMBER_ONE, Constants.NUMBER_TWO, buffer);
            } else if (andMap != null && andMap.size() != 0) {
                getAndOrBuffer(andMap, Constants.NUMBER_ONE, Constants.NUMBER_ONE, buffer);
            }
            query.setQuery(buffer.toString());
        }

        //对搜索结果过滤
        if (filterMap != null && filterMap.size() != 0) {
            StringBuffer buffer = new StringBuffer();
            getAndOrBuffer(filterMap, Constants.NUMBER_ONE, Constants.NUMBER_ONE, buffer);
            query.addFilterQuery(buffer.toString());
        }

        //对搜索结果排序
        if (sortMap != null && sortMap.size() == 1) {
            for (Map.Entry<String, String> entry : sortMap.entrySet()) {
                if ("desc".equals(entry.getValue())) {
                    query.setSort(entry.getKey(), SolrQuery.ORDER.desc);
                } else if ("asc".equals(entry.getValue())) {
                    query.setSort(entry.getKey(), SolrQuery.ORDER.asc);
                }
            }
        } else if (sortMap != null && sortMap.size() != 1) {
            throw new SystemException("请输入正确的排序方式");
        }

        query.setStart((pageable.getPageNum() - 1) * pageable.getPageNum());
        query.setRows(pageable.getPageSize());
        return doSearch(client, query);
    }


    /**
     * 执行搜索
     *
     * @param client
     * @param query
     * @return
     */
    protected SearchResult doSearch(SolrClient client, SolrQuery query) {
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
     * 拼接条件
     *
     * @param map
     * @param type
     * @param buffer
     * @return
     */
    private StringBuffer getAndOrBuffer(Map<String, String> map, Integer flag, Integer type, StringBuffer buffer) {
        String condition = type == Constants.NUMBER_ONE ? " && " : " || ";
        String symbol = flag == Constants.NUMBER_ONE ? "" : ")";
        int index = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            buffer.append(entry.getKey()).append(":").append(entry.getValue());
            buffer.append(index == map.size() - 1 ? symbol : condition);
            index++;
        }
        return buffer;
    }

    private StringBuffer getBuffer(Map<String, String> orMap, Map<String, String> andMap, StringBuffer buffer) {
        buffer.append("(");
        getAndOrBuffer(orMap, Constants.NUMBER_TWO, Constants.NUMBER_TWO, buffer);
        buffer.append(" || (");
        getAndOrBuffer(andMap, Constants.NUMBER_TWO, Constants.NUMBER_ONE, buffer);
        return buffer;
    }

    /**
     * 通过添加Field创建索引文档
     *
     * @param map
     * @throws Exception
     */
    @Override
    public void add(Map<String, String> map) throws Exception {
        HttpSolrClient client = new HttpSolrClient(solrUrl + getCore());
        SolrInputDocument sid = new SolrInputDocument();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sid.addField(entry.getKey(), entry.getValue());
        }
        client.add(sid);
        client.commit();
        client.close();
    }
}
