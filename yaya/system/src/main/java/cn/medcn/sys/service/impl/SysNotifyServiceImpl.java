package cn.medcn.sys.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.sys.dao.SysMenuDAO;
import cn.medcn.sys.dao.SystemNotifyDAO;
import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.model.SystemNotify;
import cn.medcn.sys.service.SysMenuService;
import cn.medcn.sys.service.SysNotifyService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
@Service
public class SysNotifyServiceImpl extends BaseServiceImpl<SystemNotify> implements SysNotifyService {

    @Autowired
    private SystemNotifyDAO systemNotifyDAO;

    @Override
    public Mapper<SystemNotify> getBaseMapper() {
        return systemNotifyDAO;
    }

    /**
     * 平台通知
     * @param pageable
     * @return
     */
    @Override
    public MyPage<SystemNotify> findNotifyList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        List<SystemNotify> list = systemNotifyDAO.findNotifyList(pageable.getParams());
        return MyPage.page2Mypage((Page)list);
    }

    /**
     * 未读消息的数量
     * @param userId
     * @return
     */
    @Override
    public int findUnreadMsgCount(String userId) {
        SystemNotify notify =new SystemNotify();
        notify.setAcceptId(userId);
        notify.setIsRead(false);
        return systemNotifyDAO.selectCount(notify);
    }

    @Override
    public MyPage<SystemNotify> findMessageListByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        List<SystemNotify> notifyList= systemNotifyDAO.findMessageListByPage();
        MyPage<SystemNotify> page = new MyPage<SystemNotify>();
        page.setDataList(notifyList);
        return page;
    }

    @Override
    public void addNotify(String userId, String title, String content, String sender) {
        SystemNotify notify = new SystemNotify();
        notify.setSenderName(sender);
        notify.setContent(content);
        notify.setTitle(title);
        notify.setSendTime(new Date());
        notify.setIsRead(false);
        notify.setAcceptId(userId);
        notify.setNotifyType(Constants.NUMBER_ONE);
        notify.setId(StringUtils.nowStr());
        systemNotifyDAO.insert(notify);
    }
}
