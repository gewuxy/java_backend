package cn.medcn.csp.admin.dao;

import cn.medcn.csp.admin.model.CspSysUser;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * by create HuangHuibin 2017/11/3
 */
public interface CspSysUserDAO extends Mapper<CspSysUser>{

    List<CspSysUser> findCspSysUser(Map<String, Object> params);
}
