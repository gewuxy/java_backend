package cn.medcn.api.controller;

import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.RegexUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.goods.dto.GoodInfoDTO;
import cn.medcn.goods.dto.GoodsDTO;
import cn.medcn.goods.dto.OrderDTO;
import cn.medcn.goods.dto.TradeDetailDTO;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.model.Goods;
import cn.medcn.goods.model.Order;
import cn.medcn.goods.model.PayParam;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.goods.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by LiuLP on 2017/4/20.
 */

@Controller
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CreditsService creditsService;

    @Value("${app.file.base}")
    private String appFileBase;

    /**
     * 商品列表
     * @return
     */
    @RequestMapping("/goods")
    @ResponseBody
    public String GoodsList(Pageable pageable){
        MyPage<GoodsDTO> myPage = shopService.findGoodsList(pageable);
        for(GoodsDTO dto:myPage.getDataList()){
            dto.setPicture(appFileBase+dto.getPicture());
        }
        return APIUtils.success(myPage.getDataList());
    }

    /**
     * 商品详情
     * @param id
     * @return
     */
    @RequestMapping("/goodInfo")
    @ResponseBody
    public String getGoodInfo(Integer id){
        Goods good = shopService.selectByPrimaryKey(id);
        GoodInfoDTO goodInfoDTO = GoodInfoDTO.build(good);
        goodInfoDTO.setPicture(appFileBase+goodInfoDTO.getPicture());
        return APIUtils.success(goodInfoDTO);
    }


    /**
     * 订单记录
     * @return
     */
    @RequestMapping("/order")
    @ResponseBody
    public String myOrder(Pageable pageable){
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        pageable.getParams().put("userId",userId);
        MyPage<OrderDTO> myPage = shopService.findMyOrder(pageable);
        return APIUtils.success(myPage.getDataList());
    }

    /**
     * 商品兑换
     * @return
     */
    @RequestMapping(value = "/buy",method = RequestMethod.POST)
    @ResponseBody
    public String toBuy(PayParam payParam)  {

        if(StringUtils.isEmpty(payParam.getPrice())){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.price.notnull"));
        }
        if(StringUtils.isEmpty(payParam.getAddress())){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.address.notnull"));
        }
        if(StringUtils.isEmpty(payParam.getGoodsId())){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.goodsId.notnull"));
        }
        if(StringUtils.isEmpty(payParam.getPhone())){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.phone.notnull"));
        }
        if(!RegexUtils.checkMobile(payParam.getPhone())){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.phone.invalid"));
        }
        if(StringUtils.isEmpty(payParam.getReceiver())){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.receiver.notnull"));
        }
        if(StringUtils.isEmpty(payParam.getProvince())){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.province.notnull"));
        }

        //检查商品库存
        Goods goods = shopService.selectByPrimaryKey(payParam.getGoodsId());
        if(goods.getStock() < 1){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.underStock"));
        }
        //检查用户象数是否足够购买商品
        Credits cre = new Credits();
        cre.setUserId(SecurityUtils.getCurrentUserInfo().getId());
        Credits credits = creditsService.selectOne(cre);
        if(credits.getCredit() < goods.getPrice()){
            return APIUtils.error(SpringUtils.getMessage("shop.buy.underCredits"));
        }


        //扣除象数并生成订单和象数交易明细
        Order myOrder = generateOrder(payParam);
        try {
            shopService.doBuy(myOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return APIUtils.success();
    }




    /**
     * 象数交易明细查询
     * @return
     */
    @RequestMapping("/tradeInfo")
    @ResponseBody
    public String tradeInfo(Pageable pageable){
        pageable.getParams().put("userId",SecurityUtils.getCurrentUserInfo().getId());
        MyPage<TradeDetailDTO> myPage = creditsService.findTradeInfo(pageable);
       return APIUtils.success(myPage.getDataList());
    }



    private Order generateOrder(PayParam payParam) {
        Order order = new Order();
        order.setCost(payParam.getPrice());
        order.setCreateTime(new Date());
        order.setGoodsId(payParam.getGoodsId());
        order.setOrderNo(UUIDUtil.getNowStringID()); //订单号
        order.setPhone(payParam.getPhone());
        order.setPostNo(UUIDUtil.getNowStringID()); //物流单号
        order.setPostType("");  //配送方式
        order.setPostUnit("");  //物流单位
        order.setProvince(payParam.getProvince());
        order.setAddress(payParam.getAddress());
        order.setReceiver(payParam.getReceiver());
        order.setUserId(SecurityUtils.getCurrentUserInfo().getId());
        return order;
    }
}
