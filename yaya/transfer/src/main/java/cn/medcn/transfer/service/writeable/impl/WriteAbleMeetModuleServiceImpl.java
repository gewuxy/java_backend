package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetModule;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetModuleService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleMeetModuleServiceImpl extends WriteAbleBaseServiceImpl<MeetModule> implements WriteAbleMeetModuleService {
    @Override
    public String getTable() {
        return "t_meet_module";
    }


    @Override
    public Integer addMeetmodule(MeetModule module) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Long id = (Long) insert(module);
        module.setId(id.intValue());
        return id.intValue();
    }
}
