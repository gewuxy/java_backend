package cn.medcn.article.dao;

import cn.medcn.article.model.ArticleCategory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/5/9.
 */
public interface ArticleCategoryDAO extends Mapper<ArticleCategory> {

    List<ArticleCategory> findAllCategory();

    List<ArticleCategory> findCategoryByPreid(@Param("preId")String preId);
}
