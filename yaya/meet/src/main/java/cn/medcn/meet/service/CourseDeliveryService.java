package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.CourseDeliveryDTO;
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
    void executeDelivery(Integer courseId, Integer[] acceptIds, String authorId);
}
