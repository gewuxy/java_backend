package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.MeetMaterial;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/21.
 */
public interface WriteAbleMeetMaterialService extends WriteAbleBaseService<MeetMaterial> {

    void addMeetMaterial(MeetMaterial meetMaterial) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
