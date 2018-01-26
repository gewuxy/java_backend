package cn.medcn.goods.dao;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.goods.dto.OrderDTO;
import cn.medcn.goods.model.Goods;
import cn.medcn.goods.model.Order;
import com.github.abel533.mapper.Mapper;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/4/23.
 */
public interface OrderDAO extends Mapper<Order> {

    List<OrderDTO> findMyOrder(Map<String, Object> params);

    List<Order> findOrderList(Map<String, Object> params);
}
