package cn.medcn.transfer.service.base;

import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by lixuan on 2017/6/15.
 */
public interface ReadOnlyBaseService<T> {

    String getIdKey();

    String getTable();

    Connection getConnection();

    T findOne(T condition) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    List<T> findList(T condition) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    Page findByPage(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
