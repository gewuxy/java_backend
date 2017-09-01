package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.HospitalReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/20.
 */
public interface TransferHospitalService extends ReadOnlyBaseService<HospitalReadOnly> {
    /**
     * 获取用户医院数据
     * @param hosId
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    HospitalReadOnly getHospital(Long hosId)throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException ;

}
