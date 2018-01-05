package cn.medcn.user.dao;

import cn.medcn.user.model.ReportPackage;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * by create HuangHuibin 2018/1/3
 */
public interface ReportPackageDAO extends Mapper<ReportPackage>{

    List<Map<String,Object>> packageDistStats(@Param("grain")Integer grain, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
}
