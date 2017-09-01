package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.MedCategory;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/8/21.
 */
public interface WriteAbleMedCategoryService extends WriteAbleBaseService<MedCategory> {

    MedCategory transferCategory(MedCategory medCategory) ;


    MedCategory findParent(String preId) ;

    String getHistoryId(String categoryId, String rootId) ;
}
