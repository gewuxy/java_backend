package cn.medcn.user.dao;

import cn.medcn.user.model.Patient;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Liuchangling on 2017/11/23.
 * 合理用药 用户DAO
 */

public interface PatientUserDAO extends Mapper<Patient>{

    Patient findBindUserByUniqueId(@Param("uniqueId") String uniqueId);
}
