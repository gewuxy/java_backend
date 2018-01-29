package cn.medcn.jcms.listener;

import cn.medcn.article.service.NewsService;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.jcms.jobs.JpushMessageJob;
import cn.medcn.jcms.jobs.MeetStateJob;
import cn.medcn.meet.service.JpushMessageService;
import cn.medcn.meet.service.MeetService;
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
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        MeetService meetService = ctx.getBean(MeetService.class);
        Runnable meetMessageJob = new MeetStateJob(meetService);
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(meetMessageJob, 10, 120, TimeUnit.SECONDS);

        //启动极光推送队列任务
        JpushMessageService jpushMessageService = ctx.getBean(JpushMessageService.class);
        Runnable jpushMessageJob = new JpushMessageJob(jpushMessageService);
        Thread jpushHandlerThread = new Thread(jpushMessageJob);
        jpushHandlerThread.start();
        LogUtils.info(log, "初始化极光推送持久化线程成功.");

        // 启动更新新闻列表定时任务
       /* NewsService newsService = ctx.getBean(NewsService.class);
        newsService.runUpdateNewsTimer();*/

        LogUtils.info(log, "init context successed ! ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
    }
}
