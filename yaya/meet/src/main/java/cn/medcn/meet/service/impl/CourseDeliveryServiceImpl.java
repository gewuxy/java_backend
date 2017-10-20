package cn.medcn.meet.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.meet.dao.CourseDeliveryDAO;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.DeliveryAccepterDTO;
import cn.medcn.meet.dto.DeliveryHistoryDTO;
import cn.medcn.meet.model.CourseDelivery;
import cn.medcn.meet.service.CourseDeliveryService;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/9/26.
 */
@Service
public class CourseDeliveryServiceImpl extends BaseServiceImpl<CourseDelivery> implements CourseDeliveryService {

    @Autowired
    protected CourseDeliveryDAO courseDeliveryDAO;

    @Autowired
    protected AppUserService appUserService;

    @Override
    public Mapper<CourseDelivery> getBaseMapper() {
        return courseDeliveryDAO;
    }

    /**
     * 查看投稿历史
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<DeliveryHistoryDTO> findDeliveryHistory(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        return MyPage.page2Mypage((Page) courseDeliveryDAO.findDeliveryHistory(pageable.getParams()));
    }

    /**
     * 根据接收者ID获取投稿记录
     *
     * @param acceptId
     * @param authorId
     * @return
     */
    @Override
    public List<CourseDeliveryDTO> findByAcceptId(Integer acceptId, String authorId) {
        return courseDeliveryDAO.findByAcceptId(acceptId, authorId);
    }

    /**
     * 投稿
     *
     * @param courseId
     * @param acceptIds
     */
    @Override
    public void executeDelivery(Integer courseId, Integer[] acceptIds, String authorId)  {
        for (Integer acceptId : acceptIds) {
            CourseDelivery delivery = new CourseDelivery();
            delivery.setAcceptId(acceptId);
            delivery.setAuthorId(authorId);
            delivery.setDeliveryTime(new Date());
            delivery.setId(UUIDUtil.getNowStringID());
            delivery.setSourceId(courseId);
            delivery.setViewState(false);
            delivery.setPublishState(false);
            courseDeliveryDAO.insert(delivery);
        }
    }


    /**
     * 返回所有开发投稿的单位号
     *
     * @return
     */
    @Override
    public MyPage<DeliveryAccepterDTO> findAcceptors(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize());
        return MyPage.page2Mypage((Page) courseDeliveryDAO.findAcceptors());
    }

    /**
     * 资源平台的投稿列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseDeliveryDTO> findDeliveryList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),true);
        return MyPage.page2Mypage((Page) courseDeliveryDAO.findDeliveryList(pageable.getParams()));
    }


    /**
     * 引用资源中的csp列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseDeliveryDTO> findCSPList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),true);
        return MyPage.page2Mypage((Page) courseDeliveryDAO.findCSPList(pageable.getParams()));
    }

    /**
     * 投稿
     * @param courseId
     * @param accepts
     * @param authorId
     */
    @Override
    public void contribute(Integer courseId, Integer[] accepts, String authorId) throws SystemException {
        for(Integer acceptId:accepts){
            CourseDelivery delivery = new CourseDelivery();
            delivery.setAcceptId(acceptId);
            delivery.setSourceId(courseId);
            delivery.setAuthorId(authorId);
            CourseDelivery result = selectOne(delivery);
            if(result != null){
                AppUser user = appUserService.selectByPrimaryKey(acceptId);
                throw new SystemException("您已投稿过此会议到 " + user.getNickname());
            }
            delivery.setId(cn.medcn.common.utils.StringUtils.nowStr());
            delivery.setDeliveryTime(new Date());
            insert(delivery);
        }
    }
}
