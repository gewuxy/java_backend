package cn.medcn.sys.dao;

import cn.medcn.sys.model.SystemRegion;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/4.
 */
public interface SystemRegionDAO extends Mapper<SystemRegion> {

    List<SystemRegion> findByPage(Map<String, Object> params);

    List<SystemRegion> findAll();
}
