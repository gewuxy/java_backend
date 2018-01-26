package cn.medcn.goods.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.goods.model.Order;

/**
 * Created by Liuchangling on 2018/1/26.
 */
public interface OrderService extends BaseService<Order> {
    MyPage<Order> findOrderList(Pageable pageable);
}
