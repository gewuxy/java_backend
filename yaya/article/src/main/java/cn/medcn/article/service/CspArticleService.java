package cn.medcn.article.service;

import cn.medcn.article.model.CspArticle;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;

/**
 * Created by Liuchangling on 2017/10/25.
 */
public interface CspArticleService extends BaseService<CspArticle> {

    /**
     * 帮助与反馈列表
     * @param pageable
     * @return
     */
    MyPage<CspArticle> findCSPmeetingServiceListByPage(Pageable pageable);
}
