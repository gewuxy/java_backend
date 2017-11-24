package cn.medcn.sys.dao;

import cn.medcn.sys.model.SystemLog;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * by create HuangHuibin 2017/11/23
 */
public interface SystemLogDAO extends Mapper<SystemLog>{

    List<SystemLog> findLogByPage(Map<String, Object> params);
}
