package cn.medcn.user.dao;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.user.dto.DoctorDTO;
import cn.medcn.user.model.AppDoctor;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/21.
 */
public interface AppDoctorDAO extends Mapper<AppDoctor> {

    List<DoctorDTO> findAllDoctorInfo(Map<String, Object> params);

    List<DoctorDTO> findDoctorInfoByGId(Map<String, Object> params);

    DoctorDTO findDoctorInfo(Map<String,Object> map);

    DoctorDTO getPopUpInfo(@Param("docId") Integer docId, @Param("ownerId") Integer ownerId);


    List<DoctorDTO> findUndifindGroupDoctorInfo(Map<String, Object> params);

    List<DoctorDTO> findBindWxDoctorInfo(Map<String, Object> params);
}
