package cn.medcn.api.websocket;

import cn.medcn.api.dto.Principal;
import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.meet.dto.MeetMessageDTO;
import cn.medcn.meet.service.MeetMessageService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixuan on 2017/4/28.
 */
@Service
public class MeetMessageHandler extends TextWebSocketHandler {

    private static final Log log = LogFactory.getLog(MeetMessageHandler.class);

    public static Map<String, Map<String, WebSocketSession>> sessionMap;

    @Resource
    private MeetMessageService meetMessageService;

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Value("${app.file.base}")
    private String appFileBase;

    static {
        sessionMap = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 与客户端完成连接后调用
        LogUtils.info(log, "新的客户端连接成功...");
        registSession(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //收到消息之后将消息发送到队列中
        MeetMessageDTO dto = JSON.parseObject(message.getPayload(), MeetMessageDTO.class);
        String token = (String) session.getAttributes().get(Constants.TOKEN);
        Principal principal = (Principal) redisCacheUtils.getCacheObject(Constants.TOKEN+"_"+token);
        if(!StringUtils.isEmpty(principal.getHeadimg())){
            dto.setHeadimg(appFileBase+principal.getHeadimg());
        }else{
            dto.setHeadimg("");
        }
        dto.setSenderId(principal.getId());
        dto.setSendTime(new Date());
        dto.setMsgType(0);
        dto.setSender(principal.getNickname());
        dto.setMeetId((String) session.getAttributes().get(Constants.MEET_ID_KEY));
        meetMessageService.publish(dto);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LogUtils.info(log, "消息传输出错时调用");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 一个客户端连接断开时关闭
        LogUtils.info(log, "一个客户端断开连接...");
        LogUtils.info(log, "status code : "+status.getCode()+" reason : "+status.getReason());
        String token = (String) session.getAttributes().get(Constants.TOKEN);
        String meetId = (String) session.getAttributes().get(Constants.MEET_ID_KEY);
        if(sessionMap !=null && sessionMap.get(meetId) != null){
            sessionMap.get(meetId).remove(token);
        }
    }


    protected void registSession(WebSocketSession session){
        String meetId = (String) session.getAttributes().get(Constants.MEET_ID_KEY);
        String token = (String) session.getAttributes().get(Constants.TOKEN);
        if(!StringUtils.isEmpty(meetId)){
            Map<String,WebSocketSession> sessionList = sessionMap.get(meetId);
            if(sessionList == null){
                sessionList = new ConcurrentHashMap<>();
                sessionList.put(token, session);
                sessionMap.put(meetId, sessionList);
            }else{
                sessionMap.get(meetId).put(token, session);
            }
        }
    }


    public static void broadcast(MeetMessageDTO message) throws IOException {
        if(!StringUtils.isEmpty(message.getMeetId())){
            for(WebSocketSession session:sessionMap.get(message.getMeetId()).values()){
                session.sendMessage(new TextMessage(JSON.toJSONString(message)));
            }
        }
    }
}
