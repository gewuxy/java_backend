package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.Constants;
import cn.medcn.transfer.model.readonly.ClinicalGuideCategory;
import cn.medcn.transfer.model.writeable.MedCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.ClinicalGuideCategoryService;
import cn.medcn.transfer.service.readonly.ClinicalGuideService;
import cn.medcn.transfer.service.writeable.WriteAbleMedCategoryService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMedCategoryServiceImpl;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/8/22.
 */
public class ClinicalGuideCategoryServiceImpl extends ReadOnlyBaseServiceImpl<ClinicalGuideCategory> implements ClinicalGuideCategoryService {
    @Override
    public String getIdKey() {
        return "cid";
    }

    @Override
    public String getTable() {
        return "guide";
    }

    protected WriteAbleMedCategoryService writeAbleMedCategoryService = new WriteAbleMedCategoryServiceImpl();

    protected ClinicalGuideService clinicalGuideService = new ClinicalGuideServiceImpl();

    @Override
    public List<ClinicalGuideCategory> findRootCategoryList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return findByPid("0");
    }

    @Override
    public List<ClinicalGuideCategory> findByPid(String pid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ClinicalGuideCategory condition = new ClinicalGuideCategory();
        condition.setPid(pid);
        return findList(condition);
    }

    protected ClinicalGuideCategory findById(Long id) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ClinicalGuideCategory condition = new ClinicalGuideCategory();
        condition.setCid(id);
        ClinicalGuideCategory category = findOne(condition);
        return category;
    }

    @Override
    public void transferByCid(Long cid, String pid, Integer sort) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        //保存栏目本身
        LogUtils.debug(this.getClass(), "开始转换CID="+cid+"的栏目信息");
        ClinicalGuideCategory category = findById(cid);
        List<ClinicalGuideCategory> subList = findByPid(String.valueOf(cid));
        if (subList == null || subList.size() == 0){
            LogUtils.debug(this.getClass(), "当前为叶子节点, 开始执行数据内容转换 ....");
            if (category != null){

                MedCategory medCategory = MedCategory.build(category, true, pid, sort);
                writeAbleMedCategoryService.transferCategory(medCategory);
                LogUtils.debug(this.getClass(), "categoryId = "+medCategory.getId()+"的栏目保存成功 !!");

                LogUtils.debug(this.getClass(), "开始保存CID="+cid+" , categoryId="+medCategory.getId()+"的文件 ...");
                clinicalGuideService.transferByCid(cid, medCategory.getId(), Constants.CLINICAL_GUIDE_ROOT_CATEGORY_ID);
            }
        } else {
            String categoryId = null;
            if (category != null){
                MedCategory medCategory = MedCategory.build(category, false, pid, sort);
                writeAbleMedCategoryService.transferCategory(medCategory);
                categoryId = medCategory.getId();
            } else {
                categoryId = Constants.CLINICAL_GUIDE_ROOT_CATEGORY_ID;
            }
            LogUtils.debug(this.getClass(), "当前为非叶子节点, 循环执行子栏目保存 共"+subList.size()+"条子栏目 ....");
            for (int index = 0; index < subList.size(); index ++ ){
                transferByCid(subList.get(index).getCid(), categoryId, index+1);
            }
        }
    }

    @Override
    public void transfer() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException {
        transferByCid(0L, Constants.CLINICAL_GUIDE_ROOT_CATEGORY_ID, 1);
    }
}
