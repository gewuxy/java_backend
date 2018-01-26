package cn.medcn.goods.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.goods.dao.OrderDAO;
import cn.medcn.goods.model.Order;
import cn.medcn.goods.service.OrderService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2018/1/26.
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
    @Autowired
    protected OrderDAO orderDAO;

    @Override
    public Mapper<Order> getBaseMapper() {
        return orderDAO;
    }

    @Override
    public MyPage<Order> findOrderList(Pageable pageable) {
        startPage(pageable, Pageable.countPage);
        Page<Order> page = (Page<Order>) orderDAO.findOrderList(pageable.getParams());
        return MyPage.page2Mypage(page);
    }
}
