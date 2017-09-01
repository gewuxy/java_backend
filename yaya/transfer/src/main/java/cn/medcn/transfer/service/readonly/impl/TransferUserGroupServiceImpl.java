package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.PubUserMemberReadOnly;
import cn.medcn.transfer.model.readonly.PubuserGroupReadOnly;
import cn.medcn.transfer.model.writeable.UserDoctorGroup;
import cn.medcn.transfer.model.writeable.UserGroup;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferPubuserMemberService;
import cn.medcn.transfer.service.readonly.TransferUserGroupService;
import cn.medcn.transfer.service.writeable.WriteAbleAppUserService;
import cn.medcn.transfer.service.writeable.WriteAbleUserDocGroupService;
import cn.medcn.transfer.service.writeable.WriteAbleUserGroupService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleAppUserServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleUserDocGroupServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleUserGroupServiceImpl;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class TransferUserGroupServiceImpl extends ReadOnlyBaseServiceImpl<PubuserGroupReadOnly> implements TransferUserGroupService {

    private WriteAbleUserGroupService writeAbleUserGroupService = new WriteAbleUserGroupServiceImpl();

    @Override
    public String getIdKey() {
        return "pub_groupid";
    }

    @Override
    public String getTable() {
        return "t_pubuser_group";
    }

    // 查询 旧的公众号分组数据
    @Override
    public List<PubuserGroupReadOnly> getPubUserGroupList() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select * from t_pubuser_group where pub_groupid > 0";
        List<PubuserGroupReadOnly> list = (List<PubuserGroupReadOnly>)
                DAOUtils.selectList(getConnection(),sql,null,PubuserGroupReadOnly.class);
        return list;
    }


    // 转移对应的旧的公众号分组数据 到新的分组表
    @Override
    public void transferPubUserGroup() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<PubuserGroupReadOnly> list = getPubUserGroupList();
        LogUtils.debug(TransferUserGroupServiceImpl.class,"=== 转换公众号分组数据start ===");
        for (PubuserGroupReadOnly pubuserGroupReadOnly : list){
            UserGroup userGroup = UserGroup.build(pubuserGroupReadOnly);
            // 添加新的分组数据
            Object obj = (Object)writeAbleUserGroupService.addUserGroupFromOldGroup(userGroup);
            LogUtils.debug(TransferUserGroupServiceImpl.class,"公众号ID【"+userGroup.getId()
                             +"】 分组【"+userGroup.getGroupName()+"】");

        }
        LogUtils.debug(TransferUserGroupServiceImpl.class,"=== 转换公众号分组数据end ===");
    }

    @Override
    public List<PubuserGroupReadOnly> getPubUserGroupListById(Integer userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select * from t_pubuser_group where pub_groupid > 0 and pub_user_id=?";
        Object[] params = {userId};
        List<PubuserGroupReadOnly> list = (List<PubuserGroupReadOnly>)
                DAOUtils.selectList(getConnection(),sql,params,PubuserGroupReadOnly.class);
        return list;
    }

    @Override
    public void transferPubUserGroup(Integer userId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<PubuserGroupReadOnly> list = getPubUserGroupListById(userId);
        LogUtils.debug(TransferUserGroupServiceImpl.class,"=== 转换公众号分组数据start ===");
        for (PubuserGroupReadOnly pubuserGroupReadOnly : list){
            UserGroup userGroup = UserGroup.build(pubuserGroupReadOnly);
            // 添加新的分组数据
            Object obj = (Object)writeAbleUserGroupService.addUserGroupFromOldGroup(userGroup);
            LogUtils.debug(TransferUserGroupServiceImpl.class,"公众号ID【"+userGroup.getId()
                    +"】 分组【"+userGroup.getGroupName()+"】");

        }
        LogUtils.debug(TransferUserGroupServiceImpl.class,"=== 转换公众号分组数据end ===");
    }


}
