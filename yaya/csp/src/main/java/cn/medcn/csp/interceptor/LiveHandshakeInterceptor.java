package cn.medcn.csp.interceptor;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.csp.CspConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

import static cn.medcn.csp.CspConstants.COURSE_ID_KEY;
import static cn.medcn.csp.CspConstants.LIVE_TYPE_KEY;

/**
 * Created by lixuan on 2017/9/27.
 */
@Service
public class LiveHandshakeInterceptor extends HttpSessionHandshakeInterceptor {



    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> params) throws Exception {
        ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
        String courseId = serverRequest.getServletRequest().getParameter(COURSE_ID_KEY);

        String liveType = serverRequest.getServletRequest().getParameter(LIVE_TYPE_KEY);

        if(StringUtils.isBlank(courseId)){
            throw new RuntimeException("parameter courseId required");
        }

        String token = serverRequest.getServletRequest().getParameter(Constants.TOKEN);
        if (!CheckUtils.isEmpty(token)) {
            params.put(Constants.TOKEN, token);
        }
        if (CheckUtils.isNotEmpty(liveType)) {
            params.put(LIVE_TYPE_KEY, liveType);
        }
        params.put(COURSE_ID_KEY, courseId);
        return super.beforeHandshake(request, response, webSocketHandler, params);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
