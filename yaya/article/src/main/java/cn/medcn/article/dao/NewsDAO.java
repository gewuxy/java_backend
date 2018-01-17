package cn.medcn.article.dao;

import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.model.News;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/7/3.
 */
public interface NewsDAO extends Mapper<News>{

    List<News> findByParams(Map<String, Object> params);

    Integer getMaxNid(@Param("categoryId")String categoryId);

    List<News> findAllNews(Map<String, Object> params);

    List<News> findNewsList(Map<String, Object> params);

    List<ArticleCategory> findCategoryList();
}
