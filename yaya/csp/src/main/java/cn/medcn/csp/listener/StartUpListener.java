package cn.medcn.csp.listener;

import cn.medcn.common.utils.LogUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;

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

        LogUtils.info(log, "init context successed ! ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
    }
}
