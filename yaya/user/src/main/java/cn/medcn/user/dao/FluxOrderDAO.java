package cn.medcn.user.dao;

import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.model.UserFlux;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/4/20.
 */
public interface FluxOrderDAO extends Mapper<FluxOrder>{
    /**
     * 流量订单列表
     * @param params
     * @return
     */
    List<FluxOrder> findFluxOrderList(Map<String, Object> params);

    /**
     * 查看个人订单
     * @param id
     * @return
     */
    List<FluxOrder> selectOrderInfo(String id);
}
