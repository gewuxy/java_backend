package cn.medcn.article.service.impl;

import cn.medcn.article.dao.CspAppVideoDAO;
import cn.medcn.article.model.AppVideo;
import cn.medcn.article.service.CspAppVideoService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/10/30.
 */
@Service
public class CspAppVideoServiceImpl extends BaseServiceImpl<AppVideo> implements CspAppVideoService {

    @Autowired
    protected CspAppVideoDAO appVideoDAO;

    @Override
    public Mapper<AppVideo> getBaseMapper() {
        return appVideoDAO;
    }

    @Autowired
    public AppVideo findCspAppVideo(){
        return null;
    }
}
