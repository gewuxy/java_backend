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
 * Created by lixuan on 2017/6/15.
 */
public abstract class ReadOnlyBaseServiceImpl<T> implements ReadOnlyBaseService<T> {

    @Override
    public Connection getConnection() {
        return CommonConnctionUtils.getOldConnection();
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
    public Page findByPage(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return DAOUtils.findByPage(pageable, getConnection(), getTable(), getIdKey());
    }
}
