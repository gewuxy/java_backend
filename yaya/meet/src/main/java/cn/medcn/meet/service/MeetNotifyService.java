package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.model.MeetNotify;

import java.util.List;

/**
 * Created by lixuan on 2017/8/9.
 */
public interface MeetNotifyService extends BaseService<MeetNotify> {

    /**
     * 查找用户会议提醒
     * @param userId
     * @param meetId
     * @return
     */
    MeetNotify findMeetNotify(Integer userId, String meetId);

    /**
     * 添加会议提醒
     * @param userId
     * @param meetId
     */
    void addMeetNotify(Integer userId, String meetId);

    /**
     * 添加或者取消会议提醒
     * @param userId
     * @param meetId
     * @param stored
     */
    void addMeetNotify(Integer userId, String meetId, boolean stored);

    /**
     * 取消会议提醒
     * @param userId
     * @param meetId
     */
    void doCancelNotify(Integer userId, String meetId);

    /**
     * 查询出所有需要提醒的通知
     * @return
     */
    List<MeetNotify> findLegalNotifies();

    /**
     * 推送会议提醒
     * @param notify
     */
    void doSendNotify(MeetNotify notify);

    /**
     * 推送所有会议提醒
     */
    void doSendNotify();
}
