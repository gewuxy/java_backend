package cn.medcn.csp.admin.service;

import cn.medcn.article.model.CspArticle;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;

public interface FeedBackService extends BaseService<CspArticle> {
    MyPage<CspArticle> findCSPmeetingServiceListByPage(Pageable pageable);
}
