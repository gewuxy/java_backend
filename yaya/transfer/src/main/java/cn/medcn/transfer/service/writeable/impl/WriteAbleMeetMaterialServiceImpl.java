package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetMaterial;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetMaterialService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/21.
 */
public class WriteAbleMeetMaterialServiceImpl extends WriteAbleBaseServiceImpl<MeetMaterial> implements WriteAbleMeetMaterialService {


    @Override
    public String getTable() {
        return "t_meet_material";
    }


    @Override
    public void addMeetMaterial(MeetMaterial meetMaterial) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insert(meetMaterial);
    }
}
