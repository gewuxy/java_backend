package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.CourseCategoryDAO;
import cn.medcn.meet.model.CourseCategory;
import cn.medcn.meet.service.CourseCategoryService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuan on 2017/10/25.
 */
@Service
public class CourseCategoryServiceImpl extends BaseServiceImpl<CourseCategory> implements CourseCategoryService {

    @Autowired
    protected CourseCategoryDAO courseCategoryDAO;

    @Override
    public Mapper<CourseCategory> getBaseMapper() {
        return courseCategoryDAO;
    }

    /**
     * 获取所有栏目
     *
     * @return
     */
    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'csp_course_category_list'")
    public List<CourseCategory> findAll() {
        List<CourseCategory> rootList = findByLevel(CourseCategory.CategoryDepth.root.depth);
        if (rootList != null) {
            List<CourseCategory> subList = findByLevel(CourseCategory.CategoryDepth.sub.depth);
            for (CourseCategory root : rootList) {
                for (CourseCategory  sub : subList) {
                    if (sub.getParentId() != null && sub.getParentId().intValue() == root.getId().intValue()) {
                        root.addSub(sub);
                    }
                }
            }
        }
        return rootList;
    }

    /**
     * 根据depth获取所有栏目
     *
     * @param depth
     * @return
     */
    @Override
    public List<CourseCategory> findByLevel(Integer depth) {
        return courseCategoryDAO.findByLevel(depth);
    }

    /**
     * 查询包含父级内容的栏目
     *
     * @param id
     * @return
     */
    @Override
    public CourseCategory findCategoryHasParent(Integer id) {
        CourseCategory courseCategory = courseCategoryDAO.selectByPrimaryKey(id);
        if (courseCategory != null) {
            CourseCategory parent = courseCategoryDAO.selectByPrimaryKey(courseCategory.getParentId());
            courseCategory.setParent(parent);
        }
        return courseCategory;
    }
}
