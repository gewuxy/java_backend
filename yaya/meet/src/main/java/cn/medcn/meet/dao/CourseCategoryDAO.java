package cn.medcn.meet.dao;

import cn.medcn.meet.model.CourseCategory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/10/25.
 */
public interface CourseCategoryDAO extends Mapper<CourseCategory> {

    List<CourseCategory> findByLevel(@Param("depth") Integer depth);
}
