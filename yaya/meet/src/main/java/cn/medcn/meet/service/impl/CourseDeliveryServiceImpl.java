package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.CourseDeliveryDAO;
import cn.medcn.meet.model.CourseDelivery;
import cn.medcn.meet.service.CourseDeliveryService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/9/26.
 */
@Service
public class CourseDeliveryServiceImpl extends BaseServiceImpl<CourseDelivery> implements CourseDeliveryService {

    @Autowired
    protected CourseDeliveryDAO courseDeliveryDAO;

    @Override
    public Mapper<CourseDelivery> getBaseMapper() {
        return courseDeliveryDAO;
    }
}
