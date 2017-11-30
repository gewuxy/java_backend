package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.DepartmentDAO;
import cn.medcn.user.model.Department;
import cn.medcn.user.service.DepartmentService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 11:30 2017/11/28
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department>implements DepartmentService{

    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public Mapper<Department> getBaseMapper() {
        return departmentDAO;
    }

    @Override
    public List<String> findAlldepartment() {
        return departmentDAO.findAlldepartment();
    }
}
