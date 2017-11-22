package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.PharmacistRecommend;
import cn.medcn.transfer.model.writeable.Article;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.PharmacistRecommendService;
import cn.medcn.transfer.service.writeable.WriteAbleArticleCategoryService;
import cn.medcn.transfer.service.writeable.WriteAbleArticleService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleArticleCategoryServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleArticleServiceImpl;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/11/21.
 */

public class PharmacistRecommendServiceImpl extends ReadOnlyBaseServiceImpl<PharmacistRecommend> implements PharmacistRecommendService {
    @Override
    public String getIdKey() {
        return "deid";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }

    protected WriteAbleArticleCategoryService writeAbleArticleCategoryService = new WriteAbleArticleCategoryServiceImpl();

    protected WriteAbleArticleService writeAbleArticleService = new WriteAbleArticleServiceImpl();

    protected int counter;

    protected List<PharmacistRecommend> findListByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select deid,title,last_update_time,cid,yashtml from drugeducation where cid = ?";
        Object[] params = {cid};
        List<PharmacistRecommend> list = (List<PharmacistRecommend>) DAOUtils.selectList(getConnection(), sql, params, PharmacistRecommend.class);
        return list;
    }

    @Override
    public void transferPharmacistRecommendByCid(Long cid, String categoryId, String rootId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<PharmacistRecommend> recommendList = findListByCid(cid);
        if (recommendList != null && recommendList.size() != 0) {
            for (PharmacistRecommend recommend : recommendList) {
                Article article = Article.build(recommend, categoryId);
                if (article != null) {
                    article.setHistoryId(writeAbleArticleCategoryService.getHistoryId(categoryId, rootId));
                    writeAbleArticleService.insert(article);
                }
                counter ++;
                LogUtils.debug(this.getClass(), "成功共保存了药师建议文件 = "+counter+"个 ！！！");
            }
        }

    }
}
