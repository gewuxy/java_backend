package cn.medcn.official.dao;

import cn.medcn.official.model.OffSearch;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * by create HuangHuibin 2017/11/15
 */
public interface OffSearchDao extends Mapper<OffSearch>{

    List<OffSearch> findTopHost(@Param("searchType")String searchType);
}
