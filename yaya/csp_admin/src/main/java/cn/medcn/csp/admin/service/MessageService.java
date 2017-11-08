package cn.medcn.csp.admin.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.csp.admin.model.CspAdminMessage;


public interface MessageService extends BaseService<CspAdminMessage>{
    MyPage<CspAdminMessage> findMessageListByPage(Pageable pageable);
}
