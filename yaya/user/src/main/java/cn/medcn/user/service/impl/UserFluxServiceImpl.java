package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.UserFluxDAO;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.service.UserFluxService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/10/26.
 */
@Service
public class UserFluxServiceImpl extends BaseServiceImpl<UserFlux> implements UserFluxService {

    @Autowired
    protected UserFluxDAO userFluxDAO;

    @Override
    public Mapper<UserFlux> getBaseMapper() {
        return userFluxDAO;
    }

    @Override
    public UserFlux findByCourseId(Integer courseId) {
        return userFluxDAO.findByCourseId(courseId);
    }
}
