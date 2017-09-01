package cn.medcn.api.jobs;

import cn.medcn.user.model.AppLog;
import cn.medcn.user.service.LogService;

/**
 * Created by lixuan on 2017/5/2.
 */
public class LogHandlerJob implements Runnable {

    private LogService logService;

    public LogHandlerJob(LogService logService){
        this.logService = logService;
    }

    @Override
    public void run() {
        while (true){
            AppLog log = logService.bpopFromQueue();
            if(log != null){
                logService.insert(log);
            }
        }
    }
}
