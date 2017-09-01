package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.Constants;
import cn.medcn.transfer.model.readonly.MedClass;
import cn.medcn.transfer.model.writeable.MedCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.MedClassService;
import cn.medcn.transfer.service.readonly.MedsmsService;
import cn.medcn.transfer.service.writeable.WriteAbleMedCategoryService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMedCategoryServiceImpl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lixuan on 2017/8/21.
 */
public class MedClassServiceImpl extends ReadOnlyBaseServiceImpl<MedClass> implements MedClassService {
    @Override
    public String getIdKey() {
        return "cid";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }

    protected MedsmsService medsmsService = new MedsmsServiceImpl();

    protected WriteAbleMedCategoryService writeAbleMedCategoryService = new WriteAbleMedCategoryServiceImpl();


    @Override
    public List<MedClass> findRootMedClasses() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return findMedClassesByPid(0L);
    }

    @Override
    public List<MedClass> findMedClassesByPid(Long pid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MedClass condition = new MedClass();
        condition.setPid("0");
        List<MedClass> list = findList(condition);
        return list;
    }


    @Override
    public void transfer() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        //查询出跟目录
        List<MedClass> rootMedClasses = findRootMedClasses();
        if (rootMedClasses != null && rootMedClasses.size() > 0){
            for (MedClass medClass : rootMedClasses){
                transferByCid(medClass.getCid(), Constants.MEDICINE_MANUAL_ROOT_CATEGORY_ID);
            }
        }
    }

    @Override
    public void transferByCid(Long cid, String preId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        //保存栏目本身
        MedClass medClass = findMedClass(cid);
        MedCategory medCategory = null;
        List<MedClass> medClassList = findMedClassesByPid(cid);
        boolean leaf = false;
        if (medClassList != null && medClassList.size() > 0){
            medCategory = writeAbleMedCategoryService.transferCategory(MedCategory.build(medClass, leaf, preId));
            for (MedClass mc : medClassList){
                transferByCid(mc.getCid(), medCategory.getId());
            }
        } else {
            leaf = true;
            writeAbleMedCategoryService.transferCategory(MedCategory.build(medClass, leaf, preId));
            medsmsService.transferByCid(cid);
        }
    }

    @Override
    public MedClass findMedClass(Long id) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MedClass condition = new MedClass();
        condition.setCid(id);
        return findOne(condition);
    }


}
