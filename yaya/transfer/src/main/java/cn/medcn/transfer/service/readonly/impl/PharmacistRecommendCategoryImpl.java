package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.PharmacistRecommendCategory;
import cn.medcn.transfer.model.writeable.ArticleCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.PharmacistRecommendCategoryService;
import cn.medcn.transfer.service.readonly.PharmacistRecommendService;
import cn.medcn.transfer.service.writeable.WriteAbleArticleCategoryService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleArticleCategoryServiceImpl;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static cn.medcn.transfer.Constants.PHARMACIST_RECOMMEND_ROOT_CATEGORY_ID;


/**
 * Created by Liuchangling on 2017/11/20.
 * 药师建议目录
 */

public class PharmacistRecommendCategoryImpl extends ReadOnlyBaseServiceImpl<PharmacistRecommendCategory> implements PharmacistRecommendCategoryService {
    @Override
    public String getIdKey() {
        return "cid";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }

    protected WriteAbleArticleCategoryService writeAbleArticleCategoryService = new WriteAbleArticleCategoryServiceImpl();

    protected PharmacistRecommendService pharmacistRecommendService = new PharmacistRecommendServiceImpl();

    @Override
    public List<PharmacistRecommendCategory> findRootCategory() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return findCategoryByPid("0");
    }

    @Override
    public List<PharmacistRecommendCategory> findCategoryByPid(String pid) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        PharmacistRecommendCategory condition = new PharmacistRecommendCategory();
        condition.setPid(pid);
        return findList(condition);
    }

    @Override
    public PharmacistRecommendCategory findCategoryByCid(Long cid) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        PharmacistRecommendCategory condition = new PharmacistRecommendCategory();
        condition.setCid(cid);
        return findOne(condition);
    }

    @Override
    public void transfer() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        // 先查询根目录数据
        List<PharmacistRecommendCategory> categoryList = findRootCategory();
        if (categoryList != null && categoryList.size() != 0) {
            for (PharmacistRecommendCategory category : categoryList) {
                transferByCid(category.getCid(), PHARMACIST_RECOMMEND_ROOT_CATEGORY_ID, 1);
            }
        }
    }

    // 转换药师建议数据
    @Override
    public void transferByCid(Long cid, String preId, Integer sort) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        // 保存栏目本身
        LogUtils.debug(this.getClass(), "开始转换药师建议 cId = "+cid+" 的目录信息");

        PharmacistRecommendCategory recommendCategory = findCategoryByCid(cid);

        List<PharmacistRecommendCategory> categoryList = findCategoryByPid(String.valueOf(cid));

        boolean leaf = false;
        if (categoryList != null && categoryList.size() > 0){
            String categoryId = null;
            if (recommendCategory != null) {
                ArticleCategory articleCategory = ArticleCategory.build(recommendCategory, leaf, preId, sort);
                writeAbleArticleCategoryService.transferArticleCategory(articleCategory);
                categoryId = articleCategory.getId();
            } else {
                categoryId = PHARMACIST_RECOMMEND_ROOT_CATEGORY_ID;
            }

            LogUtils.debug(this.getClass(), "当前为父节点, 循环执行子目录 保存 共"+categoryList.size()+"条子目录 ....");

            for (int i = 0; i < categoryList.size(); i++){
                PharmacistRecommendCategory recommend = categoryList.get(i);
                transferByCid(recommend.getCid(), categoryId, i+1);
            }

        } else {
            LogUtils.debug(this.getClass(), "当前为叶子节点, 开始执行数据内容转换 ....");
            if (recommendCategory != null) {
                leaf = true;
                ArticleCategory articleCategory = ArticleCategory.build(recommendCategory, leaf, preId, sort);
                writeAbleArticleCategoryService.transferArticleCategory(articleCategory);
                LogUtils.debug(this.getClass(), "categoryId = "+articleCategory.getId()+" 下的目录保存成功 !!");

                LogUtils.debug(this.getClass(), "开始转换医师建议数据内容 保存cId = "+cid+" , categoryId="+articleCategory.getId()+"目录下的文件 ...");
                // 转换药师建议数据
                pharmacistRecommendService.transferPharmacistRecommendByCid(cid, articleCategory.getId(), PHARMACIST_RECOMMEND_ROOT_CATEGORY_ID);
            }
        }
    }
}
