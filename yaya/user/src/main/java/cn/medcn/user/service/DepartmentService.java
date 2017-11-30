package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.Department;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 11:29 2017/11/28
 */
public interface DepartmentService extends BaseService<Department>{
    /**
     * 查找所有的科室
     * @return
     */
    List<String> findAlldepartment();

}
