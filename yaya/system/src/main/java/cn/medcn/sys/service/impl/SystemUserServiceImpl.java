package cn.medcn.sys.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.sys.dao.SystemRoleDAO;
import cn.medcn.sys.dao.SystemUserDAO;
import cn.medcn.sys.model.SystemRole;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SystemUserService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/5/2.
 */
@Service
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUser> implements SystemUserService {

    @Autowired
    private SystemUserDAO systemUserDAO;

    @Autowired
    private SystemRoleDAO systemRoleDAO;

    @Override
    public Mapper<SystemUser> getBaseMapper() {
        return systemUserDAO;
    }


    /**
     * 获取用户信息 包含角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public SystemUser findUserHasRole(Integer userId) {
        SystemUser user = systemUserDAO.selectByPrimaryKey(userId);
        if(!user.getIsAdmin()&&user.getRoleId()!=null){
            SystemRole role = systemRoleDAO.selectByPrimaryKey(user.getRoleId());
            user.setRole(role);
        }
        return user;
    }
}
