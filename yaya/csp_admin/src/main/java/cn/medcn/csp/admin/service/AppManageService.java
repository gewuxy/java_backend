package cn.medcn.csp.admin.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.AppVersion;

/**
 * @Author：jianliang
 * @Date: Creat in 12:05 2017/11/10
 */
public interface AppManageService extends BaseService<AppVersion>{

    MyPage<AppVersion> findappManageListByPage(Pageable pageable);

    AppVersion selectFirstApp();

}
