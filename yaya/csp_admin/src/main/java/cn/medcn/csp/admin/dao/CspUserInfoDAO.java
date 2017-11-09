package cn.medcn.csp.admin.dao;


import cn.medcn.csp.admin.model.CspUserInfo;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * create by huanghuibin on 2017/11/8
 */
public interface CspUserInfoDAO extends Mapper<CspUserInfo> {

    List<CspUserInfo> findCspUserList(Map<String, Object> params);
}
