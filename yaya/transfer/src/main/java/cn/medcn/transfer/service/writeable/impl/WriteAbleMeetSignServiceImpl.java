package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetSign;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetSignService;

/**
 * Created by lixuan on 2017/6/22.
 */
public class WriteAbleMeetSignServiceImpl extends WriteAbleBaseServiceImpl<MeetSign> implements WriteAbleMeetSignService {
    @Override
    public String getTable() {
        return "t_meet_sign";
    }
}
