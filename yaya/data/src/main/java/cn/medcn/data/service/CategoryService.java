package cn.medcn.data.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.data.dto.DataCategoryDTO;
import cn.medcn.data.model.Category;

import java.util.List;

/**
 * Created by lixuan on 2017/5/18.
 */
public interface CategoryService extends BaseService<Category>{

    /**
     * 根据父ID获取下级栏目列表
     * @param preId
     * @return
     */
    List<DataCategoryDTO> findCategories(String preId);
}
