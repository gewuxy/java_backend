package cn.medcn.csp.tasks;

import cn.medcn.common.utils.LogUtils;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.live.LiveOrderHandler;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.service.UserFluxService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 流量监控
 * Created by lixuan on 2017/11/9.
 */
public class FlowMonitorTask implements Runnable {

    private static final Log log = LogFactory.getLog(FlowMonitorTask.class);

    private UserFluxService userFluxService;

    public FlowMonitorTask(UserFluxService userFluxService){
        this.userFluxService = userFluxService;
    }

    @Override
    public void run() {
        Map<String, WebSocketSession> sessionMap = LiveOrderHandler.findVideoLiveSessions();

        if (sessionMap != null) {
            for (String courseId : sessionMap.keySet()) {

                UserFlux flux = userFluxService.findByCourseId(courseId);
                if (flux == null || flux.getFlux() <=0) {//流量耗尽或者未购买流量

                    LogUtils.debug(log, "直播会议["+courseId+"] 流量耗尽...");

                    LiveOrderHandler.sendToPoint(sessionMap.get(courseId), LiveOrderDTO.buildFluxExhaustedOrder(courseId));

                } else if (flux.getFlux() < CspConstants.FLUX_WARN_THRESHOLD) {//流量不足预警阈值

                    LogUtils.debug(log, "直播会议["+courseId+"] 流量不足...");

                    LiveOrderHandler.sendToPoint(sessionMap.get(courseId), LiveOrderDTO.buildFluxNotEnoughOrder(courseId));

                }

                int onlineUsers = LiveOrderHandler.onlineFluxUsers(courseId);

                if (onlineUsers > 0) {
                    float usedFlux =  onlineUsers * CspConstants.FLUX_USAGE_PER_SECOND * CspConstants.FLUX_MONITOR_SPACE;
                    userFluxService.doDeduct(courseId, (int)usedFlux + 1);
                }
            }
        }
    }
}
