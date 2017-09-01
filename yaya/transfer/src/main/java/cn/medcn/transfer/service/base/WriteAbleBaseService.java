package cn.medcn.transfer.service.base;

import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public interface WriteAbleBaseService<T> {

    String ID_KEY = "id";

    String getTable();

    Connection getConnection();

    Object insert(T entity) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;;

    T findOne(T condition) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    List<T> findList(T condition) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    Object getMaxId();

    String getIdKey();

    Object insertReturnId(T entity) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    Page findByPage(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

}
