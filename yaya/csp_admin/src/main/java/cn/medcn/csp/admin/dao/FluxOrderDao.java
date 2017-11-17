package cn.medcn.csp.admin.dao;

import cn.medcn.common.pagination.Pageable;
import cn.medcn.csp.admin.dto.FluxOrderDTO;
import cn.medcn.user.model.FluxOrder;
import com.github.abel533.mapper.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author：jianliang
 * @Date: Creat in 9:45 2017/11/15
 */
public interface FluxOrderDao extends Mapper<FluxOrderDTO>{
    List<FluxOrderDTO> findFluxOrderList(Map<String, Object> params);

    List<FluxOrderDTO> selectOrderInfo(String id);
}
