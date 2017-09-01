package cn.medcn.sys.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.sys.dao.SystemRoleDAO;
import cn.medcn.sys.dao.SystemRoleMenuDAO;
import cn.medcn.sys.model.SystemRole;
import cn.medcn.sys.model.SystemRoleMenu;
import cn.medcn.sys.service.SystemRoleService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/5/2.
 */
@Service
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRole> implements SystemRoleService {

    @Autowired
    private SystemRoleDAO systemRoleDAO;

    @Autowired
    private SystemRoleMenuDAO systemRoleMenuDAO;

    @Override
    public Mapper<SystemRole> getBaseMapper() {
        return systemRoleDAO;
    }

    /**
     * 分配角色权限
     *
     * @param roleId
     * @param menuIds
     */
    @Override
    public void insertRoleMenu(Integer roleId, Integer[] menuIds) {
        //删除原来的所有设置
        SystemRoleMenu condition = new SystemRoleMenu();
        condition.setRoleId(roleId);
        systemRoleMenuDAO.delete(condition);
        for(Integer menuId:menuIds){
            SystemRoleMenu roleMenu = new SystemRoleMenu(null, roleId, menuId);
            systemRoleMenuDAO.insert(roleMenu);
        }
    }
}
