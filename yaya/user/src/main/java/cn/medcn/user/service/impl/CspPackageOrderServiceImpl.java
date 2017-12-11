package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspPackageOrderDAO;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.service.CspPackageOrderService;
import com.github.abel533.mapper.Mapper;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/12/8.
 */
@Service
public class CspPackageOrderServiceImpl extends BaseServiceImpl<CspPackageOrder> implements CspPackageOrderService {
    protected CspPackageOrderDAO packageOrderDAO;

    @Override
    public Mapper<CspPackageOrder> getBaseMapper() {
        return packageOrderDAO;
    }
}

