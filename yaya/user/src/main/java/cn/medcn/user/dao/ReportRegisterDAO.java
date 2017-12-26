package cn.medcn.user.dao;

import cn.medcn.user.dto.ReportRegisterDetailDTO;
import cn.medcn.user.model.ReportRegister;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/12/26.
 */
public interface ReportRegisterDAO extends Mapper<ReportRegister> {

    List<ReportRegisterDetailDTO> findTodayRegisterUsers(@Param("startTime")String startTime, @Param("endTime")String endTime);
}
