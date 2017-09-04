package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.HospitalLevelDTO;
import cn.medcn.user.model.Department;
import cn.medcn.user.model.Hospital;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/24.
 */
public interface HospitalService extends BaseService<Hospital> {

    String PROVINCE_CACHE_KEY = "cache_province";

    String CITY_CACHE_KEY = "cache_city_";

    String DEPART_CACHE_KEY = "'depart_key'";

    /**
     * 查询出所有的科室大类
     * @return
     */
    List<String> findDepartCategory();

    /**
     * 根据市获取所有医院名称列表
     * @param city
     * @return
     */
    List<String> findHospitals(String city);

    /**
     * 分页查询医院信息
     * @param pageable
     * @return
     */
    MyPage<Hospital> findByPage(Pageable pageable);

    /**
     * 添加科室
     * @param department
     */
    void addDepart(Department department);

    /**
     * 修改科室信息
     * @param department
     */
    void updateDepart(Department department);

    /**
     * 根据ID删除科室
     * @param id
     */
    void deleteDepart(Integer id);

    /**
     * 批量导入科室信息
     * @param list
     */
    void executeBatchAddDepart(List<Department> list);

    /**
     * 查询所有的科室信息
     * @return
     */
    List<Department> findAllDepart();

    /**
     * 根据条件查询科室
     * @param category
     * @return
     */
    List<Department> findDepartByCondition(String category);

    /**
     * 根据医院名称获取医院信息
     * @param hosName
     * @return
     */
    Hospital findByName(String hosName);

    /**
     * 查询所有医院级别
     * @return
     */
    List<HospitalLevelDTO> findAllLevels();

    /**
     * 获取医院科室，用map接收，科室第一级为key,科室第二级为value
     * @return
     */
    Map<String,List<String>> getAllDepart();
}
