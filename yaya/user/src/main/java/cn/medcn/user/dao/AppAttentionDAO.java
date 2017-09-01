package cn.medcn.user.dao;

import cn.medcn.user.dto.AppUserSimpleDTO;
import cn.medcn.user.dto.UserAttendDTO;
import cn.medcn.user.dto.UserDataDetailDTO;
import cn.medcn.user.model.AppAttention;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/21.
 */
public interface AppAttentionDAO extends Mapper<AppAttention> {

    UserAttendDTO findAttendPubUserCount(@Param("userId") Integer userId);

    Integer findTotalAttendCount(@Param("userId") Integer userId);

    List<UserDataDetailDTO> findUserDataByCity(Map<String,Object> params);

    List<UserDataDetailDTO> findUserDataByHos(Map<String,Object> params);

    List<UserDataDetailDTO> findUserDataByTitle(Map<String,Object> params);

    List<UserDataDetailDTO> findUserDataByDept(Map<String,Object> params);

    List<UserDataDetailDTO> findUserDataByProvince(Map<String,Object> params);

    /**
     * 查询已关注用户的简单信息
     * @param userId
     * @return
     */
    List<AppUserSimpleDTO> findAttentions(@Param("userId") Integer userId);

    /**
     * 查询已关注用户的简单信息
     * @param userId
     * @param groupId
     * @return
     */
    List<AppUserSimpleDTO> findAttentionsByGroupId(@Param("userId") Integer userId, @Param("groupId")Integer groupId);
}
