package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.readonly.PubuserGroupReadOnly;
import cn.medcn.transfer.model.writeable.UserGroup;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleUserGroupService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class WriteAbleUserGroupServiceImpl extends WriteAbleBaseServiceImpl<UserGroup> implements WriteAbleUserGroupService {
    @Override
    public String getTable() {
        return "t_user_group";
    }

    @Override
    public Object addUserGroupFromOldGroup(UserGroup userGroup) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return insertReturnId(userGroup);
    }
}
