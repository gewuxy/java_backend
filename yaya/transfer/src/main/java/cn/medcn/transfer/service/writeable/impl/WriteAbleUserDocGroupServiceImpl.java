package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.UserDoctorGroup;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleUserDocGroupService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by Liuchangling on 2017/6/21.
 */
public class WriteAbleUserDocGroupServiceImpl extends WriteAbleBaseServiceImpl<UserDoctorGroup> implements WriteAbleUserDocGroupService {


    @Override
    public String getTable() {
        return "t_user_doctor_group";
    }

    @Override
    public void addUserDoctorGroupFromOld(UserDoctorGroup userDoctorGroup) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insert(userDoctorGroup);
    }
}
