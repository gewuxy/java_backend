package cn.medcn.article.service.impl;

import cn.medcn.article.dao.CspArticleDAO;
import cn.medcn.article.model.CspArticle;
import cn.medcn.article.service.CspArticleService;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public MyPage<CspArticle> findCSPmeetingServiceListByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        List<CspArticle> articleList= cspArticleDAO.findCSPmeetingServiceListByPage();
        MyPage<CspArticle> page = new MyPage<CspArticle>();
        page.setDataList(articleList);
        return page;
    }
}
