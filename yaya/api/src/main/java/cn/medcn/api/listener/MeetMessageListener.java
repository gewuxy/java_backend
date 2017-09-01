package cn.medcn.api.listener;

import cn.medcn.api.websocket.MeetMessageHandler;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.meet.dto.MeetMessageDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

/**
 * Created by lixuan on 2017/4/28.
 */
public class MeetMessageListener implements MessageListener {

    private static final Log log = LogFactory.getLog(MeetMessageListener.class);

    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body_bytes = message.getBody();
        MeetMessageDTO meetMessage = (MeetMessageDTO) redisTemplate.getValueSerializer().deserialize(body_bytes);
        LogUtils.info(log, "接收到消息来自["+meetMessage.getSender()+"]的消息 ："+meetMessage.getMessage());
        try {
            MeetMessageHandler.broadcast(meetMessage);
        } catch (IOException e) {
            LogUtils.error(log, "发送会议消息失败");
            e.printStackTrace();
        }
    }
}
