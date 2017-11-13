package cn.medcn.csp.admin.dao;

import cn.medcn.article.model.CspArticle;
import com.github.abel533.mapper.Mapper;

import java.util.List;

public interface FeedBackDao extends Mapper<CspArticle> {
    List<CspArticle> findCSPmeetingServiceListByPage();
}
