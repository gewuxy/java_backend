package cn.medcn.sys.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.sys.dao.SysPropertiesDAO;
import cn.medcn.sys.model.SystemProperties;
import cn.medcn.sys.service.SysPropertiesService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuLP on 2017/8/8/008.
 */
@Service
public class SystemPropertiesImpl extends BaseServiceImpl<SystemProperties> implements SysPropertiesService {

    @Autowired
    private SysPropertiesDAO sysPropertiesDAO;
    @Override
    public Mapper<SystemProperties> getBaseMapper() {
        return sysPropertiesDAO;
    }

}
