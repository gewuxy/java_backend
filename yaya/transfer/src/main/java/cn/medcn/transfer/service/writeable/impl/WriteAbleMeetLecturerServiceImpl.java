package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.Lecturer;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetLecturerService;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleMeetLecturerServiceImpl extends WriteAbleBaseServiceImpl<Lecturer> implements WriteAbleMeetLecturerService {
    @Override
    public String getTable() {
        return "t_meet_lecturer";
    }


}
