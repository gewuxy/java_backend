package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.meet.dao.LiveDAO;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.model.AudioCoursePlay;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.LiveService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static cn.medcn.meet.dto.MeetMessageDTO.MessageType.live;

/**
 * Created by lixuan on 2017/9/26.
 */
@Service
public class LivceServiceImpl extends BaseServiceImpl<Live> implements LiveService {

    @Autowired
    protected LiveDAO liveDAO;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Autowired
    protected AudioService audioService;

    @Override
    public Mapper<Live> getBaseMapper() {
        return liveDAO;
    }

    @Override
    public void publish(LiveOrderDTO dto) {
        redisCacheUtils.publish(CSP_LIVE_TOPIC_KEY, dto);
    }

    @Override
    public Live findByCourseId(Integer courseId) {
        Live cond = new Live();
        cond.setCourseId(courseId);
        return liveDAO.selectOne(cond);
    }

    @Override
    public LiveOrderDTO findCachedOrder(Integer courseId) {
        LiveOrderDTO dto = (LiveOrderDTO) redisCacheUtils.getCacheObject(SYNC_CACHE_PREFIX + courseId);
        return dto;
    }

    /**
     * 修改直播状态
     */
    @Override
    public void doModifyLiveState() {
        List<Live> list = findTimeOutLives();
        if (!CheckUtils.isEmpty(list)) {
            for (Live live : list) {
                doModifyLiveState(live);
            }
        }
    }

    /**
     * 查询出所有超时的直播
     *
     * @return
     */
    @Override
    public List<Live> findTimeOutLives() {
        return liveDAO.findTimeOutLives(new Date());
    }

    /**
     * 修改直播状态
     *
     * @param live
     */
    @Override
    public void doModifyLiveState(Live live) {
        if (live.getLiveState() != AudioCoursePlay.PlayState.over.ordinal()) {
            live.setLiveState(AudioCoursePlay.PlayState.over.ordinal());
            live.setEndTime(new Date());
            liveDAO.updateByPrimaryKey(live);

            //将直播课件复制成录播课件
            audioService.doCopyLiveToRecord(live.getCourseId());

            //将当前课件设置为删除状态
            audioService.deleteCspCourse(live.getCourseId());
        }
    }
}
