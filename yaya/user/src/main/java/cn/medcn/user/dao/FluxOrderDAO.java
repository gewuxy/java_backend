package cn.medcn.user.dao;

import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.model.UserFlux;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

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

    float selectAllMoney();

    float selectAllMoneyByPapal();

    List<FluxOrder> findFluxOrderListByUs(Map<String, Object> params);

    Float findOrderListByTime(@Param("startTime") String startTime, @Param("endTime")String endTime);

    Float findOrderListByTimeUs(@Param("startTime") String startTime,@Param("endTime") String endTime);
}
