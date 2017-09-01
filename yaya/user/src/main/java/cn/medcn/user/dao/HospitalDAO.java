package cn.medcn.user.dao;

import cn.medcn.user.dto.HospitalLevelDTO;
import cn.medcn.user.model.Hospital;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/24.
 */
public interface HospitalDAO extends Mapper<Hospital> {

    List<String> findPorvinces();

    List<String> findCities(@Param("province")String province);

    List<String> findHospitals(@Param("city")String city);

    List<Hospital> pageHospitals(Map<String, Object> params);

    List<HospitalLevelDTO> findAllLevels();
}
