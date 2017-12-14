package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspPackageInfoDAO;
import cn.medcn.user.model.CspPackageInfo;
import cn.medcn.user.service.CspPackageInfoService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/12/8.
 */
@Service
public class CspPackageInfoServiceImpl extends BaseServiceImpl<CspPackageInfo> implements CspPackageInfoService {

    @Autowired
    protected CspPackageInfoDAO packageInfoDAO;

    @Override
    public Mapper<CspPackageInfo> getBaseMapper() {
        return packageInfoDAO;
    }
}
