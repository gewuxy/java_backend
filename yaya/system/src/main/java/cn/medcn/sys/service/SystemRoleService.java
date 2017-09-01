package cn.medcn.sys.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.sys.model.SystemRole;

/**
 * Created by lixuan on 2017/5/2.
 */
public interface SystemRoleService extends BaseService<SystemRole>{

    /**
     * 分配角色权限
     * @param roleId
     * @param menuIds
     */
    void insertRoleMenu(Integer roleId, Integer[] menuIds);
}
