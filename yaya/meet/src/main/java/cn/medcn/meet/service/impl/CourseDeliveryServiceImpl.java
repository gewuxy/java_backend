package cn.medcn.meet.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.meet.dao.CourseDeliveryDAO;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.DeliveryAccepterDTO;
import cn.medcn.meet.dto.DeliveryHistoryDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCoursePlay;
import cn.medcn.meet.model.CourseDelivery;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.CourseDeliveryService;
import cn.medcn.meet.service.LiveService;
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

    @Autowired
    protected AudioService audioService;

    @Autowired
    protected LiveService liveService;

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
    public void executeDelivery(Integer courseId, Integer[] acceptIds, String authorId) throws SystemException {
        AudioCourse course = audioService.selectByPrimaryKey(courseId);
        if (course == null) {
            throw new SystemException(local("source.not.exists"));
        }

        if (course.getPlayType() == null) {
            course.setPlayType(0);
        }
        //将用户ID置空 意图是让录播投稿生成的副本不占该用户会议个数
        course.setCspUserId(null);

        for(Integer acceptId:acceptIds){
            if (course.getPlayType().intValue() == AudioCourse.PlayType.normal.getType()) {//录播投稿需要复制副本
                //复制副本
                courseId = audioService.doCopyCourse(course, null, null);
            }else{  //直播，检查会议是否已结束，如果会议已结束，将直播复制成录播会议
                Live live = liveService.findByCourseId(courseId);
                if(live != null && live.getEndTime() != null && new Date().getTime() > live.getEndTime().getTime()
                        || live.getLiveState().intValue() == AudioCoursePlay.PlayState.over.ordinal()){  //会议已结束
                    courseId = audioService.doCopyLiveToRecord(courseId);
                }
            }

            //生成投稿记录
            CourseDelivery delivery = new CourseDelivery();
            delivery.setAcceptId(acceptId);
            delivery.setSourceId(courseId);
            delivery.setAuthorId(authorId);
            if (course.getPlayType().intValue() > AudioCourse.PlayType.normal.getType()) {
                CourseDelivery result = selectOne(delivery);
                if(result != null){
                    throw new SystemException(local("cannot.repeat.delivery"));
                }
            }

            delivery.setId(cn.medcn.common.utils.StringUtils.nowStr());
            delivery.setDeliveryTime(new Date());
            insert(delivery);
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
     * 分页获取用户投稿历史
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseDeliveryDTO> pageDeliveries(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),true);
        return MyPage.page2Mypage((Page) courseDeliveryDAO.findByAcceptId((Integer)pageable.get("acceptId"), (String)pageable.get("authorId")));
    }

    /**
     * 判断课件是否已经投稿
     *
     * @param courseId
     * @return
     */
    @Override
    public boolean hasContributed(Integer courseId) {
        CourseDelivery cond = new CourseDelivery();
        cond.setSourceId(courseId);
        List<CourseDelivery> list = courseDeliveryDAO.select(cond);
        return CheckUtils.isEmpty(list) ? false : true;
    }
}
