package cn.medcn.search.service.impl;

import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.ArticleDTO;
import cn.medcn.search.dto.SearchResult;
import cn.medcn.search.service.SearchService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

/**
 * Created by LiuLP on 2017/12/6/006.
 */
@Service("testServiceImpl")
public class TestServiceImpl extends SearchServiceImpl implements SearchService{

    @Override
    public String getCore() {
        return null;
    }

    @Override
    public Class getEntityClass() {
        return ArticleDTO.class;
    }
}
