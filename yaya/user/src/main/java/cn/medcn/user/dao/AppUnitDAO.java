package cn.medcn.user.dao;

import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.dto.UnitAccountDTO;
import cn.medcn.user.model.AppUnit;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/21.
 */
public interface AppUnitDAO extends Mapper<AppUnit>{

    void deleteDetail(@Param("userId") Integer userId);

    List<UnitAccountDTO> findUnitAccounts(Map<String, Object> params);

    List<AppUserDTO> findDoctorAccounts(Map<String, Object> params);

    /**
     * 批量导入粉丝
     * @param params
     */
    void importFans(Map<String, Object> params);
}
