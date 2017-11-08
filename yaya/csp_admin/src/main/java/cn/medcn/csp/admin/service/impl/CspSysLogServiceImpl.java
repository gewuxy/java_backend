package cn.medcn.csp.admin.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.csp.admin.service.CspSysLogService;
import cn.medcn.csp.admin.dao.CspSysLogDAO;
import cn.medcn.csp.admin.model.CspSysLog;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * by create HuangHuibin 2017/11/7
 */
@Service
public class CspSysLogServiceImpl extends BaseServiceImpl<CspSysLog> implements CspSysLogService{

    @Autowired
    private CspSysLogDAO cspSysLogDAO;

    @Override
    public Mapper<CspSysLog> getBaseMapper() {
        return cspSysLogDAO;
    }

    @Override
    public MyPage<CspSysLog> findCspSysLog(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<CspSysLog> page = MyPage.page2Mypage((Page) cspSysLogDAO.findCspSysLog(pageable.getParams()));
        return page;
    }
}
