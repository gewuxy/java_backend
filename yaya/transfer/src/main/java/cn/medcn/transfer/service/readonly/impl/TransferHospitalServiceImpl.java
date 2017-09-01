package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.HospitalReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferHospitalService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/20.
 */
public class TransferHospitalServiceImpl extends ReadOnlyBaseServiceImpl<HospitalReadOnly> implements TransferHospitalService{

    @Override
    public String getTable() {
        return "t_hospital";
    }


    @Override
    public String getIdKey() {
        return "h_id";
    }

    /**
     * 获取医院数据
     * @param hosId
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public HospitalReadOnly getHospital(Long hosId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        HospitalReadOnly condition = new HospitalReadOnly();
        condition.setH_id(hosId);
        HospitalReadOnly hospitalReadOnly = findOne(condition);
        return hospitalReadOnly;
    }

}
