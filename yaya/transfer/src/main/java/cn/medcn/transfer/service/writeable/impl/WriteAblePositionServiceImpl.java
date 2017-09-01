package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetPosition;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAblePositionService;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAblePositionServiceImpl extends WriteAbleBaseServiceImpl<MeetPosition> implements WriteAblePositionService {
    @Override
    public String getTable() {
        return "t_meet_position";
    }
}
