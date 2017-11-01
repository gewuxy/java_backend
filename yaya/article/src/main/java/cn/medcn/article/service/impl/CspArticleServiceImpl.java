package cn.medcn.article.service.impl;

import cn.medcn.article.dao.CspArticleDAO;
import cn.medcn.article.model.CspArticle;
import cn.medcn.article.service.CspArticleService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/10/25.
 */
@Service
public class CspArticleServiceImpl extends BaseServiceImpl<CspArticle> implements CspArticleService {

    @Autowired
    protected CspArticleDAO cspArticleDAO;

    @Override
    public Mapper<CspArticle> getBaseMapper() {
        return cspArticleDAO;
    }
}
