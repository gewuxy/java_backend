package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetProperty;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetPropertyService;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleMeetPropertyServiceImpl extends WriteAbleBaseServiceImpl<MeetProperty> implements WriteAbleMeetPropertyService {
    @Override
    public String getTable() {
        return "t_meet_prop";
    }



}
