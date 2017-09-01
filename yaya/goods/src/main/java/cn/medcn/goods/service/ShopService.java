package cn.medcn.goods.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.goods.dto.CreditPayDTO;
import cn.medcn.goods.dto.GoodsDTO;
import cn.medcn.goods.dto.OrderDTO;
import cn.medcn.goods.model.Goods;
import cn.medcn.goods.model.Order;
import cn.medcn.goods.model.TradeDetail;

import java.util.List;

/**
 * Created by LiuLP on 2017/4/23.
 */
public interface ShopService extends BaseService<Goods> {


    MyPage<OrderDTO> findMyOrder(Pageable pageable);



    void doBuy( Order order) throws Exception;




    MyPage<GoodsDTO> findGoodsList(Pageable pageable);
}
