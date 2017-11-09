package cn.medcn.csp.admin.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.csp.admin.dao.CspUserInfoDAO;
import cn.medcn.csp.admin.model.CspUserInfo;
import cn.medcn.csp.admin.service.CspUserService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * by create HuangHuibin 2017/11/8
 */
@Service
public class CspUserServiceImpl extends BaseServiceImpl<CspUserInfo> implements CspUserService {

    @Autowired
    private CspUserInfoDAO cspUserInfoDAO;

    @Override
    public Mapper<CspUserInfo> getBaseMapper() {
        return cspUserInfoDAO;
    }

    @Override
    public MyPage<CspUserInfo> findCspUserList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<CspUserInfo> page = MyPage.page2Mypage((Page) cspUserInfoDAO.findCspUserList(pageable.getParams()));
        return page;
    }
}
