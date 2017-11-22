package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.ArticleCategory;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/11/17.
 */
public interface WriteAbleArticleCategoryService extends WriteAbleBaseService<ArticleCategory> {

    ArticleCategory transferArticleCategory(ArticleCategory articleCategory);

    ArticleCategory findParent(String preId);

    String getHistoryId(String categoryId, String rootId) ;

}
