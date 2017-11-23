package cn.medcn.meet.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.DeliveryAccepterDTO;
import cn.medcn.meet.dto.DeliveryHistoryDTO;
import cn.medcn.meet.model.CourseDelivery;

import java.util.List;

/**
 * Created by lixuan on 2017/9/26.
 */
public interface CourseDeliveryService extends BaseService<CourseDelivery>{

    /**
     * 查看投稿历史
     * @param pageable
     * @return
     */
    MyPage<DeliveryHistoryDTO> findDeliveryHistory(Pageable pageable);

    /**
     * 根据接收者ID获取投稿记录
     * @param acceptId
     * @param authorId
     * @return
     */
    List<CourseDeliveryDTO> findByAcceptId(Integer acceptId, String authorId);

    /**
     * 投稿
     * @param courseId
     * @param acceptIds
     * @param authorId
     */
    void executeDelivery(Integer courseId, Integer[] acceptIds, String authorId) throws SystemException;

    /**
     * 返回所有开发投稿的单位号
     * @return
     */
    MyPage<DeliveryAccepterDTO> findAcceptors(Pageable pageable);

    /**
     * 资源平台的投稿列表
     * @param pageable
     * @return
     */
    MyPage<CourseDeliveryDTO> findDeliveryList(Pageable pageable);

    /**
     * 引用资源中的csp列表
     * @param pageable
     * @return
     */
    MyPage<CourseDeliveryDTO> findCSPList(Pageable pageable);

    /**
     * 分页获取用户投稿历史
     * @param pageable
     * @return
     */
    MyPage<CourseDeliveryDTO> pageDeliveries(Pageable pageable);

}
