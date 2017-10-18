package cn.medcn.user.dao;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.user.dto.*;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.Material;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by lixuan on 2017/4/20.
 */
public interface AppUserDAO extends Mapper<AppUser> {



    List<PublicAccountDTO> searchAccount(Map<String, Object> map);

    List<PublicAccountDTO> mySubscribe(Map<String, Object> map);

    List<AppUser> findAppUsers(Map<String, Object> params);

    UnitInfoDTO findUnitInfo(Map<String, Object> map);

    List<MaterialDTO> findMaterialDTOList(Map<String, Object> map);

    List<MeetingDTO> findMeetingDTOList(Map<String, Object> map);

    List<MaterialDTO> findAllMaterialList(Map<String,Object> map);

    Integer findAllDoctorCount(Integer userId);


    List<RecommendDTO> selectRecommend(Map<String,Object> map);

    int findUndifindGroupNum(Integer userId);

    AppUser findAppUserByLoginName(@Param("loginName") String loginName);

    Integer findBindWxCount(@Param("userId") Integer userId);


    List<TestAccountDTO> findTestAccounts(Map<String, Object> params);

    List<AppUser> findAccepterList();

}
