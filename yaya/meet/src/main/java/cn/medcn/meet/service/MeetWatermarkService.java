package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.model.MeetMaterial;
import cn.medcn.meet.model.MeetWatermark;

/**
 * Created by lixuan on 2017/6/9.
 */
public interface MeetWatermarkService extends BaseService<MeetWatermark> {

    MeetWatermark findWatermarkByCourseId(Integer courseId);
}
