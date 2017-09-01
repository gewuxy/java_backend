package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.MeetMessageDTO;
import cn.medcn.meet.model.MeetMessage;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface MeetMessageService extends BaseService<MeetMessage> {

    String MEET_MESSAGE_QUEUE_KEY = "meet_message_queue";

    String MEET_MESSAGE_TOPIC_KEY = "meet_message_topic";

    MeetMessage popFromQueue() throws Exception;

    void pushToQueue(MeetMessage message) throws Exception;

    MeetMessage bpopFromQuque() throws Exception;

    void publish(MeetMessageDTO message);

    /**
     * 查询会议最后N条聊天记录
     * @param pageable
     * @return
     */
    MyPage<MeetMessageDTO> findLastMessage(Pageable pageable);
}
