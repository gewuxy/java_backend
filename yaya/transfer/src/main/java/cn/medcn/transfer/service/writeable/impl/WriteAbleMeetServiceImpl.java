package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.Lecturer;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.model.writeable.MeetModule;
import cn.medcn.transfer.model.writeable.MeetProperty;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetLecturerService;
import cn.medcn.transfer.service.writeable.WriteAbleMeetPropertyService;
import cn.medcn.transfer.service.writeable.WriteAbleMeetService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/16.
 */
public class WriteAbleMeetServiceImpl extends WriteAbleBaseServiceImpl<Meet> implements WriteAbleMeetService {

    private WriteAbleMeetLecturerService writeAbleMeetLecturerService = new WriteAbleMeetLecturerServiceImpl();

    private WriteAbleMeetPropertyService writeAbleMeetPropertyService = new WriteAbleMeetPropertyServiceImpl();

    @Override
    public String getTable() {
        return "t_meet";
    }

    @Override
    public void transferMeet(Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insert(meet);
    }


    @Override
    public void addMeetLecurter(Lecturer lecturer) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        writeAbleMeetLecturerService.insert(lecturer);
    }

    @Override
    public void transferMeetProperty(MeetProperty meetProperty) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        writeAbleMeetPropertyService.insert(meetProperty);
    }

    @Override
    public boolean checkExisted(Integer oldId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if(oldId == null || oldId == 0){
            return false;
        }
        Meet condition = new Meet();
        condition.setOldId(oldId);
        Meet meet = findOne(condition);
        return meet != null;
    }
}
