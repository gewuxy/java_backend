package cn.medcn.meet.dao;

import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.DeliveryAccepterDTO;
import cn.medcn.meet.dto.DeliveryHistoryDTO;
import cn.medcn.meet.model.CourseDelivery;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/9/26.
 */
public interface CourseDeliveryDAO extends Mapper<CourseDelivery> {

    List<DeliveryHistoryDTO> findDeliveryHistory(Map<String, Object> params);

    List<CourseDeliveryDTO> findByAcceptId(Integer acceptId, String authorId);

    List<DeliveryAccepterDTO> findAcceptors();

    List<CourseDeliveryDTO> findDeliveryList(Map<String, Object> params);
}
