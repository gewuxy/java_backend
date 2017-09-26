package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.LiveDAO;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.service.LiveService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/9/26.
 */
@Service
public class LivceServiceImpl extends BaseServiceImpl<Live> implements LiveService {

    @Autowired
    protected LiveDAO liveDAO;

    @Override
    public Mapper<Live> getBaseMapper() {
        return liveDAO;
    }
}
