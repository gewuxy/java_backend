package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MedClass;
import cn.medcn.transfer.model.writeable.MedCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lixuan on 2017/8/21.
 */
public interface MedClassService extends ReadOnlyBaseService<MedClass> {

    String TABLE_NAME = "medclass";


    List<MedClass> findRootMedClasses() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;


    List<MedClass> findMedClassesByPid(Long pid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transfer() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;


    void transferByCid(Long cid, String preId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    MedClass findMedClass(Long id) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

}
