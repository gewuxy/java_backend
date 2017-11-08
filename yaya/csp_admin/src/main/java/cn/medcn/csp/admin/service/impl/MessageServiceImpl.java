package cn.medcn.csp.admin.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.csp.admin.dao.MessageDao;
import cn.medcn.csp.admin.model.CspAdminMessage;
import cn.medcn.csp.admin.model.CspSysLog;
import cn.medcn.csp.admin.service.MessageService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<CspAdminMessage> implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public MyPage<CspAdminMessage> findMessageListByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<CspAdminMessage> page = MyPage.page2Mypage((Page) messageDao.findMessageListByPage(pageable.getParams()));
        return page;
    }

    @Override
    public Mapper<CspAdminMessage> getBaseMapper() {
        return messageDao;
    }
}
