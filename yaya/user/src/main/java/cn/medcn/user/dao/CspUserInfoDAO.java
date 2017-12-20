package cn.medcn.user.dao;

import cn.medcn.user.dto.CspNewlyStaticDTO;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.dto.VideoLiveRecordDTO;
import cn.medcn.user.model.CspUserInfo;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/9/26.
 */
public interface CspUserInfoDAO extends Mapper<CspUserInfo> {

    CspUserInfo findCspUserById(@Param("userId") String userId);

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

    /**
     * 视频直播记录
     * @param params
     * @return
     */
    List<VideoLiveRecordDTO> findVideoLiveRecord(Map<String, Object> params);

    List<CspUserInfo> findCspUserList(Map<String, Object> params);

    CspUserInfo selectByUserName(String userName);

    List<CspUserInfo> findUserList(Map<String, Object> params);

    CspUserInfo selectByMobile(String mobile);
    List<CspUserInfo> selectRegisterTime();

    int selectRegisterCount(int location);

    CspUserInfo selectByEmail(String username);

    int selectNewUser();

    int selectAllUserCount();

    List<CspNewlyStaticDTO> findNewlyRegisterList(Map<String, Object> map);

    List<CspUserInfo> selectByIp();
}
