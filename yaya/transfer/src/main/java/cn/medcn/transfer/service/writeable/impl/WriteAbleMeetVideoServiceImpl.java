package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetVideo;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetVideoService;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleMeetVideoServiceImpl extends WriteAbleBaseServiceImpl<MeetVideo> implements WriteAbleMeetVideoService {
    @Override
    public String getTable() {
        return "t_meet_video";
    }
}
