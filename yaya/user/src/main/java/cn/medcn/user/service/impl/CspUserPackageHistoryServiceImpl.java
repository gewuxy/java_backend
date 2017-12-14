package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspUserPackageHistoryDAO;
import cn.medcn.user.model.CspUserPackageHistory;
import cn.medcn.user.service.CspUserPackageHistoryService;
import com.github.abel533.mapper.Mapper;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/12/12.
 */
@Service
public class CspUserPackageHistoryServiceImpl extends BaseServiceImpl<CspUserPackageHistory> implements CspUserPackageHistoryService {

    private CspUserPackageHistoryDAO packageHistoryDAO;

    @Override
    public Mapper<CspUserPackageHistory> getBaseMapper() {
        return packageHistoryDAO;
    }
}
