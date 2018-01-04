package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.FluxOrder;
import com.paypal.api.payments.Payment;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;

import java.util.List;

/**
 * Created by LiuLP on 2017/9/26.
 */
public interface ChargeService extends BaseService<FluxOrder> {

    Charge createCharge(String orderNo, Float money, String channel, String ip,String subject,String appId) throws RateLimitException, APIException, ChannelException, InvalidRequestException, APIConnectionException, AuthenticationException;

    void createOrder(String id, String orderNo, Integer amount, String channel);

    void updateOrderAndUserFlux(FluxOrder result);

    /**
     * 创建paypal订单,将订单id返回给前端
     * @param userId
     * @param flux
     */
    String createPaypalOrder(String userId, Integer flux);

    /**
     * 创建web paypal订单
     * @param userId
     * @param flux
     * @param paymentId
     * @return
     */
    void createPaypalWebOrder(String userId, Integer flux, String paymentId);

    /**
     * 生成paypal支付对象
     * @param money
     * @return
     */
    Payment generatePayment(Float money);

    /**
     * 流量订单列表
     * @param pageable
     * @return
     */
    MyPage<FluxOrder> findFluxOrderList(Pageable pageable);

    float selectAllMoney();

    float selectAllMoneyByPapal();

    MyPage<FluxOrder> findFluxOrderListByUs(Pageable pageable);

    Float findOrderListByTime(String startTime, String endTime);

    Float findOrderListByTimeUs(String startTime, String endTime);
}
