package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.ArticleCategory;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleArticleCategoryService;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/11/21.
 */

public class WriteAbleArticleCategoryServiceImpl extends WriteAbleBaseServiceImpl<ArticleCategory> implements WriteAbleArticleCategoryService {
    @Override
    public String getTable() {
        return "t_article_category";
    }

    @Override
    public ArticleCategory transferArticleCategory(ArticleCategory articleCategory) {
        try {
            ArticleCategory condition = new ArticleCategory();
            condition.setPreId(articleCategory.getPreId());
            condition.setName(articleCategory.getName());

            ArticleCategory existed = findOne(condition);
            if (existed == null){
                insert(articleCategory);
            } else {
                articleCategory.setId(existed.getId());
                LogUtils.debug(this.getClass(), "目录 ："+articleCategory.getName()+" 已经存在，跳过....");
            }
            return articleCategory;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArticleCategory findParent(String preId) {
        try {
            ArticleCategory condition = new ArticleCategory();
            condition.setId(preId);
            return findOne(condition);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getHistoryId(String categoryId, String rootId) {
        ArticleCategory category = findParent(categoryId);
        if (!rootId.equals(category.getPreId())) {
            return getHistoryId(category.getPreId(), rootId) + "_" + categoryId;
        } else {
            return rootId + "_" + categoryId;
        }
    }


}
