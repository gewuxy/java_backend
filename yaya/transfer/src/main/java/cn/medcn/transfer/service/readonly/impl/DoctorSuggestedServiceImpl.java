package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.DoctorSuggested;
import cn.medcn.transfer.model.writeable.Article;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.DoctorSuggestedService;
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
 * Created by Liuchangling on 2017/11/16.
 * 医师建议
 */

public class DoctorSuggestedServiceImpl extends ReadOnlyBaseServiceImpl<DoctorSuggested> implements DoctorSuggestedService {
    @Override
    public String getIdKey() {
        return "yid";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }

    protected int counter = 0;


    protected WriteAbleArticleCategoryService writeAbleArticleCategoryService = new WriteAbleArticleCategoryServiceImpl();

    protected WriteAbleArticleService writeAbleArticleService = new WriteAbleArticleServiceImpl();

    // 根据cid 目录id查询医师建议 当前目录下数据列表
    protected List<DoctorSuggested> findListByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select yid,title,cid,wzzy,keywords,author,imageurl,last_update_time,yshtml from t_ysjy where cid = ?";
        Object[] params = {cid};
        List<DoctorSuggested> list = (List<DoctorSuggested>) DAOUtils.selectList(getConnection(), sql, params, DoctorSuggested.class);
        return list;
    }


    // 转换医师建议文件数据
    @Override
    public void transferDoctorSuggestByCid(Long cid, String categoryId, String rootId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<DoctorSuggested> suggestedList = findListByCid(cid);

        if (suggestedList != null && suggestedList.size() != 0) {
            for (DoctorSuggested suggested : suggestedList) {
                Article article = Article.build(suggested, categoryId);
                if (article != null) {
                    article.setHistoryId(writeAbleArticleCategoryService.getHistoryId(categoryId, rootId));
                    writeAbleArticleService.insert(article);
                }
                counter ++;
                LogUtils.debug(this.getClass(), "成功共保存了医师建议文件 = "+counter+"个 ！！！");
            }
        }
    }

}
