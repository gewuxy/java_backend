package cn.medcn.csp.admin.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.csp.admin.model.CspSysUser;

/**
 * by create HuangHuibin 2017/11/3
 */
public interface CspSysUserService extends BaseService<CspSysUser> {

    MyPage<CspSysUser> findCspSysUser(Pageable pageable);
}
