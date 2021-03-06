package cn.medcn.sys.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.model.SystemNotify;

import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
public interface SysNotifyService extends BaseService<SystemNotify>{

    /**
     * 平台通知
     * @param pageable
     * @return
     */
    MyPage<SystemNotify> findNotifyList(Pageable pageable);

    /**
     * 未读消息的数量
     * @param userId
     * @return
     */
    int findUnreadMsgCount(String userId);

    /**
     *消息列表
     * @param pageable
     * @return
     */
    MyPage<SystemNotify> findMessageListByPage(Pageable pageable);

    /**
     * 个人信息推送
     * @param userId
     * @param title
     * @param content
     */
    void addNotify(String userId,String title,String content,String sender);
}
