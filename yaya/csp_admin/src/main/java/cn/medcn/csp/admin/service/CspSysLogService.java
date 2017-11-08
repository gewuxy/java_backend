package cn.medcn.csp.admin.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.csp.admin.model.CspSysLog;

/**
 * by create HuangHuibin 2017/11/7
 */
public interface CspSysLogService extends BaseService<CspSysLog>{

    MyPage<CspSysLog> findCspSysLog(Pageable pageable);

}
