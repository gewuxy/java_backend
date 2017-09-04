package cn.medcn.user.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.user.dao.DepartmentDAO;
import cn.medcn.user.dao.HospitalDAO;
import cn.medcn.user.dto.HospitalLevelDTO;
import cn.medcn.user.model.Department;
import cn.medcn.user.model.Hospital;
import cn.medcn.user.service.HospitalService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by lixuan on 2017/4/24.
 */
@Service
public class HospitalServiceImpl extends BaseServiceImpl<Hospital> implements HospitalService {

    @Autowired
    private HospitalDAO hospitalDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private RedisCacheUtils<String> redisCacheUtils;

    @Override
    public Mapper<Hospital> getBaseMapper() {
        return hospitalDAO;
    }

    /**
     * 查询出所有的科室信息
     *
     * @return
     */
    @Override
    @Cacheable(value=MIDDLE_TIME_CACHE, key="'depart_category'")
    public List<String> findDepartCategory() {
        List<String> list =  departmentDAO.findAllCategory();
        return list;
    }

    /**
     * 根据市获取所有医院名称列表
     *
     * @param city
     * @return
     */
    @Override
    public List<String> findHospitals(String city) {
        if(StringUtils.isEmpty(city)){
            return Lists.newArrayList();
        }
        List<String> list = hospitalDAO.findHospitals(city);
        return list;
    }

    /**
     * 分页查询医院信息
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<Hospital> findByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<Hospital> page = MyPage.page2Mypage((Page) hospitalDAO.pageHospitals(pageable.getParams()));
        return page;
    }

    /**
     * 添加科室
     *
     * @param department
     */
    @Override
    @CacheEvict(value = MIDDLE_TIME_CACHE, key = DEPART_CACHE_KEY)
    public void addDepart(Department department) {
        departmentDAO.insert(department);
    }

    /**
     * 修改科室信息
     *
     * @param department
     */
    @Override
    @CacheEvict(value = MIDDLE_TIME_CACHE, key = DEPART_CACHE_KEY)
    public void updateDepart(Department department) {
        departmentDAO.updateByPrimaryKeySelective(department);
    }

    /**
     * 根据ID删除科室
     *
     * @param id
     */
    @Override
    @CacheEvict(value = MIDDLE_TIME_CACHE, key = DEPART_CACHE_KEY)
    public void deleteDepart(Integer id) {
        departmentDAO.deleteByPrimaryKey(id);
    }

    /**
     * 批量导入科室信息
     *
     * @param list
     */
    @Override
    public void executeBatchAddDepart(List<Department> list) {
        if(list!=null && !list.isEmpty()){
            for(Department department:list){
                departmentDAO.insert(department);
            }
        }
    }

    /**
     * 查询所有的科室信息
     *
     * @return
     */
    @Override
    @Cacheable(value = MIDDLE_TIME_CACHE, key = DEPART_CACHE_KEY)
    public List<Department> findAllDepart() {
        List<Department> list = departmentDAO.select(new Department());
        return list;
    }

    /**
     * 根据条件查询科室
     *
     * @param category
     * @return
     */
    @Override
    public List<Department> findDepartByCondition(String category) {
        Department condition = new Department();
        condition.setCategory(category);
        List<Department> list = departmentDAO.select(condition);
        return list;
    }

    /**
     * 根据医院名称获取医院信息
     *
     * @param hosName
     * @return
     */
    @Override
    public Hospital findByName(String hosName) {
        Hospital condition = new Hospital();
        condition.setName(hosName);
        List<Hospital> hospitals = hospitalDAO.select(condition);
        return hospitals.size()>0?hospitals.get(0):null;
    }

    /**
     * 查询所有医院级别
     *
     * @return
     */
    @Override
    public List<HospitalLevelDTO> findAllLevels() {
        return null;
    }

    /**
     * 获取医院科室，用map接收，科室第一级为key,科室第二级为value
     * @return
     */
    @Override
    public Map<String,List<String>> getAllDepart() {
        List<Department> list = findAllDepart();
        Map<String,List<String>> map = new HashMap<>();
        for(Department department:list){
            List<String> nameList = new ArrayList<>();
            map.put(department.getCategory(),nameList);
        }
        Set<String> set = map.keySet();
        for(String category:set){
            for(Department department:list){
                if(category.equals(department.getCategory())){
                    map.get(category).add(department.getName());
                }
            }
        }
        return map;
    }
}
