package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.model.Live;

import java.util.List;

/**
 * Created by lixuan on 2017/9/26.
 */
public interface LiveService extends BaseService<Live> {

    String CSP_LIVE_TOPIC_KEY = "csp_live_topic";

    //同步指令缓存前缀
    String SYNC_CACHE_PREFIX = "sync_cache_";

    void publish(LiveOrderDTO dto);

    Live findByCourseId(Integer courseId);

    LiveOrderDTO findCachedOrder(Integer courseId);

    /**
     * 修改直播状态
     */
    void doModifyLiveState();

    /**
     * 查询出所有超时的直播
     * @return
     */
    List<Live> findTimeOutLives();

    /**
     * 修改直播状态
     * @param live
     */
    void doModifyLiveState(Live live);

}
