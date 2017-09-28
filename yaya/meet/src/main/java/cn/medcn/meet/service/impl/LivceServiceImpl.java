package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.meet.dao.LiveDAO;
import cn.medcn.meet.dto.LiveOrderDTO;
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

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Override
    public Mapper<Live> getBaseMapper() {
        return liveDAO;
    }

    @Override
    public void publish(LiveOrderDTO dto) {
        redisCacheUtils.publish(CSP_LIVE_TOPIC_KEY, dto);
    }

    @Override
    public Live findByCourseId(Integer courseId) {
        Live cond = new Live();
        cond.setCourseId(courseId);
        return liveDAO.selectOne(cond);
    }
}
