package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.DepartmentReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferDepartmentService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/20.
 */
public class TransferDepartmentServiceImpl  extends ReadOnlyBaseServiceImpl<DepartmentReadOnly> implements TransferDepartmentService{

    @Override
    public String getTable() {
        return "t_department";
    }

    @Override
    public String getIdKey() {
        return "d_did";
    }

    /**
     * 获取科室数据
     * @param deptId
     * @return
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public DepartmentReadOnly getDepartment(Long deptId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        DepartmentReadOnly condition = new DepartmentReadOnly();
        condition.setD_did(deptId);
        DepartmentReadOnly readOnly = findOne(condition);
        return readOnly;
    }


}
