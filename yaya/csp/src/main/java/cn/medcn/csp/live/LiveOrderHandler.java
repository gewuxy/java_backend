package cn.medcn.csp.live;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.service.LiveService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.medcn.csp.CspConstants.COURSE_ID_KEY;
import static cn.medcn.csp.CspConstants.LIVE_TYPE_KEY;
import static cn.medcn.meet.dto.LiveOrderDTO.LIVE_TYPE_PPT;
import static cn.medcn.meet.dto.LiveOrderDTO.LIVE_TYPE_VIDEO;

/**
 * Created by lixuan on 2017/9/27.
 */
@Service
public class LiveOrderHandler extends TextWebSocketHandler {

    public static Map<String, Map<String, WebSocketSession>> sessionMap = new ConcurrentHashMap<>();

    private static final Log log = LogFactory.getLog(LiveOrderHandler.class);


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
        LogUtils.debug(log, "accept order = " + order.getOrder());
        order.setSid(session.getId());
        //收到消息 发送到队列
        liveService.publish(order);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LogUtils.debug(log, String.format("client connection error with sessionId (%s) !!!", session.getId()));

        String courseId = (String) session.getAttributes().get(COURSE_ID_KEY);
        if(sessionMap !=null && sessionMap.get(courseId) != null){
            sessionMap.get(courseId).remove(session.getId());
        }

