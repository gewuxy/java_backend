package cn.medcn.transfer.service.base;

import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;
import cn.medcn.transfer.utils.CommonConnctionUtils;
import cn.medcn.transfer.utils.DAOUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public abstract class WriteAbleBaseServiceImpl<T> implements WriteAbleBaseService<T> {

    @Override
    public Connection getConnection() {
        return CommonConnctionUtils.getNewConnection();
    }

    @Override
    public Object insert(T entity) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return DAOUtils.insertReturnId(getConnection(), entity, getTable());
    }

    @Override
    public T findOne(T condition) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return (T) DAOUtils.selectOne(getConnection(), condition, getTable());
    }

    @Override
    public List<T> findList(T condition) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return (List<T>) DAOUtils.selectList(getConnection(), condition, getTable());
    }

    @Override
    public Object getMaxId() {
        return DAOUtils.getMaxId(this.getConnection(), getTable(), getIdKey());
    }

    @Override
    public String getIdKey() {
        return ID_KEY;
    }

    @Override
    public Object insertReturnId(T entity) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return DAOUtils.insertReturnId(getConnection(), entity, getTable());
    }

    @Override
    public Page findByPage(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return DAOUtils.findByPage(pageable, getConnection(), getTable(), getIdKey());
    }

}
