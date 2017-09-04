package cn.medcn.user.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.AppDoctorDAO;
import cn.medcn.user.dao.AppUserDAO;
import cn.medcn.user.dao.GroupDAO;
import cn.medcn.user.dao.PubUserDocGroupDAO;
import cn.medcn.user.dto.DoctorDTO;
import cn.medcn.user.model.PubUserDocGroup;
import cn.medcn.user.model.Group;
import cn.medcn.user.service.DoctorService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/5/16/016.
 */
@Service
public class DoctorServiceImpl extends BaseServiceImpl<Group> implements DoctorService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppDoctorDAO appDoctorDAO;

    @Autowired
    private PubUserDocGroupDAO pubUserDocGroupDAO;

    @Override
    public Mapper<Group> getBaseMapper() {
        return groupDAO;
    }


    /**
     * 查询所有医生分组
     * @param userId
     * @return
     */
    @Override
    public List<Group> findGroupList(Integer userId) {
        Group group = new Group();
        group.setPubUserId(userId);
        List<Group> groupList = groupDAO.findGroupList(group);
        return groupList;
    }

    /**
     * 查询所有医生信息
     * @param pageable
     * @return
     */
    @Override
    public MyPage<DoctorDTO> findAllDoctorInfo(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),true);
        Page<DoctorDTO> page = (Page<DoctorDTO>)appDoctorDAO.findAllDoctorInfo(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 查询分组医生信息
     * @param pageable
     * @return
     */
    @Override
    public MyPage<DoctorDTO> findDoctorInfoByGId(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<DoctorDTO> page = (Page<DoctorDTO>)appDoctorDAO.findDoctorInfoByGId(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 单个用户移动分组
     * @param doctorId
     * @param groupId
     * @param userId
     */
    @Override
    public void allotGroup(Integer doctorId, Integer groupId, Integer userId) {
        //更改分组id
        PubUserDocGroup pubUserDocGroup = new PubUserDocGroup();
        pubUserDocGroup.setDoctorId(doctorId);
        pubUserDocGroup.setPubUserId(userId);
        PubUserDocGroup oldGroup =  pubUserDocGroupDAO.selectOne(pubUserDocGroup);
        if(oldGroup != null){
            oldGroup.setGroupId(groupId);
            pubUserDocGroupDAO.updateByPrimaryKey(oldGroup);
        }else{
            //该医生还没有分组,插入分组
            pubUserDocGroup.setGroupId(groupId);
            pubUserDocGroupDAO.insertSelective(pubUserDocGroup);

        }
    }

    /**
     * 多个用户移动分组
     * @param doctorIds
     * @param groupId
     * @param userId
     */
    @Override
    public void allotGroups(Integer[] doctorIds, Integer groupId, Integer userId) {
        for(Integer doctorId:doctorIds){
            allotGroup(doctorId,groupId,userId);
        }
    }

    /**
     * 所有医生的数量
     * @param userId
     * @return
     */
    @Override
    public Integer findAllDoctorCount(Integer userId) {
        return appUserDAO.findAllDoctorCount(userId);
    }

    /**
     * 医生详细个人信息
     * @param map
     * @return
     */
    @Override
    public DoctorDTO findDoctorInfo(Map<String,Object> map ) {

        return appDoctorDAO.findDoctorInfo(map);
    }

    @Override
    public int findUndifindGroupNum(Integer userId) {
        return appUserDAO.findUndifindGroupNum(userId);
    }

    @Override
    public MyPage<DoctorDTO> findUndifindGroupDoctorInfo(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),true);
        Page<DoctorDTO> page = (Page<DoctorDTO>)appDoctorDAO.findUndifindGroupDoctorInfo(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 删除公众号和医生的关系
     * @param group
     */
    @Override
    public void deletePubUserDoctorGroup(PubUserDocGroup group) {
        pubUserDocGroupDAO.delete(group);
    }

    /**
     * 查询绑定微信的用户数量
     * @param userId
     * @return
     */
    @Override
    public Integer findBindWxCount(Integer userId) {
        return appUserDAO.findBindWxCount(userId);
    }

    /**
     * 查询绑定微信的用户信息
     * @param pageable
     * @return
     */
    @Override
    public MyPage<DoctorDTO> findBindWxDoctorInfo(Pageable pageable){
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),true);
        Page<DoctorDTO> page = (Page<DoctorDTO>)appDoctorDAO.findBindWxDoctorInfo(pageable.getParams());
        return MyPage.page2Mypage(page);
    }
}
