package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.PubUserMemberReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;
import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public interface TransferPubuserMemberService extends ReadOnlyBaseService<PubUserMemberReadOnly> {

    /**
     * 查询公众号粉丝列表
     * @return
     */
    List<PubUserMemberReadOnly> findPubuserMemberList() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    List<PubUserMemberReadOnly> findPubuserMemberList(Integer userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transferPubUserMemberByUserId(Integer userId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;

    void transferUserDoctorGroupByUserId(Integer userId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    // 根据分组ID查询 用户列表
    List<PubUserMemberReadOnly> getPubUserListById(Integer groupId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;


    /**
     * 转换公众账号数据
     */
    int transferPubUserMember(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;


    // 转换公众号粉丝用户分组
    int transferUserDoctorGroup(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    // 分页查询公众号粉丝数据
    Page findPubuserMemberByPage(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    /**
     * 转换公众号粉丝用户数据
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    void transferPubMemberdata() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * 转换公众号粉丝用户分组
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    void transferUserDoctorGroupData() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;


    void transferPubMemberByUserAndDate(Integer userId, String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
