package cn.medcn.sys.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.BaseService;
import cn.medcn.sys.model.SystemMenu;

import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
public interface SysMenuService extends BaseService<SystemMenu>{

    /**
     * 获取超级管理员菜单
     * @return
     */
    List<SystemMenu> findRootMenus()  throws SystemException;

    /**
     * 根据角色ID获取菜单
     * @param roleId
     * @return
     */
    List<SystemMenu> findMenusByRole(Integer roleId)  throws SystemException;

    /**
     * 获取父ID下面的所有子菜单
     * @param preid
     * @return
     */
    List<SystemMenu> findAllSubMenus(Integer preid)  throws SystemException;

    /**
     * 获取菜单信息 包含父菜单信息
     * @param id
     * @return
     */
    SystemMenu findMenuHasParent(Integer id);

    /**
     * 获取角色子菜单
     * @param roleId
     * @param preid
     * @return
     */
    List<SystemMenu> findSubMenusByRole(Integer roleId, Integer preid);

    /**
     * 删除菜单以及子菜单
     * @param id
     */
    void deleteMenu(Integer id);
}
