package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.Constants;
import cn.medcn.transfer.model.readonly.SymptomReadOnly;
import cn.medcn.transfer.model.writeable.ArticleCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.SymptomService;
import cn.medcn.transfer.service.writeable.WriteAbleArticleCategoryService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleArticleCategoryServiceImpl;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/11/16.
 * 对症找药
 */

public class SymptomServiceImpl extends ReadOnlyBaseServiceImpl<SymptomReadOnly> implements SymptomService {
    @Override
    public String getIdKey() {
        return "cid";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }

    protected WriteAbleArticleCategoryService writeAbleArticleService = new WriteAbleArticleCategoryServiceImpl();

    /**
     * 查询根目录数据
     * @return
     */
    @Override
    public List<SymptomReadOnly> findRootSymptomList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return findSymptomByPid(0L);
    }

    /**
     * 根据父节点id 查询下级目录
     * @param pid
     * @return
     */
    @Override
    public List<SymptomReadOnly> findSymptomByPid(Long pid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SymptomReadOnly symptom = new SymptomReadOnly();
        symptom.setPid(pid.toString());
        return findList(symptom);
    }

    /**
     * 根据自身id 查询是否有该目录
     * @param cid
     * @return
     */
    @Override
    public SymptomReadOnly findSymptomByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SymptomReadOnly symptom = new SymptomReadOnly();
        symptom.setCid(cid);
        return findOne(symptom);
    }


    @Override
    public void transfer() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // 先查询根目录数据
        List<SymptomReadOnly> rootSymptom = findRootSymptomList();

        if (rootSymptom != null && rootSymptom.size() != 0) {
            for (SymptomReadOnly symptom : rootSymptom) {
                transferByCid(symptom.getCid(), Constants.SYMPTOM_ROOT_CATEGORY_ID, 1);
            }
        }
    }


    @Override
    public void transferByCid(Long cid, String preId, Integer sort) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        //保存栏目本身
        LogUtils.debug(this.getClass(), "开始转换对症找药 CID="+cid+" 的目录信息");
        SymptomReadOnly symptom = findSymptomByCid(cid);

        ArticleCategory category = null;

        List<SymptomReadOnly> symptomList = findSymptomByPid(cid);

        boolean leaf = false;
        if (symptomList != null && symptomList.size() > 0){
            category = writeAbleArticleService.transferArticleCategory(category.build(symptom, leaf, preId, sort));
            for (int i = 0; i < symptomList.size(); i++){
                SymptomReadOnly sym = symptomList.get(i);
                transferByCid(sym.getCid(), category.getId(), i+1);
            }
        } else {
            leaf = true;
            writeAbleArticleService.transferArticleCategory(category.build(symptom, leaf, preId, sort));
        }
    }


}