        broadcast(LiveOrderDTO.buildUserJoinOrder(courseId, session.getId(), onlineAudiences(courseId)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        LogUtils.debug(log, String.format("user with sessionId (%s) exit !!!", session.getId()));

        String courseId = (String) session.getAttributes().get(COURSE_ID_KEY);
        if(sessionMap !=null && sessionMap.get(courseId) != null){
            sessionMap.get(courseId).remove(session.getId());
        }

        broadcast(LiveOrderDTO.buildUserJoinOrder(courseId, session.getId(), onlineAudiences(courseId)));
    }


    protected void register(WebSocketSession session) {


        String courseId = (String) session.getAttributes().get(COURSE_ID_KEY);
        String token = (String) session.getAttributes().get(Constants.TOKEN);
        String liveType = (String) session.getAttributes().get(LIVE_TYPE_KEY);
        if (liveType == null) {
            liveType = LIVE_TYPE_PPT;
        }

        LogUtils.debug(log, String.format("user login in meeting with courseId = %s and token = %s", courseId, token));

        Map<String, WebSocketSession> sessionList = sessionMap.get(courseId);
        if(sessionList == null){
            sessionList = new ConcurrentHashMap<>();
            sessionList.put(session.getId(), session);
            sessionMap.put(courseId, sessionList);
        }else{
            if (token != null) {
                WebSocketSession duplicateSession = getDuplicate(courseId, token, session);
                if (duplicateSession != null) {
                    try {
                        //存在重复的token信息 向之前登录的人发送被踢指令
                        duplicateSession.sendMessage(new TextMessage(JSON.toJSONString(LiveOrderDTO.buildKickOrder(courseId, session.getId(), liveType))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            sessionMap.get(courseId).put(session.getId(), session);
        }

        broadcast(LiveOrderDTO.buildUserJoinOrder(courseId, session.getId(), onlineAudiences(courseId)));
    }

    /**
     * 发送指令到指定session
     * @param session
     * @param order
     */
    public static void sendToPoint (WebSocketSession session, LiveOrderDTO order){
        try {
            session.sendMessage(new TextMessage(JSON.toJSONString(order)));
        } catch (IOException e) {
            e.printStackTrace();
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
            Map<String, WebSocketSession> currentMap = sessionMap.get(dto.getCourseId());
            dto.setOnLines(onlineAudiences(dto.getCourseId()));
            if (dto.getOrder() == LiveOrderDTO.ORDER_KICK_REFUSE) {//当指令为拒绝被踢
                WebSocketSession currentSession = sessionMap.get(dto.getCourseId()).get(dto.getSid());
                if (currentSession != null) {
                    WebSocketSession otherSession = getDuplicate(dto.getCourseId(), (String) currentSession.getAttributes().get(Constants.TOKEN), currentSession);
                    try {
                        if (otherSession != null) {
                            otherSession.sendMessage(new TextMessage(JSON.toJSONString(LiveOrderDTO.buildKickRefuseOrder(dto.getCourseId(), otherSession.getId(), dto.getLiveType()))));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (dto.getOrder() == LiveOrderDTO.ORDER_KICK_ACCEPT || dto.getOrder() == LiveOrderDTO.ORDER_KICK){
                WebSocketSession currentSession = sessionMap.get(dto.getCourseId()).get(dto.getSid());
                if (currentSession != null) {
                    WebSocketSession otherSession = getDuplicate(dto.getCourseId(), (String) currentSession.getAttributes().get(Constants.TOKEN), currentSession);
                    try {
                        if (otherSession != null) {
                            otherSession.sendMessage(new TextMessage(JSON.toJSONString(LiveOrderDTO.buildKickAcceptOrder(dto.getCourseId(), otherSession.getId(), dto.getLiveType()))));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
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

    /**
     * 踢掉指定token的用户
     * @param courseId
     * @param token
     */
    public static void kickUser(String courseId, String token){
        Map<String, WebSocketSession> subSessionMap = sessionMap.get(courseId);
        if (subSessionMap != null) {
            Iterator<String> iterator = subSessionMap.keySet().iterator();
            while (iterator.hasNext()) {
                String wssKey = iterator.next();
                WebSocketSession wss = subSessionMap.get(wssKey);
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
    }


    protected static WebSocketSession getDuplicate(String courseId, String token, WebSocketSession currentSession){
        if (courseId == null) {
            return null;
        }
        Map<String, WebSocketSession> sMap = sessionMap.get(courseId);
        WebSocketSession duplicateSession = null;

        String liveType = (String) currentSession.getAttributes().get(LIVE_TYPE_KEY);
        if (sMap != null) {
            for (String key : sMap.keySet()) {
                if (token.equals(sMap.get(key).getAttributes().get(Constants.TOKEN))
                        && !sMap.get(key).getId().equals(currentSession.getId())) {
                    if (liveType == null) {
                        liveType = LIVE_TYPE_PPT;
                    }

                    if (liveType.equals(sMap.get(key).getAttributes().get(LIVE_TYPE_KEY))) {
                        duplicateSession = sMap.get(key);
                        break;
                    }

                }
            }
        }
        return duplicateSession;
    }


    public static boolean hasDuplicate(String courseId, String token){
        if (courseId == null) {
            return false;
        }
        Map<String, WebSocketSession> sMap = sessionMap.get(courseId);
        boolean hasDuplicate = false;
        if (sMap != null) {
            for (String key : sMap.keySet()) {
                if (token.equals(sMap.get(key).getAttributes().get(Constants.TOKEN))
                        && LIVE_TYPE_PPT.equals(sMap.get(key).getAttributes().get(LIVE_TYPE_KEY))) {
                    hasDuplicate = true;
                    break;
                }
            }
        }
        return hasDuplicate;
    }

    public static boolean hasDuplicate(String courseId, String token, String liveType){
        if (liveType == null || LIVE_TYPE_PPT.equals(liveType)) {
            return hasDuplicate(courseId, token);
        }
        if (courseId == null) {
            return false;
        }
        Map<String, WebSocketSession> sMap = sessionMap.get(courseId);
        boolean hasDuplicate = false;
        if (sMap != null) {
            for (String key : sMap.keySet()) {
                if (token.equals(sMap.get(key).getAttributes().get(Constants.TOKEN))
                        && liveType.equals(sMap.get(key).getAttributes().get(LIVE_TYPE_KEY))) {
                    hasDuplicate = true;
                    break;
                }
            }
        }
        return hasDuplicate;
    }


    public static Map<String, WebSocketSession> findVideoLiveSessions(){
        Map<String, WebSocketSession> map = null;
        if (sessionMap != null && sessionMap.size() > 0) {
            map = new HashMap<>();

            Map<String, WebSocketSession> currentSessionMap = null;
            for (String courseId : sessionMap.keySet()) {
                currentSessionMap = sessionMap.get(courseId);
                if (currentSessionMap != null && currentSessionMap.size() > 0) {

                    for (String sid : currentSessionMap.keySet()) {
                        String liveType = (String) currentSessionMap.get(sid).getAttributes().get(LIVE_TYPE_KEY);
                        if (liveType != null && liveType.equals(LIVE_TYPE_VIDEO)) {
                            map.put(courseId, currentSessionMap.get(sid));
                            break;
                        }
                    }

                }
            }
        }

        return map;
    }

    /**
     * 返回消耗流量的用户
     * @param courseId
     * @return
     */
    public static int onlineFluxUsers(String courseId){
        Map<String, WebSocketSession> sMap = sessionMap.get(courseId);
        if (sMap == null || sMap.size() == 0) {
            return 0;
        } else {
            int counter = 0;
            for (String sKey : sMap.keySet()) {
                WebSocketSession session = sMap.get(sKey);
                if (session.getAttributes().get(Constants.TOKEN) == null) {
                    counter ++;
                }
            }

            return counter;
        }
    }

    /**
     * 获取观看人数
     * @param courseId
     * @return
     */
    public static int onlineAudiences(String courseId){
        int onLines = 0;

        Map<String, WebSocketSession> sMap = sessionMap.get(courseId);
        if (sMap != null && sMap.size() > 0) {
            for (WebSocketSession session : sMap.values()) {
                if (CheckUtils.isEmpty((String)session.getAttributes().get(Constants.TOKEN))){
                    onLines ++ ;
                }
            }
        }

        return onLines;
    }

    /**
     * 发送消息给主持人
     * @param courseId
     * @param order
     */
    public static void sendToSpeaker (String courseId, LiveOrderDTO order){
        Map<String , WebSocketSession> map = sessionMap.get(courseId);
        if (map != null){
            for (WebSocketSession session : map.values()) {
                if (session.getAttributes().get(Constants.TOKEN) != null) {
                    sendToPoint(session, order);
                }
            }
        }
    }

}
