package cn.medcn.user.dao;

import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.model.CspUserInfo;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Liuchangling on 2017/9/26.
 */
public interface CspUserInfoDAO extends Mapper<CspUserInfo> {

    /**
     * 根据uniqueId 查询用户是否存在
     * @param uniqueId
     * @return
     */
    CspUserInfo findBindUserByUniqueId(@Param("uniqueId") String uniqueId);

    /**
     * 根据邮箱或者手机号码检查csp账号 是否存在
     * @param username
     * @return
     */
    CspUserInfo findByLoginName(@Param("username") String username);


    /**
     * csp web端查找用户信息
     * @param userId
     * @return
     */
    CspUserInfoDTO findCSPUserInfo(String userId);
}
