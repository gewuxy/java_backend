package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.RechargeOrder;

/**
 * Created by lixuan on 2017/8/8.
 */
public interface RechargeOrderService extends BaseService<RechargeOrder>{

    /**
     * 生成订单
     * @param userId
     * @param outTradeNo
     * @param cost
     * @return
     */
    RechargeOrder generateOrder(Integer userId, String outTradeNo, float cost);

    /**
     * 创建订单
     * @param userId
     * @param outTradeNo
     * @param cost
     * @return
     */
    RechargeOrder createRechargeOrder(Integer userId, String outTradeNo, float cost);

    /**
     * 修改订单状态为已支付
     * @param outTradeNo
     * @param thirdPartyTradeNo 第三方交易凭证号
     */
    void modifyOrderPayed(String outTradeNo, String thirdPartyTradeNo) throws Exception;

    /**
     * 根据订单号查找订单
     * @param outTradeNo
     * @return
     */
    RechargeOrder getRechargeOrder(String outTradeNo);
}
