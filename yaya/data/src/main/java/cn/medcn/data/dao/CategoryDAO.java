package cn.medcn.data.dao;

import cn.medcn.data.dto.DataCategoryDTO;
import cn.medcn.data.model.Category;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/5/18.
 */
public interface CategoryDAO extends Mapper<Category> {

    List<DataCategoryDTO> findCategories(@Param("preId")String preId);
}
