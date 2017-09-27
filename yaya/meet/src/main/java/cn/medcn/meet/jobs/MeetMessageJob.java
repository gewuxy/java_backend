package cn.medcn.meet.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.medcn.common.utils.LogUtils;
import cn.medcn.meet.model.MeetMessage;
import cn.medcn.meet.service.MeetMessageService;

import java.util.Date;

/**
 * Created by lixuan on 2017/4/25.
 */
public class MeetMessageJob implements Runnable{

    private static Log log = LogFactory.getLog(MeetMessageJob.class);

    private MeetMessageService meetMessageService;

    public MeetMessageJob(MeetMessageService meetMessageService){
        this.meetMessageService = meetMessageService;
    }

    @Override
    public void run() {
        while(true){
            try {
                MeetMessage meetMessage = meetMessageService.bpopFromQueue();
                if(meetMessage != null){
                    meetMessage.setSendTime(new Date());
                    meetMessageService.insert(meetMessage);
                    LogUtils.info(log,"保存会议消息成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
