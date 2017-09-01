package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.UserDoctorGroup;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public interface WriteAbleUserDocGroupService extends WriteAbleBaseService<UserDoctorGroup>{

    void addUserDoctorGroupFromOld(UserDoctorGroup userDoctorGroup) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
