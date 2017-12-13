package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspUserPackageDetail;

/**
 * Created by Liuchangling on 2017/12/12.
 */

public interface CspUserPackageDetailService extends BaseService<CspUserPackageDetail> {
    void addUserHistoryInfo(String userId,Integer oldId,Integer newId,Integer updateType);
}
