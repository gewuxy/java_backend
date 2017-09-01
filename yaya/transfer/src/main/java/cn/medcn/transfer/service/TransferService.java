package cn.medcn.transfer.service;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/6/14.
 */
public interface TransferService {


    void transfer() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;


    void transferByOwner(String owner) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, IOException;

    /**
     * 根据起始会议ID转换单位号的会议
     * @param owner
     * @param date
     */
    void transferMeetByOwner(String owner, String date) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, IOException;

    /**
     * 根据起始ID转换用户信息
     * @param date
     */
    void transferDoctor(String date) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, IOException;

    void transferPubUser(String owner, String date) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transferByOwnerAndDate(String owner, String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;
}
