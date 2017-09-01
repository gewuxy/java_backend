package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.PubUserMemberReadOnly;
import cn.medcn.transfer.model.writeable.AppAttention;
import cn.medcn.transfer.model.writeable.UserDoctorGroup;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferPubuserMemberService;
import cn.medcn.transfer.service.writeable.WriteAbleAppAttentionService;
import cn.medcn.transfer.service.writeable.WriteAbleUserDocGroupService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleAppAttentionServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleUserDocGroupServiceImpl;
import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class TransferPubuserMemberServiceImpl extends ReadOnlyBaseServiceImpl<PubUserMemberReadOnly> implements TransferPubuserMemberService {

    private WriteAbleAppAttentionService writeAbleAppAttentionService = new WriteAbleAppAttentionServiceImpl();
    private WriteAbleUserDocGroupService writeAbleUserDocGroupService = new WriteAbleUserDocGroupServiceImpl();

    @Override
    public String getIdKey() {
        return "pum_id";
    }

    @Override
    public String getTable() {
        return "t_public_usermember";
    }


    /**
     * 查询公众号粉丝列表
     * @return
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public List<PubUserMemberReadOnly> findPubuserMemberList() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        PubUserMemberReadOnly condition = new PubUserMemberReadOnly();
        List<PubUserMemberReadOnly> list = (List<PubUserMemberReadOnly>)
                DAOUtils.selectList(getConnection(),condition,getTable());
        return list;
    }

    @Override
    public List<PubUserMemberReadOnly> findPubuserMemberList(Integer userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        PubUserMemberReadOnly condition = new PubUserMemberReadOnly();
        condition.setPub_user_id(userId);
        List<PubUserMemberReadOnly> list = (List<PubUserMemberReadOnly>)
                DAOUtils.selectList(getConnection(),condition,getTable());
        return list;
    }

    @Override
    public void transferPubUserMemberByUserId(Integer userId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        List<PubUserMemberReadOnly> userMemberList = findPubuserMemberList(userId);
        LogUtils.debug(TransferPubuserMemberServiceImpl.class, "=== 转换公众号粉丝数据 start ===");
        for (PubUserMemberReadOnly userMemberReadOnly : userMemberList) {
            AppAttention appAttention = AppAttention.build(userMemberReadOnly);
            // 转换公众号粉丝用户
            writeAbleAppAttentionService.addAppAttentionFromPubUserMember(appAttention);
        }
        LogUtils.debug(TransferPubuserMemberServiceImpl.class, "=== 转换公众号粉丝数据 end ===");
    }


    private List<PubUserMemberReadOnly> findPubUserMemberByUserAndDate(Integer userId, String date) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select * from t_public_usermember where pub_user_id = ? and attention_time > ?";
        Object[] params = {userId, date};
        List<PubUserMemberReadOnly> list = (List<PubUserMemberReadOnly>) DAOUtils.selectList(getConnection(), sql, params, PubUserMemberReadOnly.class);
        return list;
    }

    @Override
    public void transferUserDoctorGroupByUserId(Integer userId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<PubUserMemberReadOnly> userMemberList = findPubuserMemberList(userId);
        LogUtils.debug(TransferPubuserMemberServiceImpl.class, "=== 转换公众号粉丝用户分组 start ===");
        for (PubUserMemberReadOnly userMemberReadOnly : userMemberList){
            UserDoctorGroup userDoctorGroup = UserDoctorGroup.build(userMemberReadOnly);
            if (userDoctorGroup.getId()!=null) {
                writeAbleUserDocGroupService.addUserDoctorGroupFromOld(userDoctorGroup);
            }
        }
        LogUtils.debug(TransferUserGroupServiceImpl.class,"转换公众号用户分组数据 end");
    }

    /**
     * 根据组ID 查询分组成员
     * @param groupId
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<PubUserMemberReadOnly> getPubUserListById(Integer groupId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        PubUserMemberReadOnly condition = new PubUserMemberReadOnly();
        condition.setGroup_id(groupId);
        List<PubUserMemberReadOnly> userList = (List<PubUserMemberReadOnly>)
                DAOUtils.selectList(getConnection(),condition,getTable());
        return userList;
    }


    /**
     * 分页查询公众号粉丝数据
     * @return
     */
    @Override
    public Page findPubuserMemberByPage(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        Page page = DAOUtils.findByPage(pageable,getConnection(),getTable(),getIdKey());
        return page;
    }

    /**
     * 将旧的公众号粉丝数据 转换到 新的公众号粉丝表
     */
    @Override
    public int transferPubUserMember(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
       // List<PubUserMemberReadOnly> userMemberList = findPubuserMemberList();
        Page page = findPubuserMemberByPage(pageable);
        List<PubUserMemberReadOnly> userMemberList = (List<PubUserMemberReadOnly>)page.getDatas();
        LogUtils.debug(TransferPubuserMemberServiceImpl.class, "=== 转换公众号粉丝数据 start ===");
        for (PubUserMemberReadOnly userMemberReadOnly : userMemberList) {
            AppAttention appAttention = AppAttention.build(userMemberReadOnly);
            writeAbleAppAttentionService.addAppAttentionFromPubUserMember(appAttention);
        }
        LogUtils.debug(TransferPubuserMemberServiceImpl.class, "=== 转换公众号粉丝数据 end ===");
        return page.getPages();
    }

    /**
     * 转换公众号粉丝用户数据
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void transferPubMemberdata() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Pageable pageable = new Pageable();
        pageable.setPageNum(1);
        pageable.setPageSize(10000);
        PubUserMemberReadOnly userMemberReadOnly = new PubUserMemberReadOnly();
        pageable.setCondition(userMemberReadOnly);
        int pages = transferPubUserMember(pageable);
        for (int i=2;i<=pages;i++){
            pageable.setPageNum(i);
            transferPubUserMember(pageable);
        }
    }

    // 转换公众号粉丝用户分组
    @Override
    public int transferUserDoctorGroup(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
       // List<PubUserMemberReadOnly> userMemberList = findPubuserMemberList();
        Page page = findPubuserMemberByPage(pageable);
        List<PubUserMemberReadOnly> userMemberList = (List<PubUserMemberReadOnly>)page.getDatas();
        LogUtils.debug(TransferPubuserMemberServiceImpl.class, "=== 转换公众号粉丝用户分组 start ===");
        for (PubUserMemberReadOnly userMemberReadOnly : userMemberList){
            UserDoctorGroup userDoctorGroup = UserDoctorGroup.build(userMemberReadOnly);
            if (userDoctorGroup.getId()!=null) {
                writeAbleUserDocGroupService.addUserDoctorGroupFromOld(userDoctorGroup);
            }
        }
        LogUtils.debug(TransferUserGroupServiceImpl.class,"转换公众号用户分组数据 end");
        return  page.getPages();
    }

    /**
     * 转换公众号粉丝用户分组
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void transferUserDoctorGroupData() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Pageable pageable = new Pageable();
        pageable.setPageNum(1);
        pageable.setPageSize(10000);
        PubUserMemberReadOnly userMemberReadOnly = new PubUserMemberReadOnly();
        pageable.setCondition(userMemberReadOnly);
        int pages = transferUserDoctorGroup(pageable);
        for (int i=2;i<=pages;i++){
            pageable.setPageNum(i);
            transferUserDoctorGroup(pageable);
        }
    }


    @Override
    public void transferPubMemberByUserAndDate(Integer userId, String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<PubUserMemberReadOnly> userMemberList = findPubUserMemberByUserAndDate(userId, date);
        LogUtils.debug(TransferPubuserMemberServiceImpl.class, "=== 转换公众号粉丝用户分组 start ===");
        for (PubUserMemberReadOnly userMemberReadOnly : userMemberList){
            AppAttention appAttention = AppAttention.build(userMemberReadOnly);
            writeAbleAppAttentionService.addAppAttentionFromPubUserMember(appAttention);
            UserDoctorGroup userDoctorGroup = UserDoctorGroup.build(userMemberReadOnly);
            if (userDoctorGroup.getId()!=null) {
                writeAbleUserDocGroupService.addUserDoctorGroupFromOld(userDoctorGroup);
            }
        }
        LogUtils.debug(TransferUserGroupServiceImpl.class,"转换公众号用户分组数据 end");
    }
}
