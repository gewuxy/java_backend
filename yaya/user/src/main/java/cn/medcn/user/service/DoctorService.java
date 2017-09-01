package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.DoctorDTO;
import cn.medcn.user.model.Group;
import cn.medcn.user.model.PubUserDocGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/5/16/016.
 */

public interface DoctorService extends BaseService<Group>{

    /**
     * 查询所有的医生分组
     * @param userId
     * @return
     */
    List<Group> findGroupList(Integer userId);

    MyPage<DoctorDTO> findAllDocInfo(Pageable pageable);

    MyPage<DoctorDTO> findDocInfoByGId(Pageable pageable);

    void allotGroup(Integer doctorId, Integer groupId, Integer userId);

    void allotGroups(Integer[] doctorIds, Integer groupId, Integer userId);

    Integer findAllDocCount(Integer userId);

    DoctorDTO findDoctorInfo(Map<String,Object> map );

    int findUndifindGroupNum(Integer userId);

    MyPage<DoctorDTO> findUndifindGroupDocInfo(Pageable pageable);

    /**
     * 删除公众号和医生的关系
     * @param group
     */
    void deletePubUserDoctorGroup(PubUserDocGroup group);

    /**
     *  查询绑定微信的用户数量
     * @param userId
     * @return
     */
    Integer findBindWxCount(Integer userId);

    /**
     * 查询绑定微信的用户信息
     * @param pageable
     * @return
     */
    MyPage<DoctorDTO> findBindWxDocInfo(Pageable pageable);
}
