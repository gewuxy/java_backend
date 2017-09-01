package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.Lecturer;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.model.writeable.MeetProperty;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/16.
 */
public interface WriteAbleMeetService extends WriteAbleBaseService<Meet> {

    void transferMeet(Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void addMeetLecurter(Lecturer lecturer) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transferMeetProperty(MeetProperty meetProperty) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    boolean checkExisted(Integer oldId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
