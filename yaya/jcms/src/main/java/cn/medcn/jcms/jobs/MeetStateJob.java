package cn.medcn.jcms.jobs;

import cn.medcn.meet.service.MeetService;

/**
 * Created by lixuan on 2017/6/12.
 */
public class MeetStateJob implements Runnable {

    private MeetService meetService;

    public MeetStateJob(MeetService meetService){
        this.meetService = meetService;
    }

    @Override
    public void run() {
        meetService.doModifyState();
    }
}
