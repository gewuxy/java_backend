package cn.medcn.user.dao;

import cn.medcn.user.model.Department;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * Created by lixuan on 2017/4/24.
 */
public interface DepartmentDAO extends Mapper<Department> {
    List<String> findAllCategory();

    /**
     * 查找所有的科室
     * @return
     */
    List<String> findAlldepartment();
}
