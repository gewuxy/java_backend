package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspUserPackageDAO;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspUserPackageService;
import com.github.abel533.mapper.Mapper;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public class CspUserPackageServiceImpl extends BaseServiceImpl<CspUserPackage> implements CspUserPackageService {
    protected CspUserPackageDAO userPackageDAO;

    @Override
    public Mapper<CspUserPackage> getBaseMapper() {
        return userPackageDAO;
    }
}
