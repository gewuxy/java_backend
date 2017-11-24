package cn.medcn.sys.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.sys.dao.SystemLogDAO;
import cn.medcn.sys.model.SystemLog;
import cn.medcn.sys.service.SystemLogService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * by create HuangHuibin 2017/11/23
 */
@Service
public class SystemLogServiceImpl extends BaseServiceImpl<SystemLog> implements SystemLogService{

    @Autowired
    private SystemLogDAO systemLogDAO;

    @Override
    public Mapper<SystemLog> getBaseMapper() {
        return systemLogDAO;
    }

    @Override
    public MyPage<SystemLog> findLogByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<SystemLog> page = MyPage.page2Mypage((Page) systemLogDAO.findLogByPage(pageable.getParams()));
        return page;
    }
}
