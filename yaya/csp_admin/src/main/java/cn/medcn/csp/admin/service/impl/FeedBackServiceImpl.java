package cn.medcn.csp.admin.service.impl;

import cn.medcn.article.model.CspArticle;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.csp.admin.dao.FeedBackDao;
import cn.medcn.csp.admin.service.FeedBackService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackServiceImpl extends BaseServiceImpl<CspArticle> implements FeedBackService{

    @Autowired
    private FeedBackDao feedBackDao;

    @Override
    public MyPage<CspArticle> findCSPmeetingServiceListByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        List<CspArticle> articleList= feedBackDao.findCSPmeetingServiceListByPage();
        MyPage<CspArticle> page = new MyPage<CspArticle>();
        page.setDataList(articleList);
        return page;
    }

    @Override
    public Mapper<CspArticle> getBaseMapper() {
        return feedBackDao;
    }


}
