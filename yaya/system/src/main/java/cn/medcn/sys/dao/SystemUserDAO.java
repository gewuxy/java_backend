package cn.medcn.sys.dao;

import cn.medcn.sys.model.SystemUser;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/2.
 */
public interface SystemUserDAO extends Mapper<SystemUser> {

    List<SystemUser> findUserByPage(Map<String, Object> params);
}
