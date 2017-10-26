package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.model.CourseCategory;

import java.util.List;

/**
 * Created by lixuan on 2017/10/25.
 */
public interface CourseCategoryService extends BaseService<CourseCategory> {
    /**
     * 获取所有栏目
     * @return
     */
    List<CourseCategory> findAll();

    /**
     * 根据level获取所有栏目
     * @param level
     * @return
     */
    List<CourseCategory> findByLevel(Integer level);
}
