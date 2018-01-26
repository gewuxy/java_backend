package cn.medcn.goods.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.goods.dao.GoodsDAO;
import cn.medcn.goods.dao.OrderDAO;
import cn.medcn.goods.dao.TradeDetailDAO;
import cn.medcn.goods.dto.CreditPayDTO;
import cn.medcn.goods.dto.GoodsDTO;
import cn.medcn.goods.dto.OrderDTO;
import cn.medcn.goods.model.Goods;
import cn.medcn.goods.model.Order;
import cn.medcn.goods.model.TradeDetail;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.goods.service.ShopService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LiuLP on 2017/4/23.
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<Goods> implements ShopService{

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private TradeDetailDAO tradeDetailDAO;


    @Override
    public Mapper<Goods> getBaseMapper() {
        return goodsDAO;
    }


    /**
     * 我的订单记录
     * @param pageable
     * @return
     */
    @Override
    public MyPage<OrderDTO> findMyOrder( Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<OrderDTO> page = (Page<OrderDTO>)orderDAO.findMyOrder(pageable.getParams());
        return MyPage.page2Mypage(page);
    }


    /**
     * 扣除象数并生成订单和象数交易明细,扣除商品库存
     *
     * @param order
     * @throws Exception
     */
    @Override
    public void doBuy( Order order) throws Exception {
        Goods goods = goodsDAO.selectByPrimaryKey(order.getGoodsId());
        //扣除象数并生成象数交易明细
        CreditPayDTO creditPayDTO = new CreditPayDTO();
        creditPayDTO.setCredits(order.getCost());
        creditPayDTO.setPayer(order.getUserId());
        creditPayDTO.setPayerDescrib("您购买了商品:"+goods.getName());
        creditsService.executePlayCredits(creditPayDTO);
        //生成订单
        orderDAO.insertSelective(order);
        //扣除商品库存
        goods.setStock(goods.getStock()-1);
        goodsDAO.updateByPrimaryKey(goods);



    }



    @Override
    public MyPage<GoodsDTO> findGoodsList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<GoodsDTO> page = (Page<GoodsDTO>)goodsDAO.findAll();
        return MyPage.page2Mypage(page);
    }


    @Override
    public MyPage<Goods> findShopGoodsList(Pageable pageable) {
        startPage(pageable, Pageable.countPage);
        Page<Goods> page = (Page<Goods>) goodsDAO.findGoodsList(pageable.getParams());
        return MyPage.page2Mypage(page);
    }


}
