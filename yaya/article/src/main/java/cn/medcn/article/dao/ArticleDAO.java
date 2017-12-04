package cn.medcn.article.dao;

import cn.medcn.article.model.Article;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/9.
 */
public interface ArticleDAO extends Mapper<Article> {

    List<Article> findArticles(Map<String, Object> params);


    Article findAdvert(@Param("categoryId") String categoryId);

    List<Article> findBanners(@Param("categoryId")String categoryId);

    List<Article> searchArticles(Map<String, Object> params);
}
