package cn.medcn.csp.admin.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.csp.admin.model.CspUserInfo;

/**
 * by create HuangHuibin 2017/11/8
 */
public interface CspUserService extends BaseService<CspUserInfo>{

    MyPage<CspUserInfo> findCspUserList(Pageable pageable);
}
