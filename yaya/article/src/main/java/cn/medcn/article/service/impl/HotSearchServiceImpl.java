package cn.medcn.article.service.impl;

import cn.medcn.article.dao.HotSearchDAO;
import cn.medcn.article.model.HotSearch;
import cn.medcn.article.service.HotSearchService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * by create HuangHuibin 2017/12/8
 */
@Service
public class HotSearchServiceImpl extends BaseServiceImpl<HotSearch> implements HotSearchService{

    @Autowired
    private HotSearchDAO hotSearchDAO;

    @Override
    public Mapper<HotSearch> getBaseMapper() {
        return hotSearchDAO;
    }

    @Override
    public List<HotSearch> findTopHost(String categoryId) {
        return  hotSearchDAO.findTopHost(categoryId);
    }
}
