package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.Medsms;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lixuan on 2017/8/21.
 */
public interface MedsmsService extends ReadOnlyBaseService<Medsms> {

    String TABLE_NAME = "t_medicinesms";


    List<Medsms> findSmsByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;


    void transferByCid(Long cid);
}
