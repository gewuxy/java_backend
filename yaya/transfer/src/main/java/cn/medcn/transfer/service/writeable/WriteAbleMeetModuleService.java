package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.MeetModule;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public interface WriteAbleMeetModuleService extends WriteAbleBaseService<MeetModule> {

    Integer addMeetmodule(MeetModule module) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
