package cn.medcn.csp.tasks;

import cn.medcn.csp.live.LiveOrderHandler;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.service.UserFluxService;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 流量监控
 * Created by lixuan on 2017/11/9.
 */
public class FlowMonitorTask implements Runnable {

    private UserFluxService userFluxService;

    public FlowMonitorTask(UserFluxService userFluxService){
        this.userFluxService = userFluxService;
    }

    @Override
    public void run() {
        Map<String, WebSocketSession> sessionMap = LiveOrderHandler.findVideoLiveSessions();

        if (sessionMap != null) {
            for (String courseId : sessionMap.keySet()) {
                UserFlux flux = userFluxService.findByCourseId(Integer.valueOf(courseId));

            }
        }
    }
}
