package cn.medcn.user.dao;

import cn.medcn.user.model.CspUserPackageHistory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/12.
 */
public interface CspUserPackageHistoryDAO extends Mapper<CspUserPackageHistory> {

    CspUserPackageHistory getLastHistoryByUserId(@Param("userId") String userId);

    List<Map<String,Object>> renewStats(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
}
