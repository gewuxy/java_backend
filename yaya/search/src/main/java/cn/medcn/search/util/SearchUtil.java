package cn.medcn.search.util;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by weilong on 2017/7/21.
 */
@Service
public class SearchUtil {

    /**
     * solr http服务地址
     */
    @Value("${solr.url}")
    private String SOLR_URL;

    public void setSOLR_URL(String SOLR_URL) {
        this.SOLR_URL = SOLR_URL;
    }

    private HttpSolrClient getHttpSolrClient(String core) {
        return new HttpSolrClient(SOLR_URL + "/" + core);
    }

    /**
     * 添加文档
     *
     * @param map
     * @throws Exception
     */
    public void add(Map<String, String> map, String core)
            throws Exception {
        SolrInputDocument sid = new SolrInputDocument();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sid.addField(entry.getKey(), entry.getValue());
        }
        HttpSolrClient solrClient = getHttpSolrClient(core);
        solrClient.add(sid);
        solrClient.commit();
        solrClient.close();
    }

    /**
     * 通过entitySearchDto对象添加文档
     *
     * @throws Exception
     */
    public void add(Object entitySearchDTO, String core)
            throws Exception {
        if (entitySearchDTO == null) {
            return;
        }
        HttpSolrClient solrClient = getHttpSolrClient(core);
        solrClient.addBean(entitySearchDTO);
        solrClient.commit();
        solrClient.close();
    }

    /**
     * 添加多个Article文档
     *
     * @param entitySearchDTOList
     * @throws Exception
     */
    public void addList(List<Object> entitySearchDTOList, String core)
            throws Exception {
        if (entitySearchDTOList == null || entitySearchDTOList.size() <= 0) {
            return;
        }
        HttpSolrClient solrClient = getHttpSolrClient(core);
        solrClient.addBeans(entitySearchDTOList);
        solrClient.commit();
        solrClient.close();
    }

    /**
     * 根据id删除索引
     *
     * @param id
     * @throws Exception
     */
    public void deleteById(String id, String core)
            throws Exception {
        HttpSolrClient solrClient = getHttpSolrClient(core);
        solrClient.deleteById(id);
        solrClient.commit();
        solrClient.close();
    }

    /**
     * 根据id集合删除索引
     *
     * @param idList
     * @throws Exception
     */
    public void deleteByIdList(List<String> idList, String core)
            throws Exception {
        HttpSolrClient solrClient = getHttpSolrClient(core);
        solrClient.deleteById(idList);
        solrClient.commit();
        solrClient.close();
    }

    /**
     * 清除所有索引记录
     *
     * @throws Exception
     */
    public void emptyCore(String core)
            throws Exception {
        HttpSolrClient solrClient = getHttpSolrClient(core);
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
        solrClient.close();
    }

    /**
     * 根据查询条件获取模型对象
     *
     * @param solrQuery
     * @return List<ArticleSearchDTO>
     * @throws Exception
     */
    public List<Object> searchList(SolrQuery solrQuery, String core) throws Exception {
        HttpSolrClient solrClient = getHttpSolrClient(core);
        QueryResponse result = solrClient.query(solrQuery);
        List<Object> entitySearchDTO = result.getBeans(Object.class);
        solrClient.close();
        return entitySearchDTO;
    }

    /**
     * 根据查询条件获取模型对象
     *
     * @param solrQuery
     * @return List<ArticleSearchDTO>
     * @throws Exception
     */
    public QueryResponse search(SolrQuery solrQuery, String core) throws Exception {
        HttpSolrClient solrClient = getHttpSolrClient(core);
        QueryResponse result = solrClient.query(solrQuery);
        solrClient.close();
        return result;
    }

    /**
     * 根据查询条件获取模型对象
     *
     * @param key String
     * @return List<ArticleSearchDTO>
     * @throws Exception
     */
    public List<Object> searchList(String key, String core) throws Exception {
        HttpSolrClient solrClient = getHttpSolrClient(core);
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", key);
        QueryResponse result = solrClient.query(solrQuery);
        List<Object> entitySearchDTO = result.getBeans(Object.class);
        solrClient.close();
        return entitySearchDTO;
    }


}
