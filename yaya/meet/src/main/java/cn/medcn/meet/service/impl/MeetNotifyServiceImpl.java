package cn.medcn.meet.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.MeetDAO;
import cn.medcn.meet.dao.MeetNotifyDAO;
import cn.medcn.meet.dao.MeetPropertyDAO;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.model.MeetNotify;
import cn.medcn.meet.model.MeetProperty;
import cn.medcn.meet.service.MeetNotifyService;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.model.WXUserInfo;
import cn.medcn.weixin.service.WXMessageService;
import cn.medcn.weixin.service.WXUserInfoService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 会议提醒服务
 * Created by lixuan on 2017/8/9.
 */
@Service
public class MeetNotifyServiceImpl extends BaseServiceImpl<MeetNotify> implements MeetNotifyService {

    @Autowired
    protected MeetNotifyDAO meetNotifyDAO;

    @Autowired
    protected MeetDAO meetDAO;

    @Autowired
    protected MeetPropertyDAO meetPropertyDAO;


    @Autowired
    protected WXMessageService wxMessageService;

    @Autowired
    protected WXUserInfoService wxUserInfoService;

    @Autowired
    protected AppUserService appUserService;

    @Autowired
    protected JPushService jPushService;

    @Value("${app.yaya.base}")
    protected String appBase;


    @Override
    public Mapper<MeetNotify> getBaseMapper() {
        return meetNotifyDAO;
    }


    /**
     * 查找用户会议提醒
     *
     * @param userId
     * @param meetId
     * @return
     */
    @Override
    public MeetNotify findMeetNotify(Integer userId, String meetId) {
        MeetNotify condition = new MeetNotify();
        condition.setTargetId(userId);
        condition.setMeetId(meetId);
        return meetNotifyDAO.selectOne(condition);
    }

    /**
     * 添加会议提醒
     *
     * @param userId
     * @param meetId
     */
    @Override
    public void addMeetNotify(Integer userId, String meetId) {
        MeetProperty meetProperty = meetPropertyDAO.findProperty(meetId);
        if (meetProperty != null){
            if (meetProperty.getStartTime() != null){
                //如果会议开始时间大于当前时间 则设置会议提醒
                long meetStartTime = meetProperty.getStartTime().getTime();
                if (meetStartTime > System.currentTimeMillis()){
                    MeetNotify meetNotify = findMeetNotify(userId, meetId);
                    boolean isAdd = false;
                    if (meetNotify == null){
                        meetNotify = new MeetNotify();
                        isAdd = true;
                    }
                    meetNotify.setMeetId(meetId);
                    meetNotify.setNotifyType(MeetNotify.NotifyType.meet.ordinal());
                    meetNotify.setState(MeetNotify.NotifyState.initial.ordinal());
                    meetNotify.setTargetId(userId);
                    meetNotify.setNotifyTime(meetStartTime - TimeUnit.MINUTES.toMillis(Constants.MEET_NOTIFY_PRE_TIME));
                    meetNotify.setContent(MeetNotify.DEFAULT_MEET_NOTIFY_CONTENT);
                    if (isAdd)
                        meetNotifyDAO.insert(meetNotify);
                    else
                        meetNotifyDAO.updateByPrimaryKey(meetNotify);
                }
            }
        }
    }

    /**
     * 添加或者取消会议提醒
     *
     * @param userId
     * @param meetId
     * @param stored true表示收藏 false表示取消
     */
    @Override
    public void addMeetNotify(Integer userId, String meetId, boolean stored) {
        if (stored){
            addMeetNotify(userId, meetId);
        } else {
            doCancelNotify(userId, meetId);
        }
    }

    /**
     * 取消会议提醒
     *
     * @param userId
     * @param meetId
     */
    @Override
    public void doCancelNotify(Integer userId, String meetId) {
        MeetNotify meetNotify = findMeetNotify(userId, meetId);
        if (meetNotify != null){
            meetNotify.setState(MeetNotify.NotifyState.cancel.ordinal());
            meetNotifyDAO.updateByPrimaryKey(meetNotify);
        }
    }

    /**
     * 查询出所有需要提醒的通知
     *
     * @return
     */
    @Override
    public List<MeetNotify> findLegalNotifies() {
        Long currentTime = System.currentTimeMillis();
        return meetNotifyDAO.findLegalNotifies(currentTime);
    }

    /**
     * 推送会议提醒
     *
     * @param notify
     */
    @Override
    public void doSendNotify(MeetNotify notify) {
        if (notify != null && !CheckUtils.isEmpty(notify.getMeetId())){

            AppUser appUser = appUserService.selectByPrimaryKey(notify.getTargetId());
            if (appUser != null){
                Meet meet = meetDAO.selectByPrimaryKey(notify.getMeetId());
                MeetProperty meetProperty = meetPropertyDAO.findProperty(notify.getMeetId());
                if (meet != null && meetProperty != null){
                    // 发送微信模板消息
                    String unionId = appUser.getUnionid();
                    if (!CheckUtils.isEmpty(unionId)){
                        WXUserInfo wxUserInfo = wxUserInfoService.findWXUserInfo(unionId);
                        if (wxUserInfo != null && wxUserInfo.getSubscribe() != null && wxUserInfo.getSubscribe()){
                            wxMessageService.send(WXMessageService.TEMPLATE_MESSAGE.meet,
                                    wxUserInfo.getOpenid(),
                                    appBase+"weixin/meet/info?meetId="+notify.getMeetId(),
                                    notify.getContent(),
                                    meet.getMeetName(),
                                    meet.getOrganizer(),
                                    wxMessageService.formatMessageDate(meetProperty.getStartTime()));
                            notify.setState(MeetNotify.NotifyState.notified.ordinal());
                            meetNotifyDAO.updateByPrimaryKey(notify);
                        }
                    }
                }
            }
        }
    }

    /**
     * 推送所有会议提醒
     */
    @Override
    public void doSendNotify() {
        List<MeetNotify> notifyList = findLegalNotifies();
        if (!CheckUtils.isEmpty(notifyList)){
            for (MeetNotify meetNotify : notifyList){
                doSendNotify(meetNotify);
            }
        }
    }
}
