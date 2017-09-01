package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.PubuserGroupReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public interface TransferUserGroupService extends ReadOnlyBaseService<PubuserGroupReadOnly>{

    // 查询 旧的公众号分组数据
    List<PubuserGroupReadOnly> getPubUserGroupList() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;


    // 转移所有公众号分组数据 到新的分组表
    void transferPubUserGroup() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;



    // 查询 旧的公众号分组数据
    List<PubuserGroupReadOnly> getPubUserGroupListById(Integer userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;


    // 转移所有公众号分组数据 到新的分组表
    void transferPubUserGroup(Integer userId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;




}
