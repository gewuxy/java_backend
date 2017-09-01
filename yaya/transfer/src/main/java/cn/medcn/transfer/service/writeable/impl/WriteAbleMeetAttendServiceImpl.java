package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetAttend;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetAttendService;

/**
 * Created by lixuan on 2017/6/22.
 */
public class WriteAbleMeetAttendServiceImpl extends WriteAbleBaseServiceImpl<MeetAttend> implements WriteAbleMeetAttendService {
    @Override
    public String getTable() {
        return "t_meet_attend";
    }
}
