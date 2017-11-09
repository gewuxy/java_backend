package cn.medcn.csp.listener;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.csp.live.LiveOrderHandler;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/9/27.
 */
public class LiveOrderListener implements MessageListener {

    protected RedisTemplate redisTemplate;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] contentBytes = message.getBody();
        LiveOrderDTO dto = (LiveOrderDTO) redisTemplate.getValueSerializer().deserialize(contentBytes);
        if (dto.getOrder() == LiveOrderDTO.ORDER_SYNC) {
            redisCacheUtils.setCacheObject(LiveService.SYNC_CACHE_PREFIX + dto.getCourseId(), dto, (int)TimeUnit.DAYS.toSeconds(3));
        }
        LiveOrderHandler.broadcast(dto);
    }
}
