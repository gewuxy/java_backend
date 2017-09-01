package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.AppMenuDAO;
import cn.medcn.user.dao.AppRoleMenuDAO;
import cn.medcn.user.model.AppMenu;
import cn.medcn.user.model.AppRoleMenu;
import cn.medcn.user.service.AppMenuService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuan on 2017/5/8.
 */
@Service
public class AppMenuServiceImpl extends BaseServiceImpl<AppMenu> implements AppMenuService {
    @Autowired
    private AppMenuDAO appMenuDAO;

    @Autowired
    private AppRoleMenuDAO appRoleMenuDAO;

    @Override
    public Mapper<AppMenu> getBaseMapper() {
        return appMenuDAO;
    }

    @Override
    @Cacheable(value=DEFAULT_CACHE, key = "'app_role_menu_'+#role")
    public List<AppMenu> findMenuByRole(Integer role) {
        List<AppMenu> menuList = appMenuDAO.findMenusByRole(role);
        return menuList;
    }

    /**
     * 根据id删除菜单以及所有子菜单
     *
     * @param id
     */
    @Override
    public void deleteMenu(Integer id) {
        appMenuDAO.deleteByPrimaryKey(id);
        AppMenu condition = new AppMenu();
        condition.setPreid(id);
        appMenuDAO.delete(condition);
    }

    /**
     * 按sort排序的所有菜单
     *
     * @return
     */
    @Override
    public List<AppMenu> findAll() {
        return appMenuDAO.findAll();
    }
}
