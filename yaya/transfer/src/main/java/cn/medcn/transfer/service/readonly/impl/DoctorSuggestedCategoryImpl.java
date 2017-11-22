package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.DoctorSuggestedCategory;
import cn.medcn.transfer.model.writeable.ArticleCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.DoctorSuggestedCategoryService;
import cn.medcn.transfer.service.readonly.DoctorSuggestedService;
import cn.medcn.transfer.service.writeable.WriteAbleArticleCategoryService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleArticleCategoryServiceImpl;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static cn.medcn.transfer.Constants.DOCTOR_SUGGESTED_ROOT_CATEGORY_ID;

/**
 * Created by Liuchangling on 2017/11/16.
 * 医师建议目录
 */

public class DoctorSuggestedCategoryImpl extends ReadOnlyBaseServiceImpl<DoctorSuggestedCategory> implements DoctorSuggestedCategoryService {
    @Override
    public String getIdKey() {
        return "cid";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }

    protected WriteAbleArticleCategoryService writeAbleArticleCategoryService = new WriteAbleArticleCategoryServiceImpl();

    protected DoctorSuggestedService doctorSuggestedService = new DoctorSuggestedServiceImpl();

    @Override
    public List<DoctorSuggestedCategory> findRootCategory() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return findCategoryByPid("0");
    }

    @Override
    public List<DoctorSuggestedCategory> findCategoryByPid(String pid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        DoctorSuggestedCategory condition = new DoctorSuggestedCategory();
        condition.setPid(pid);
        return findList(condition);
    }

    @Override
    public DoctorSuggestedCategory findCategoryByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        DoctorSuggestedCategory condition = new DoctorSuggestedCategory();
        condition.setCid(cid);
        return findOne(condition);
    }

    @Override
    public void transfer() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // 先查询根目录数据
        List<DoctorSuggestedCategory> categoryList = findRootCategory();
        if (categoryList != null && categoryList.size() != 0) {
            for (DoctorSuggestedCategory category : categoryList) {
                transferByCid(category.getCid(), DOCTOR_SUGGESTED_ROOT_CATEGORY_ID, 1);
            }
        }
    }

    // 转换医师建议数据
    @Override
    public void transferByCid(Long cid, String preId, Integer sort) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        // 保存栏目本身
        LogUtils.debug(this.getClass(), "开始转换医师建议目录 cId = "+cid+" 的目录信息");

        DoctorSuggestedCategory suggestedCategory = findCategoryByCid(cid);

        List<DoctorSuggestedCategory> categoryList = findCategoryByPid(String.valueOf(cid));

        boolean leaf = false;
        if (categoryList != null && categoryList.size() > 0){
            String categoryId = null;
            if (suggestedCategory != null) {
                ArticleCategory articleCategory = ArticleCategory.build(suggestedCategory, leaf, preId, sort);
                writeAbleArticleCategoryService.transferArticleCategory(articleCategory);
                categoryId = articleCategory.getId();
            } else {
                categoryId = DOCTOR_SUGGESTED_ROOT_CATEGORY_ID;
            }
            LogUtils.debug(this.getClass(), "当前为非叶子节点, 循环执行子栏目保存 共"+categoryList.size()+"条子栏目 ....");
            for (int i = 0; i < categoryList.size(); i++){
                DoctorSuggestedCategory suggest = categoryList.get(i);
                transferByCid(suggest.getCid(), categoryId, i+1);
            }

        } else {
            LogUtils.debug(this.getClass(), "当前为叶子节点, 开始执行数据内容转换 ....");
            if (suggestedCategory != null) {
                leaf = true;
                ArticleCategory articleCategory = ArticleCategory.build(suggestedCategory, leaf, preId, sort);
                writeAbleArticleCategoryService.transferArticleCategory(articleCategory);
                LogUtils.debug(this.getClass(), "categoryId = "+articleCategory.getId()+"的栏目保存成功 !!");

                LogUtils.debug(this.getClass(), "开始转换医师建议数据内容 保存cId= "+cid+" , categoryId="+articleCategory.getId()+"目录下的文件 ...");
                // 转换医师建议数据
                doctorSuggestedService.transferDoctorSuggestByCid(cid, articleCategory.getId(), DOCTOR_SUGGESTED_ROOT_CATEGORY_ID);
            }
        }
    }
}
