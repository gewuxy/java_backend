package cn.medcn.sys.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.sys.model.SystemUser;

/**
 * Created by lixuan on 2017/5/2.
 */
public interface SystemUserService extends BaseService<SystemUser>{

    /**
     * 获取用户信息 包含角色信息
     * @param userId
     * @return
     */
    SystemUser findUserHasRole(Integer userId);
}
