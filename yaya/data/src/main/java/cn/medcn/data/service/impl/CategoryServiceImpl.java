package cn.medcn.data.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.data.dao.CategoryDAO;
import cn.medcn.data.dto.DataCategoryDTO;
import cn.medcn.data.model.Category;
import cn.medcn.data.service.CategoryService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuan on 2017/5/18.
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public Mapper<Category> getBaseMapper() {
        return categoryDAO;
    }


    /**
     * 根据父ID获取下级栏目列表
     *
     * @param preId
     * @return
     */
    @Override
    public List<DataCategoryDTO> findCategories(String preId) {
        return categoryDAO.findCategories(preId);
    }
}
