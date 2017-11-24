package cn.medcn.article.dao;

import cn.medcn.article.model.CspArticle;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * Created by Liuchangling on 2017/10/25.
 */
public interface CspArticleDAO extends Mapper<CspArticle> {
    List<CspArticle> findCSPmeetingServiceListByPage();
}
