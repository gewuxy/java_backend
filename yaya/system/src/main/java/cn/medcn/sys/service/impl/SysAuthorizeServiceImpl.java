package cn.medcn.sys.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.sys.dao.SysAuthorizeDAO;
import cn.medcn.sys.model.SystemAuthorize;
import cn.medcn.sys.service.SysAuthorizeService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/9/25.
 */
@Service
public class SysAuthorizeServiceImpl extends BaseServiceImpl<SystemAuthorize> implements SysAuthorizeService {

    @Autowired
    protected SysAuthorizeDAO sysAuthorizeDAO;

    @Override
    public Mapper<SystemAuthorize> getBaseMapper() {
        return sysAuthorizeDAO;
    }
}
