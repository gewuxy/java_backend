package cn.medcn.sys.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.sys.model.SystemLog;

/**
 * by create HuangHuibin 2017/11/23
 */
public interface SystemLogService extends BaseService<SystemLog>{

    MyPage<SystemLog> findLogByPage(Pageable pageable);
}
