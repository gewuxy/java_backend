package cn.medcn.official.service.Impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.official.dao.OffSearchDao;
import cn.medcn.official.model.OffSearch;
import cn.medcn.official.service.OffiSearchService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * by create HuangHuibin 2017/11/15
 */
@Service
public class OffiSearchServiceImpl extends BaseServiceImpl<OffSearch> implements OffiSearchService{

    @Autowired
    private OffSearchDao offSearchDao;

    @Override
    public Mapper<OffSearch> getBaseMapper() {
        return offSearchDao;
    }


    @Override
    public List<OffSearch> findTopHost(Integer searchType) {
        List<OffSearch> list = offSearchDao.findTopHost(searchType);
        return list;
    }
}
