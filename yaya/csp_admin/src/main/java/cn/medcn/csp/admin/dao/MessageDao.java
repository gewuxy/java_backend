package cn.medcn.csp.admin.dao;

import cn.medcn.csp.admin.model.CspAdminMessage;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

public interface MessageDao extends Mapper<CspAdminMessage>{
    List<CspAdminMessage> findMessageListByPage(Map<String, Object> params);
}
