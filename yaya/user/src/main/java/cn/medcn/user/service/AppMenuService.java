package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.AppMenu;

import java.util.List;

/**
 * Created by lixuan on 2017/5/8.
 */
public interface AppMenuService extends BaseService<AppMenu> {
    /**
     * 根据角色查询所有的菜单
     * @param role
     * @return
     */
    List<AppMenu> findMenuByRole(Integer role);

    /**
     * 根据id删除菜单以及所有子菜单
     * @param id
     */
    void deleteMenu(Integer id);

    /**
     * 按sort排序的所有菜单
     * @return
     */
    List<AppMenu> findAll();
}
