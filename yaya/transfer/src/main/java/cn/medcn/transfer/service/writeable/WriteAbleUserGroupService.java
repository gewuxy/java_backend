package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.readonly.PubuserGroupReadOnly;
import cn.medcn.transfer.model.writeable.UserGroup;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public interface WriteAbleUserGroupService extends WriteAbleBaseService<UserGroup> {

    Object addUserGroupFromOldGroup(UserGroup userGroup) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

}
