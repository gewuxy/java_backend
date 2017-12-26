package cn.medcn.csp.tasks;

import cn.medcn.meet.service.LiveService;

/**
 * Created by lixuan on 2017/12/26.
 */
public class LiveStateChangeTask implements Runnable {

    private LiveService liveService;

    public LiveStateChangeTask (LiveService liveService){
        this.liveService = liveService;
    }

    @Override
    public void run() {
        liveService.doModifyLiveState();
    }
}
