package cn.medcn.sys.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.sys.dao.SystemRoleDAO;
import cn.medcn.sys.dao.SystemUserDAO;
import cn.medcn.sys.model.SystemRole;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SystemUserService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    /**
     * 获取系统用户列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<SystemUser> findUserByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<SystemUser> page = MyPage.page2Mypage((Page) systemUserDAO.findUserByPage(pageable.getParams()));
        return page;
    }
}
