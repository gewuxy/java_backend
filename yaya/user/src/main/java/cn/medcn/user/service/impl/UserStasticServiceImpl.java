package cn.medcn.user.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.AppAttentionDAO;
import cn.medcn.user.dto.UserAttendDTO;
import cn.medcn.user.dto.UserDataDetailDTO;
import cn.medcn.user.model.AppAttention;
import cn.medcn.user.service.UserStasticService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by Liuchangling on 2017/5/18.
 */
@Service
public class UserStasticServiceImpl extends BaseServiceImpl<AppAttention> implements UserStasticService{
    @Autowired
    private AppAttentionDAO appAttentionDAO;

    @Override
    public Mapper<AppAttention> getBaseMapper() {
        return appAttentionDAO;
    }

    /**
     * 查询本周、本月、所有 关注公众号的粉丝数
     * @param userId
     * @return
     */
    @Override
    public UserAttendDTO findAttendCount(Integer userId) {
        UserAttendDTO attendDTO = appAttentionDAO.findAttendPubUserCount(userId);
        return attendDTO;
    }

    /**
     * 查询关注公众号的 粉丝用户总数
     * @param userId
     * @return
     */
    @Override
    public Integer findTotalAttendCount(Integer userId) {
        return appAttentionDAO.findTotalAttendCount(userId);
    }

    /**
     * 查询关注公众号的所有用户 所在城市的占比情况
     * @param pageable
     * @return
     */
    @Override
    public MyPage<UserDataDetailDTO> findUserDataByCity(Pageable pageable){
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<UserDataDetailDTO> page = (Page<UserDataDetailDTO>) appAttentionDAO.findUserDataByCity(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public List<UserDataDetailDTO> findDataByCity(Map<String, Object> params) {
        List<UserDataDetailDTO> cityList = appAttentionDAO.findUserDataByCity(params);
        return cityList;
    }

    /**
     * 查询关注公众号的所有用户 所在医院级别的用户数
     * @param pageable
     * @return
     */
    @Override
    public MyPage<UserDataDetailDTO> findUserDataByHos(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<UserDataDetailDTO> page = (Page<UserDataDetailDTO>) appAttentionDAO.findUserDataByHos(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public List<UserDataDetailDTO> findDataByHos(Map<String, Object> params) {
        List<UserDataDetailDTO> hosList = appAttentionDAO.findUserDataByHos(params);
        return hosList;
    }

    /**
     * 查询关注公众号的所有用户 职称的占比数
     * @param pageable
     * @return
     */
    @Override
    public MyPage<UserDataDetailDTO> findUserDataByTitle(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<UserDataDetailDTO> page = (Page<UserDataDetailDTO>) appAttentionDAO.findUserDataByTitle(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public List<UserDataDetailDTO> findDataByTitle(Map<String, Object> params) {
        List<UserDataDetailDTO> titList = appAttentionDAO.findUserDataByTitle(params);
        return titList;
    }

    /**
     * 查询关注公众号的所有用户 所在科室的占比数
     * @param pageable
     * @return
     */
    @Override
    public MyPage<UserDataDetailDTO> findUserDataByDepart(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<UserDataDetailDTO> page = (Page<UserDataDetailDTO>) appAttentionDAO.findUserDataByDept(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public List<UserDataDetailDTO> findDataByDepart(Map<String, Object> params) {
        List<UserDataDetailDTO> depList = appAttentionDAO.findUserDataByDept(params);
        return depList;
    }

    /**
     * 查询关注公众号的用户 所在的省份 数量占比分析
     * @param params
     * @return
     */
    @Override
    public List<UserDataDetailDTO> findDataByProvince(Map<String,Object> params) {
        List<UserDataDetailDTO> list = appAttentionDAO.findUserDataByProvince(params);
        return list;
    }

    public MyPage<UserDataDetailDTO> findUserDataByProvince(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<UserDataDetailDTO> page  = (Page<UserDataDetailDTO>)appAttentionDAO.findUserDataByProvince(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public List<UserDataDetailDTO> findUserDataByPro(Map<String, Object> params) {
        List<UserDataDetailDTO> list = appAttentionDAO.findUserDataByProvince(params);
        return list;
    }


    @Override
    public MyPage<UserDataDetailDTO> findUserData(Pageable pageable, Integer propNum) {
        if (propNum == null) {
            propNum = UserDataDetailDTO.conditionNumber.PRO_OR_CITY;
        }
        String province = (String) pageable.get("province");
        MyPage page = null;
        if (propNum == UserDataDetailDTO.conditionNumber.PRO_OR_CITY){
            if (province == null || province.equals(UserDataDetailDTO.conditionNumber.DEFAULT_CITY)){
                page = findUserDataByProvince(pageable);
            } else {
                page = findUserDataByCity(pageable);
            }
        } else if(propNum == UserDataDetailDTO.conditionNumber.HOSPITAL){
            page = findUserDataByHos(pageable);
        } else if (propNum == UserDataDetailDTO.conditionNumber.USER_TITLE) {
            page = findUserDataByTitle(pageable);
        } else if (propNum == UserDataDetailDTO.conditionNumber.DEPARTMENT){
            page = findUserDataByDepart(pageable);
        } else {
            // 关注量分析 查询 关注公众号的所有省的用户数
            page = findUserDataByProvince(pageable);
        }
        return page;
    }
}
