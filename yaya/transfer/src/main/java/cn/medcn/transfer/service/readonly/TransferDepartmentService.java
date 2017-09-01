package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.DepartmentReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/20.
 */
public interface TransferDepartmentService extends ReadOnlyBaseService<DepartmentReadOnly> {
    /**
     * 获取用户科室数据
     * @param deptId
     * @return
     */
    DepartmentReadOnly getDepartment(Long deptId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
