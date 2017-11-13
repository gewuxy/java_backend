package cn.medcn.csp.listener;

import cn.medcn.common.utils.LogUtils;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.tasks.FlowMonitorTask;
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
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
    }
}
