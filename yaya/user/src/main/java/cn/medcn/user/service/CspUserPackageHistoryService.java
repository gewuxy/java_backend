package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspUserPackageHistory;

/**
 * Created by Liuchangling on 2017/12/12.
 */

public interface CspUserPackageHistoryService extends BaseService<CspUserPackageHistory> {
    void addUserHistoryInfo(String userId,Integer oldId,Integer newId,Integer updateType);

    CspUserPackageHistory getLastHistoryByUserId(String userId);
}
