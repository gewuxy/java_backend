package cn.medcn.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/1/4.
 */
public interface BaseDAO<T,PK extends Serializable>{

    int deleteByPrimaryKey(PK id);

    int insert(T record);

    T selectByPrimaryKey(PK id);

    int updateByPrimaryKey(T record);

    List<T> findByEntity(T entity);

    List<T> findByParams(Map<String, Object> params);

    T findOneByParams(Map<String, Object> params);

    T findOneByEntity(T entity);
}
