package cn.medcn.csp.live;

import cn.medcn.common.Constants;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.service.LiveService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.medcn.csp.CspConstants.COURSE_ID_KEY;

/**
 * Created by lixuan on 2017/9/27.
 */
@Service
public class LiveOrderHandler extends TextWebSocketHandler {

    public static Map<String, Map<String, WebSocketSession>> sessionMap = new ConcurrentHashMap<>();

    @Value("${app.file.base}")
    private String appFileBase;

    @Autowired
    protected LiveService liveService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        register(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LiveOrderDTO order = JSON.parseObject(message.getPayload(), LiveOrderDTO.class);

        //收到消息 发送到队列
        liveService.publish(order);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String courseId = (String) session.getAttributes().get(COURSE_ID_KEY);
        if(sessionMap !=null && sessionMap.get(courseId) != null){
            sessionMap.get(courseId).remove(session.getId());
        }
    }


    protected void register(WebSocketSession session) {
        String courseId = (String) session.getAttributes().get(COURSE_ID_KEY);
        String token = (String) session.getAttributes().get(Constants.TOKEN);

        Map<String, WebSocketSession> sessionList = sessionMap.get(courseId);
        if(sessionList == null){
            sessionList = new ConcurrentHashMap<>();
            sessionList.put(session.getId(), session);
            sessionMap.put(courseId, sessionList);
        }else{
            if (token != null) {
                Iterator<String> iterator = sessionList.keySet().iterator();
                while (iterator.hasNext()) {
                    String wssKey = iterator.next();
                    WebSocketSession wss = sessionList.get(wssKey);
                    if (wss != null && wss.getAttributes().get(Constants.TOKEN) != null ) {
                        if (token.equals(wss.getAttributes().get(Constants.TOKEN))) {
                            try {
                                wss.close();
                                iterator.remove();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
            sessionMap.get(courseId).put(session.getId(), session);
        }
    }

    /**
     * 广播到客户端
     *
     * @param dto
     * @throws IOException
     */
    public static void broadcast(LiveOrderDTO dto) {
        if (!StringUtils.isEmpty(dto.getCourseId())) {
            try {
                for (WebSocketSession session : sessionMap.get(dto.getCourseId()).values()) {
                    session.sendMessage(new TextMessage(JSON.toJSONString(dto)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
