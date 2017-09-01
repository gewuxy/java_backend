package cn.medcn.api.websocket;

import cn.medcn.api.interceptor.HandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * Created by lixuan on 2017/4/28.
 */
@Configuration
@EnableWebSocket
public class WebsocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {


    @Resource
    private HandshakeInterceptor handshakeInterceptor;

    @Resource
    private MeetMessageHandler meetMessageHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(meetMessageHandler,"/api/im").
                addInterceptors(handshakeInterceptor).
                setAllowedOrigins("*");
    }
}
