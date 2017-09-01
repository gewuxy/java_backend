package cn.medcn.sys.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.sys.dao.SysMenuDAO;
import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.service.SysMenuService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SystemMenu> implements SysMenuService {

    @Autowired
    private SysMenuDAO sysMenuDAO;

    @Override
    public Mapper<SystemMenu> getBaseMapper() {
        return sysMenuDAO;
    }


    /**
     * 获取超级管理员菜单
     *
     * @return
     */
    @Override
    public List<SystemMenu> findRootMenus()  throws SystemException {
        List<SystemMenu> list = sysMenuDAO.findRootMenus();
        return list;
    }

    /**
     * 根据角色ID获取菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SystemMenu> findMenusByRole(Integer roleId) throws SystemException {
        return sysMenuDAO.findMenuByRole(roleId);
    }

    /**
     * 获取父ID下面的所有子菜单
     *
     * @param preid
     * @return
     */
    @Override
    public List<SystemMenu> findAllSubMenus(Integer preid) throws SystemException {
        return sysMenuDAO.findAllSubMenus(preid);
    }

    /**
     * 获取菜单信息 包含父菜单信息
     *
     * @param id
     * @return
     */
    @Override
    public SystemMenu findMenuHasParent(Integer id) {
        SystemMenu menu = selectByPrimaryKey(id);
        if(menu != null && menu.getPreid()!=null){
            SystemMenu parent = selectByPrimaryKey(menu.getPreid());
            menu.setParent(parent);
        }
        return menu;
    }

    /**
     * 获取角色子菜单
     *
     * @param roleId
     * @param preid
     * @return
     */
    @Override
    public List<SystemMenu> findSubMenusByRole(Integer roleId, Integer preid) {
        return sysMenuDAO.findSubMenusByRole(roleId, preid);
    }

    /**
     * 删除菜单以及子菜单
     *
     * @param id
     */
    @Override
    public void deleteMenu(Integer id) {
        sysMenuDAO.deleteByPrimaryKey(id);
        SystemMenu condition = new SystemMenu();
        condition.setPreid(id);
        sysMenuDAO.delete(condition);
    }
}
