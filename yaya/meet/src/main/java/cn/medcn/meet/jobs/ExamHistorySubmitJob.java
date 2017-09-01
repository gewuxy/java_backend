package cn.medcn.meet.jobs;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.meet.model.ExamHistory;
import cn.medcn.meet.service.ExamService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

/**
 * Created by lixuan on 2017/4/27.
 */
public class ExamHistorySubmitJob implements Runnable {

    private static Log log = LogFactory.getLog(ExamHistorySubmitJob.class);

    private ExamService examService;

    public ExamHistorySubmitJob(ExamService examService){
        this.examService = examService;
    }

    @Override
    public void run() {
        while(true){
            ExamHistory history = examService.popFromQueue();
            if(history != null){
                try {
                    examService.saveHistory(history);
                } catch (SystemException e) {
                    LogUtils.error(log, e.getMessage());
                }
                LogUtils.info(log, "保存考试答案成功");
            }
        }
    }
}
