package cn.medcn.csp.admin.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.csp.admin.dao.CspSysUserDAO;
import cn.medcn.csp.admin.model.CspSysUser;
import cn.medcn.csp.admin.service.CspSysUserService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * by create HuangHuibin 2017/11/3
 */
@Service
public class CspSysUserServiceImpl extends BaseServiceImpl<CspSysUser> implements CspSysUserService{

    @Autowired
    private CspSysUserDAO cspSysUserDAO;

    @Override
    public Mapper<CspSysUser> getBaseMapper() {
        return cspSysUserDAO;
    }

    @Override
    public MyPage<CspSysUser> findCspSysUser(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<CspSysUser> page = MyPage.page2Mypage((Page) cspSysUserDAO.findCspSysUser(pageable.getParams()));
        return page;
    }
}
