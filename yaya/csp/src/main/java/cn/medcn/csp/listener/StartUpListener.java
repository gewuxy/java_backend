package cn.medcn.csp.listener;

import cn.medcn.common.utils.LogUtils;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.tasks.FlowMonitorTask;
import cn.medcn.csp.tasks.LiveStateChangeTask;
import cn.medcn.csp.tasks.UserPackageTask;
import cn.medcn.csp.tasks.UserRegionUpdateTask;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.LiveService;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.UserFluxService;
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

        UserFluxService userFluxService = ctx.getBean(UserFluxService.class);
        Runnable fluxMonitorTask = new FlowMonitorTask(userFluxService);
        ScheduledExecutorService notifyService = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        notifyService.scheduleWithFixedDelay(fluxMonitorTask, 5, CspConstants.FLUX_MONITOR_SPACE, TimeUnit.SECONDS);
        LogUtils.info(log, "init context successed ! ");

        LiveService liveService = ctx.getBean(LiveService.class);
        //更改直播会议状态线程
        Runnable liveStateChangeTask = new LiveStateChangeTask(liveService);
        //每十分钟执行一次直播状态监测
        notifyService.scheduleWithFixedDelay(liveStateChangeTask, 20, 300, TimeUnit.SECONDS);

        // 启动更新CSP用户套餐版本定时任务
        CspUserPackageService userPackageService = ctx.getBean(CspUserPackageService.class);
        AudioService audioService = ctx.getBean(AudioService.class);
        // 用户套餐变更线程
        Runnable userPackageTask = new UserPackageTask(userPackageService, audioService);
        // 调度器
        notifyService.scheduleWithFixedDelay(userPackageTask, 5, 3600, TimeUnit.SECONDS);

        CspUserService cspUserService = ctx.getBean(CspUserService.class);

        //启动用户地理位置修改线程
        Runnable regionTask = new UserRegionUpdateTask(cspUserService);
        Thread regionThread = new Thread(regionTask);
        regionThread.start();

        LogUtils.info(log, "init context successed ! --- modifyUserPackageTask --- ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
    }
}
