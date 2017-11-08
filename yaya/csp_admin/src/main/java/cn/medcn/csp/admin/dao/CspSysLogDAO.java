package cn.medcn.csp.admin.dao;

import cn.medcn.csp.admin.model.CspSysLog;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * by create HuangHuibin 2017/11/7
 */
public interface CspSysLogDAO extends Mapper<CspSysLog>{

    List<CspSysLog> findCspSysLog(Map<String, Object> params);
}
