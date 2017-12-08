package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspPackageInfoDAO;
import cn.medcn.user.model.CspPackageInfo;
import cn.medcn.user.service.CspPackageInfoService;
import com.github.abel533.mapper.Mapper;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public class CspPackageInfoServiceImpl extends BaseServiceImpl<CspPackageInfo> implements CspPackageInfoService {
    protected CspPackageInfoDAO packageInfoDAO;

    @Override
    public Mapper<CspPackageInfo> getBaseMapper() {
        return packageInfoDAO;
    }
}
