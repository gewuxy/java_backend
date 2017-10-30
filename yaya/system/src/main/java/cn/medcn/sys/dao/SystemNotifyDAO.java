package cn.medcn.sys.dao;

import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.model.SystemNotify;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/19.
 */
public interface SystemNotifyDAO extends Mapper<SystemNotify> {


    List<SystemNotify> findNotifyList(Map<String, Object> params);
}
