package cn.medcn.meet.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.meet.dao.MeetMessageDAO;
import cn.medcn.meet.dto.MeetMessageDTO;
import cn.medcn.meet.model.MeetMessage;
import cn.medcn.meet.service.MeetMessageService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/4/25.
 */
@Service
public class MeetMessageServiceImpl extends BaseServiceImpl<MeetMessage> implements MeetMessageService{

    @Autowired
    private MeetMessageDAO meetMessageDAO;

    @Override
    public Mapper<MeetMessage> getBaseMapper() {
        return meetMessageDAO;
    }

    @Autowired
    private RedisCacheUtils<MeetMessage> redisCacheUtils;

    @Override
    public MeetMessage popFromQueue() throws Exception {
        return redisCacheUtils.popFromQueue(MEET_MESSAGE_QUEUE_KEY);
    }

    @Override
    public void pushToQueue(MeetMessage message) throws Exception {
        redisCacheUtils.pushToQueue(MEET_MESSAGE_QUEUE_KEY, message);
    }

    @Override
    public MeetMessage bpopFromQueue() throws Exception {
        MeetMessage message = redisCacheUtils.bRPopFromQueue(MEET_MESSAGE_QUEUE_KEY);
        return message;
    }

    @Override
    public void publish(MeetMessageDTO message) {
        //放到持久化队列中
        redisCacheUtils.pushToQueue(MEET_MESSAGE_QUEUE_KEY, MeetMessageDTO.buildToMessage(message));
        //发布到频道 MEET_MESSAGE_TOPIC_KEY
        redisCacheUtils.publish(MEET_MESSAGE_TOPIC_KEY, message);
    }


    /**
     * 查询会议最后N条聊天记录
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetMessageDTO> findLastMessage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(), Pageable.countPage);
        Page<MeetMessageDTO> page = (Page<MeetMessageDTO>) meetMessageDAO.findMeetMessageDTO(pageable.getParams());
        return MyPage.page2Mypage(page);
    }
}
