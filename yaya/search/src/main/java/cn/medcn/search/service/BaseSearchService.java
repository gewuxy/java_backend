package cn.medcn.search.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weilong on 2017/8/7.
 */
public interface BaseSearchService {
    public void updateById(String id) throws SystemException;

    public void updateByIdList(List<String> idList) throws SystemException;

    public void deleteById(String id) throws SystemException;

    public void deleteByIdList(List<String> idList) throws SystemException;

    public void empty() throws SystemException;

    public QueryResponse search(SolrQuery solrQuery) throws SystemException;

    public void reload();

    public String getReloadSchedule();

    public void setReloadSchedule(String schedule);

    public void stopReload();

    public Object getEntity();

    public ArrayList<Object> getEntitySearchDtoList(List<Object> list);
}
