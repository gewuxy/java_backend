package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.LiveRecordDAO;
import cn.medcn.meet.model.LiveRecord;
import cn.medcn.meet.service.LiveRecordService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/9/26.
 */
@Service
public class LiveRecordServiceImpl extends BaseServiceImpl<LiveRecord> implements LiveRecordService {


    @Autowired
    protected LiveRecordDAO liveRecordDAO;

    @Override
    public Mapper<LiveRecord> getBaseMapper() {
        return liveRecordDAO;
    }
}
