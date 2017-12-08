package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspPackageDAO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.service.CspPackageService;
import com.github.abel533.mapper.Mapper;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public class CspPackageServiceImpl extends BaseServiceImpl<CspPackage> implements CspPackageService {
    protected CspPackageDAO cspPackageDAO;

    @Override
    public Mapper<CspPackage> getBaseMapper() {
        return cspPackageDAO;
    }
}
