package cn.medcn.meet.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.meet.dao.JpushMessageDAO;
import cn.medcn.meet.dao.JpushMessageHistoryDAO;
import cn.medcn.meet.dao.MeetDAO;
import cn.medcn.meet.dao.MeetPropertyDAO;
import cn.medcn.meet.dto.JpushMessageDTO;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.JpushMessageService;
import cn.medcn.user.dao.AppUserDAO;
import cn.medcn.user.dto.AppUserSimpleDTO;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.model.WXUserInfo;
import cn.medcn.weixin.service.WXMessageService;
import cn.medcn.weixin.service.WXUserInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.pingplusplus.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/7.
 */
@Service
public class JpushMessageServiceImpl extends BaseServiceImpl<JpushMessage> implements JpushMessageService {

    @Autowired
    private JpushMessageDAO jpushMessageDAO;

    @Autowired
    private JpushMessageHistoryDAO jpushMessageHistoryDAO;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private JPushService jPushService;


    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    protected MeetDAO meetDAO;

    @Autowired
    protected MeetPropertyDAO meetPropertyDAO;

    @Autowired
    protected WXUserInfoService wxUserInfoService;

    @Autowired
    protected WXMessageService wxMessageService;

    @Override
    public Mapper<JpushMessage> getBaseMapper() {
        return jpushMessageDAO;
    }


    @Override
    public MyPage<JpushMessageDTO> findJpushMessage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<JpushMessageDTO> page = MyPage.page2Mypage((Page) jpushMessageDAO.findMessage(pageable.getParams()));
        return page;
    }

    /**
     * 保存消息查看记录
     *
     * @param history
     */
    @Override
    public void insertHistory(JpushMessageHistory history) {
        jpushMessageHistoryDAO.insert(history);
    }

    /**
     * 放到队列中
     *
     * @param message
     */
    @Override
    public void appendToQueue(JpushMessage message) {
        redisCacheUtils.pushToQueue(JPUSH_MESSAGE_QUEUE_KEY, message);
    }

    /**
     * 发送消息并持久化
     *
     * @param message
     */
    @Override
    public void doSend(JpushMessage message) {
        if (message.getSendType() == null) {
            message.setSendType(JpushMessage.SendType.single.ordinal());
        }
        if (message.getNotifyType() == null) {
            message.setNotifyType(MeetNotify.NotifyType.meet.ordinal());
        }
        if (message.getFlat() == null) {
            message.setFlat(JpushMessage.SendFlat.jPush.ordinal());
        }
        AppUser sender = appUserDAO.selectByPrimaryKey(message.getSender());
        if (sender != null) {
            message.setSenderName(sender.getNickname());
        }
        try {
            if (message.getSendType().intValue() == JpushMessage.SendType.single.ordinal()) {
                sendSingle(message, sender);
            } else {
                sendGroup(message, sender);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    protected void sendToJpush(JpushMessage message, AppUser sender, Integer userId) {
        message.setSenderName(sender.getNickname());
        if (!CheckUtils.isEmpty(message.getMeetId())) {
            Meet meet = meetDAO.selectByPrimaryKey(message.getMeetId());
            if (meet != null) {
                message.setMeetName(meet.getMeetName());
            }
        }
        Map<String, String> extrasMap = JpushMessage.generateExtras(message);
        int result = jPushService.sendToAlias(jPushService.generateAlias(userId), message.getTitle(), message.getTitle(), message.getContent(), extrasMap);
        message.setState(result);
    }

    protected void sendToWeChat(JpushMessage message, Integer userId) {
        message.setState(JpushMessage.SendState.fail.ordinal());
        if (!CheckUtils.isEmpty(message.getMeetId())) {
            Meet meet = meetDAO.selectByPrimaryKey(message.getMeetId());
            MeetProperty meetProperty = meetPropertyDAO.findProperty(message.getMeetId());
            if (meet != null && meetProperty != null) {
                WXUserInfo wxUserInfo = wxUserInfoService.findWxUserInfoByAppUserId(userId);
                if (wxUserInfo != null && wxUserInfo.getSubscribe() != null && wxUserInfo.getSubscribe() == true) {
                    WXMessageService.TEMPLATE_MESSAGE template_message = WXMessageService.TEMPLATE_MESSAGE.meet;
                    if (message.getNotifyType().intValue() == MeetNotify.NotifyType.exam.ordinal()) {
                        template_message = WXMessageService.TEMPLATE_MESSAGE.exam;
                    } else if (message.getNotifyType().intValue() == MeetNotify.NotifyType.survey.ordinal()) {
                        template_message = WXMessageService.TEMPLATE_MESSAGE.survey;
                    }
                    wxMessageService.sendByMeetId(template_message,
                            wxUserInfo.getOpenid(),
                            meet.getId(),
                            message.getContent(),
                            meet.getMeetName(),
                            meet.getOrganizer(),
                            wxMessageService.formatMessageDate(meetProperty.getStartTime()));
                    message.setState(JpushMessage.SendState.success.ordinal());
                }
            }
        }
    }

    protected void sendSingle(JpushMessage message, AppUser sender) {
        AppUser accepter = appUserDAO.selectByPrimaryKey(message.getReceiver());
        if (message.getFlat() == JpushMessage.SendFlat.jPush.ordinal()) {
            sendToJpush(message, sender, message.getReceiver());
        } else if (message.getFlat() == JpushMessage.SendFlat.weChat.ordinal()) {
            sendToWeChat(message, accepter.getId());
        } else if (message.getFlat() == JpushMessage.SendFlat.all.ordinal()) {
            sendToJpush(message, sender, message.getReceiver());
            sendToWeChat(message, accepter.getId());
        }
        message.setSendTime(new Date());
        insert(message);
    }

    protected void sendGroup(JpushMessage message, AppUser sender) {
        List<AppUserSimpleDTO> list = appUserService.findAllAttation(message.getSender(), message.getGroupId());
        for (AppUserSimpleDTO dto : list) {
            message.setReceiver(dto.getId());
            sendSingle(message, sender);
        }
    }

    protected void sendGroups(JpushMessage message, AppUser sender){
        List<AppUserSimpleDTO> list = appUserService.findAllAttention(message.getSender(), message.getChoseGroupIds());
        for (AppUserSimpleDTO dto : list) {
            message.setReceiver(dto.getId());
            sendSingle(message, sender);
        }
    }

    @Override
    public JpushMessage brpopFromQueue() {
        JpushMessage message = (JpushMessage) redisCacheUtils.bRPopFromQueue(JPUSH_MESSAGE_QUEUE_KEY);
        return message;
    }

    /**
     * 查询消息发送历史
     *
     * @param pageable
     * @param sender
     * @return
     */
    @Override
    public MyPage<JpushMessageDTO> findHistories(Pageable pageable, Integer sender) {
        pageable.getParams().put("userId", sender);
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage page = MyPage.page2Mypage((Page) jpushMessageDAO.findHistories(pageable.getParams()));
        return page;
    }
}
