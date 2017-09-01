package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.DoctorReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;
import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/15.
 */
public interface TransferDoctorService extends ReadOnlyBaseService<DoctorReadOnly> {


    /**
     * 转换医生信息
     * @param doctorReadOnly
     * @param headimg
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    void transfer(DoctorReadOnly doctorReadOnly,String headimg) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;

    void addAppUser(DoctorReadOnly doctorReadOnly,String headimg) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    /**
     * 查询所有的医生数据
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    List findUserList() throws InvocationTargetException,IntrospectionException,InstantiationException,IllegalAccessException;

    /**
     * 获取医生头像
     * @param doctorId
     * @return
     */
    Blob getUserImgBlob(Long doctorId) throws SQLException;

    /**
     * 转换所有医生用户数据
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws SQLException
     */
    int transferAppUser(Pageable pageable) throws InvocationTargetException,IntrospectionException,InstantiationException,IllegalAccessException, IOException, SQLException;

    // 分页转换数据
    Page findUserListByPage(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    DoctorReadOnly findPubUserByUname(String userName) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;


    void transferUserdata() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException;


    // 根据用户名 转换用户相关数据
    void transferUserByUsername(String username) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, SQLException, IOException;

    /**
     * 查找医生信息
     * @param doctorId
     */
    DoctorReadOnly findUserInfoById(Long doctorId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    List<DoctorReadOnly> findByDate(String date) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transferDoctorByDate(String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;

    void transferPubUserByDate(String owner, String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
