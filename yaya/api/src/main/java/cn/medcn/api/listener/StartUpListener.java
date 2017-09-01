package cn.medcn.api.listener;

import cn.medcn.api.jobs.AutoMeetNotifyJob;
import cn.medcn.api.jobs.LogHandlerJob;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.meet.jobs.MeetMessageJob;
import cn.medcn.meet.service.MeetMessageService;
import cn.medcn.meet.service.MeetNotifyService;
import cn.medcn.user.service.LogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/4/25.
 */
public class StartUpListener extends ContextLoaderListener {

    private static Log log = LogFactory.getLog(StartUpListener.class);

    public StartUpListener(){}

    public StartUpListener(WebApplicationContext context){
        super(context);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);
        //启动会议留言持久化线程
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        MeetMessageService meetMessageService = ctx.getBean(MeetMessageService.class);
        Runnable meetMessageJob = new MeetMessageJob(meetMessageService);
        Thread meetMessageThread= new Thread(meetMessageJob);
        meetMessageThread.start();
        LogUtils.info(log,"初始化会议留言持久化线程成功!");
        //启动考试答案保存线程
//        ExamService examService = ctx.getBean(ExamService.class);
//        Runnable examHistoryJob = new ExamHistorySubmitJob(examService);
//        Thread examHistoryThread = new Thread(examHistoryJob);
//        examHistoryThread.start();
//        LogUtils.info(log,"初始化考试答案保存线程成功.");

        LogService logService = ctx.getBean(LogService.class);
        Runnable logHandlerJob = new LogHandlerJob(logService);
        Thread logHandlerThread = new Thread(logHandlerJob);
        logHandlerThread.start();
        LogUtils.info(log, "初始化日志持久化线程成功.");


        MeetNotifyService meetNotifyService = ctx.getBean(MeetNotifyService.class);
        Runnable meetNotifyJob = new AutoMeetNotifyJob(meetNotifyService);
        ScheduledExecutorService notifyService = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        notifyService.scheduleAtFixedRate(meetNotifyJob, 20, 60, TimeUnit.SECONDS);

        LogUtils.info(log, "init context successed ! ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
    }
}
