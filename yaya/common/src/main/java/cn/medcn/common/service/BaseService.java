package cn.medcn.common.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * Created by lixuan on 2017/1/4.
 */
public interface BaseService<T> {

    String DEFAULT_CACHE = "default_cache";

    String MIDDLE_TIME_CACHE = "middle_time_cache";

    Mapper<T> getBaseMapper();

    T selectOne(T var1);

    List<T> select(T var1);

    int selectCount(T var1);

    T selectByPrimaryKey(Object var1);

    int insert(T var1);

    int insertSelective(T var1);

    int delete(T var1);

    int deleteByPrimaryKey(Object var1);

    int updateByPrimaryKey(T var1);

    int updateByPrimaryKeySelective(T var1);

    MyPage<T> page(Pageable pageable);
}
