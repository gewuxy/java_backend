package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.DoctorSuggested;
import cn.medcn.transfer.model.readonly.SymptomReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/11/16.
 */

public interface SymptomService extends ReadOnlyBaseService<SymptomReadOnly> {
    // 对症找药
    String TABLE_NAME = "t_findmed";

    List<SymptomReadOnly> findRootSymptomList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    List<SymptomReadOnly> findSymptomByPid(Long pid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    SymptomReadOnly findSymptomByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transfer() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transferByCid(Long cid, String preId, Integer sort) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
