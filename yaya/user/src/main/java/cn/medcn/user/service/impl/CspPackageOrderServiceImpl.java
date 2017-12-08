package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspPackageOrderDAO;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.service.CspPackageOrderService;
import com.github.abel533.mapper.Mapper;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public class CspPackageOrderServiceImpl extends BaseServiceImpl<CspPackageOrder> implements CspPackageOrderService {
    protected CspPackageOrderDAO packageOrderDAO;

    @Override
    public Mapper<CspPackageOrder> getBaseMapper() {
        return packageOrderDAO;
    }
}
