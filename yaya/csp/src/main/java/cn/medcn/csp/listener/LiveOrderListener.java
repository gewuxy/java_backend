package cn.medcn.csp.listener;

import cn.medcn.csp.live.LiveOrderHandler;
import cn.medcn.meet.dto.LiveOrderDTO;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by lixuan on 2017/9/27.
 */
public class LiveOrderListener implements MessageListener {

    protected RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] contentBytes = message.getBody();
        LiveOrderDTO dto = (LiveOrderDTO) redisTemplate.getValueSerializer().deserialize(contentBytes);

        LiveOrderHandler.broadcast(dto);
    }
}
