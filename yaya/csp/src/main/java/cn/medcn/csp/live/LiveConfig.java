package cn.medcn.csp.live;

import cn.medcn.csp.interceptor.LiveHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * Created by lixuan on 2017/9/27.
 */
@Configuration
@EnableWebSocket
public class LiveConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Autowired
    protected LiveHandshakeInterceptor liveHandshakeInterceptor;

    @Autowired
    protected LiveOrderHandler liveOrderHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(liveOrderHandler,"/live/order").
                addInterceptors(liveHandshakeInterceptor).
                setAllowedOrigins("*");
    }
}
