package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspUserPackageDetailDAO;
import cn.medcn.user.model.CspUserPackageDetail;
import cn.medcn.user.service.CspUserPackageDetailService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/12/12.
 */
@Service
public class CspUserPackageDetailServiceImpl extends BaseServiceImpl<CspUserPackageDetail> implements CspUserPackageDetailService {

    @Autowired
    private CspUserPackageDetailDAO packageDetailDAO;

    @Override
    public Mapper<CspUserPackageDetail> getBaseMapper() {
        return packageDetailDAO;
    }
}
