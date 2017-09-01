package cn.medcn.api.jobs;

import cn.medcn.meet.service.MeetNotifyService;

/**
 * Created by lixuan on 2017/8/9.
 */
public class AutoMeetNotifyJob implements Runnable {

    protected MeetNotifyService meetNotifyService;

    public AutoMeetNotifyJob(MeetNotifyService meetNotifyService){
        this.meetNotifyService = meetNotifyService;
    }

    @Override
    public void run() {
        meetNotifyService.doSendNotify();
    }
}
