package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.model.Live;

/**
 * Created by lixuan on 2017/9/26.
 */
public interface LiveService extends BaseService<Live> {

    String CSP_LIVE_TOPIC_KEY = "csp_live_topic";

    void publish(LiveOrderDTO dto);

}
