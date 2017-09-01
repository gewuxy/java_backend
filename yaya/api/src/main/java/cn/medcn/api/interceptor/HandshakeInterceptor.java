package cn.medcn.api.interceptor;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.LogUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/10.
 */
@Service
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Log log = LogFactory.getLog(HandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> params) throws Exception {
        LogUtils.info(log, "Before hand shake...");
        ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
        String meetid = serverRequest.getServletRequest().getParameter(Constants.MEET_ID_KEY);
        if(StringUtils.isBlank(meetid)){
            LogUtils.info(log, "会议ID没有传递, 握手失败...");
        }
        String token = serverRequest.getServletRequest().getParameter(Constants.TOKEN);
        params.put(Constants.MEET_ID_KEY, meetid);
        params.put(Constants.TOKEN, token);
        return super.beforeHandshake(request, response, webSocketHandler, params);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
        LogUtils.info(log, "After hand shake...");
    }

}
