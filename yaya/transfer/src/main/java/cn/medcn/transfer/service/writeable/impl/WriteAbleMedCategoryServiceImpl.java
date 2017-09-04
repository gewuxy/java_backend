package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.Constants;
import cn.medcn.transfer.model.writeable.MedCategory;
import cn.medcn.transfer.model.writeable.MedicineSms;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMedCategoryService;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/8/21.
 */
public class WriteAbleMedCategoryServiceImpl extends WriteAbleBaseServiceImpl<MedCategory> implements WriteAbleMedCategoryService {
    @Override
    public String getTable() {
        return "t_data_category";
    }


    @Override
    public MedCategory transferCategory(MedCategory medCategory) {
        try {
            MedCategory condition = new MedCategory();
            condition.setPreId(medCategory.getPreId());
            condition.setName(medCategory.getName());
            MedCategory existed = findOne(condition);
            if (existed == null){
                insert(medCategory);
            } else {
                medCategory.setId(existed.getId());
                //LogUtils.debug(this.getClass(), "栏目 ："+medCategory.getName()+" 已经存在，跳过....");
            }
            return medCategory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public MedCategory findParent(String preId) {
        try {
            MedCategory condition = new MedCategory();
            condition.setId(preId);
            return findOne(condition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String getHistoryId(String categoryId, String rootId) {
        MedCategory category = findParent(categoryId);
        if (!rootId.equals(category.getPreId())) {
            return getHistoryId(category.getPreId(), rootId) + "_" + categoryId;
        } else {
            return rootId + "_" + categoryId;
        }
    }
}
