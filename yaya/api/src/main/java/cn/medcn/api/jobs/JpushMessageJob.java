package cn.medcn.api.jobs;

import cn.medcn.meet.model.JpushMessage;
import cn.medcn.meet.service.JpushMessageService;

/**
 * Created by lixuan on 2017/6/7.
 */
public class JpushMessageJob implements Runnable {

    private JpushMessageService jpushMessageService;

    public JpushMessageJob(JpushMessageService jpushMessageService){
        this.jpushMessageService = jpushMessageService;
    }

    @Override
    public void run() {
        while (true){
            JpushMessage message = jpushMessageService.brpopFromQueue();
            if(message != null){
                jpushMessageService.doSend(message);
            }
        }
    }
}
