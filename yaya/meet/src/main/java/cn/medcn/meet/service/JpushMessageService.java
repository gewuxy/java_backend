package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.JpushMessageDTO;
import cn.medcn.meet.model.JpushMessage;
import cn.medcn.meet.model.JpushMessageHistory;

/**
 * Created by lixuan on 2017/6/7.
 */
public interface JpushMessageService extends BaseService<JpushMessage>{


    String JPUSH_MESSAGE_QUEUE_KEY = "jpush_message_queue";

    /**
     * 分页查询消息记录
     * @param pageable
     * @return
     */
    MyPage<JpushMessageDTO> findJpushMessage(Pageable pageable);

    /**
     * 保存消息查看记录
     * @param history
     */
    void insertHistory(JpushMessageHistory history);

    /**
     * 放到队列中
     * @param message
     */
    void appendToQueue(JpushMessage message);

    /**
     * 发送消息并持久化
     * @param message
     */
    void doSend(JpushMessage message);

    JpushMessage brpopFromQueue();

    /**
     * 查询消息发送历史
     * @param pageable
     * @param sender
     * @return
     */
    MyPage<JpushMessageDTO> findHistories(Pageable pageable, Integer sender);
}
