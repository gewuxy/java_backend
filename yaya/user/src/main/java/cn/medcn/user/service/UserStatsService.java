package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.UserAttendDTO;
import cn.medcn.user.dto.UserDataDetailDTO;
import cn.medcn.user.model.AppAttention;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;


/**
 * Created by Liuchangling on 2017/5/18.
 */
public interface UserStatsService extends BaseService<AppAttention>{

    /**
     * 查询本周、本月、所有 关注公众号的用户数
     * @param userId
     * @return
     */
    UserAttendDTO findAttendCount(Integer userId);

    /**
     * 查询关注公众号的 所有用户数
     * @param userId
     * @return
     */
    Integer findTotalAttendCount(Integer userId);

    /**
     * 查询关注公众号的所有用户 所在城市的占比数
     * @param pageable
     * @return
     */
    MyPage<UserDataDetailDTO> findUserDataByCity(Pageable pageable);

    List<UserDataDetailDTO> findDataByCity(Map<String,Object> params);

    /**
     * 查询关注公众号的所有用户 所在医院级别的占比数
     * @param pageable
     * @return
     */
    MyPage<UserDataDetailDTO> findUserDataByHos(Pageable pageable);

    List<UserDataDetailDTO> findDataByHos(Map<String,Object> params);

    /**
     * 查询关注公众号的所有用户 职称的占比数
     * @param pageable
     * @return
     */
    MyPage<UserDataDetailDTO> findUserDataByTitle(Pageable pageable);

    List<UserDataDetailDTO> findDataByTitle(Map<String,Object> params);

    /**
     * 查询关注公众号的所有用户 所在科室的占比数
     * @param pageable
     * @return
     */
    MyPage<UserDataDetailDTO> findUserDataByDepart(Pageable pageable);

    List<UserDataDetailDTO> findDataByDepart(Map<String,Object> params);

    /**
     * 查询关注公众号的用户 所在省份的数量 比例分析
     * @param pageable
     * @return
     */
    MyPage<UserDataDetailDTO> findUserDataByProvince(Pageable pageable);

    List<UserDataDetailDTO> findDataByProvince(Map<String,Object> params);

    List<UserDataDetailDTO> findUserDataByPro(Map<String,Object> params);


    MyPage<UserDataDetailDTO> findUserData(Pageable pageable, Integer propNum);


}
