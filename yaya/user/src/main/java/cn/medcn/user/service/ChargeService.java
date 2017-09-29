package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.FluxOrder;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;

/**
 * Created by LiuLP on 2017/9/26.
 */
public interface ChargeService extends BaseService<FluxOrder> {

    Charge createCharge(String orderNo, String appId, Integer flux, String channel, String ip) throws RateLimitException, APIException, ChannelException, InvalidRequestException, APIConnectionException, AuthenticationException;

    void createOrder(String id, String orderNo, Integer amount, String channel);

    void updateOrderAndUserFlux(FluxOrder result);

    /**
     * 创建paypal订单
     * @param userId
     * @param flux
     */
    void createPaypalOrder(String userId, Integer flux);
}
