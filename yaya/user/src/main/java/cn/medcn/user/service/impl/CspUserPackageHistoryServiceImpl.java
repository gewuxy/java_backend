package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dao.CspUserPackageHistoryDAO;
import cn.medcn.user.model.CspUserPackageHistory;
import cn.medcn.user.service.CspUserPackageHistoryService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/12/12.
 */
@Service
public class CspUserPackageHistoryServiceImpl extends BaseServiceImpl<CspUserPackageHistory> implements CspUserPackageHistoryService {

    @Autowired
    private CspUserPackageHistoryDAO packageHistoryDAO;

    @Override
    public Mapper<CspUserPackageHistory> getBaseMapper() {
        return packageHistoryDAO;
    }

    /**
     * 添加用户套餐信息历史
     *
     * @param userId
     * @param oldId
     * @param newId
     * @param updateType
     */
    @Override
    public void addUserHistoryInfo(String userId, Integer oldId, Integer newId, Integer updateType) {
        CspUserPackageHistory detail = new CspUserPackageHistory();
        detail.setBeforePackageId(oldId);
        detail.setUserId(userId);
        detail.setAfterPackageId(newId);
        detail.setId(StringUtils.nowStr());
        detail.setUpdateTime(new Date());
        detail.setUpdateType(updateType);
        packageHistoryDAO.insert(detail);
    }

    @Override
    public CspUserPackageHistory getLastHistoryByUserId(String userId) {
        return packageHistoryDAO.getLastHistoryByUserId(userId);
    }
}
