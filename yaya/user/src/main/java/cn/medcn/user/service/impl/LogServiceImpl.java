package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.user.dao.AppLogDAO;
import cn.medcn.user.dao.CspLogDAO;
import cn.medcn.user.model.AppLog;
import cn.medcn.user.model.CspLog;
import cn.medcn.user.service.LogService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/5/2.
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<AppLog> implements LogService {

    @Autowired
    private AppLogDAO appLogDAO;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    protected CspLogDAO cspLogDAO;


    @Override
    public Mapper<AppLog> getBaseMapper() {
        return appLogDAO;
    }

    @Override
    public void pushToQueue(AppLog log) {
        redisCacheUtils.pushToQueue(LOG_QUEUE_NAME, log);
    }


    @Override
    public AppLog bpopFromQueue() {
        AppLog log = (AppLog) redisCacheUtils.bRPopFromQueue(LOG_QUEUE_NAME);
        return log;
    }

    @Override
    public void saveCspLog(CspLog cspLog) {
        cspLogDAO.insert(cspLog);
    }
}
