package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dao.FluxOrderDAO;
import cn.medcn.user.dao.UserFluxDAO;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.service.ChargeService;
import com.github.abel533.mapper.Mapper;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuLP on 2017/9/25.
 */
@Service
public class ChargeServiceImpl extends BaseServiceImpl<FluxOrder> implements ChargeService {


    @Autowired
    protected FluxOrderDAO fluxOrderDAO;

    @Autowired
    protected UserFluxDAO userFluxDAO;

    @Override
    public Mapper<FluxOrder> getBaseMapper() {
        return fluxOrderDAO;
    }


    /**
     * 创建Charge对象，返回给前端
     *
     * @param appId
     * @param flux
     * @param channel
     * @param ip
     * @return
     * @throws RateLimitException
     * @throws APIException
     * @throws ChannelException
     * @throws InvalidRequestException
     * @throws APIConnectionException
     * @throws AuthenticationException
     */
    public Charge createCharge(String orderNo, String appId, Integer flux, String channel, String ip) throws RateLimitException, APIException, ChannelException, InvalidRequestException, APIConnectionException, AuthenticationException {
        Map<String, Object> chargeParams = new HashMap();

        chargeParams.put("order_no", orderNo);
        //单位为对应币种的最小货币单位，人民币为分。如订单总金额为 1 元， amount 为 100
        chargeParams.put("amount", flux *2 * 100);
        Map<String, String> app = new HashMap();
        //appId
        app.put("id", appId);
        chargeParams.put("app", app);
        chargeParams.put("channel", channel);
        chargeParams.put("currency", "cny");
        chargeParams.put("client_ip", ip);
        chargeParams.put("subject", "charge flux");
        chargeParams.put("body", "charge flux");

        //支付宝手机网页支付
        if ("alipay_wap".equals(channel)) {
            Map<String, String> extraMap = new HashMap();
            extraMap.put("success_url", "http://www.baidu.com");
            chargeParams.put("extra", extraMap);
        }
        //支付宝电脑网站支付
        if ("alipay_pc_direct".equals(channel)) {
            Map<String, String> extraMap = new HashMap();
            extraMap.put("success_url", "http://www.baidu.com");
            chargeParams.put("extra", extraMap);
        }
        //银联全渠道手机网页支付
        if ("upacp_wap".equals(channel)) {
            Map<String, String> extraMap = new HashMap();
            extraMap.put("result_url", "http://127.0.0.1:8080/PartTimeJob/pinus_webview.html");
            chargeParams.put("extra", extraMap);
        }
        //银联PC网页支付
        if ("upacp_pc".equals(channel)) {
            Map<String, String> extraMap = new HashMap();
            extraMap.put("result_url", "http://127.0.0.1:8080/PartTimeJob/pinus_webview.html");
            chargeParams.put("extra", extraMap);
        }
        return Charge.create(chargeParams);
    }


    /**
     * 创建订单
     *
     * @param userId
     * @param orderNo 订单id
     * @param flux  流量值
     * @param channel 付款方式
     */
    public void createOrder(String userId, String orderNo, Integer flux, String channel) {
        FluxOrder order = new FluxOrder();
        order.setId(StringUtils.nowStr());
        order.setUserId(userId);
        order.setBuyTime(new Date());
        order.setTradeId(orderNo);
        order.setState(0);
        //已M为单位存储
        order.setFlux(flux * 1024);
        order.setPlatform(channel);
        fluxOrderDAO.insert(order);
    }


    /**
     * 更新订单状态和用户流量值
     *
     * @param result
     */
    public void updateOrderAndUserFlux(FluxOrder result) {
        //更新订单状态
        result.setState(1);
        result.setEffectTime(new Date());
        fluxOrderDAO.updateByPrimaryKey(result);

        //查找充值记录
        String userId = result.getUserId();
        UserFlux flux = userFluxDAO.selectByPrimaryKey(userId);
        //没有充值记录，创建充值记录
        if (flux == null) {
            UserFlux condition = new UserFlux();
            condition.setUserId(userId);
            condition.setFlux(result.getFlux());
            userFluxDAO.insert(condition);
        } else {
            flux.setFlux(flux.getFlux() + result.getFlux());
            userFluxDAO.updateByPrimaryKey(flux);
        }

    }

    /**
     * 创建paypal订单,将订单id返回给前端
     * @param userId
     * @param flux
     */
    @Override
    public String createPaypalOrder(String userId, Integer flux) {
        String id = StringUtils.nowStr();
        FluxOrder order = new FluxOrder();
        order.setId(id);
        order.setTradeId(id);
        order.setState(0);
        order.setBuyTime(new Date());
        order.setUserId(userId);
        order.setPlatform("paypal");
        //已M为单位存储
        order.setFlux(flux * 1024);
        fluxOrderDAO.insert(order);
        return id;
    }

    /**
     * 创建web paypal订单
     * @param userId
     * @param flux
     * @param paymentId
     * @return
     */
    @Override
    public void createPaypalWebOrder(String userId, Integer flux, String paymentId) {
        String id = StringUtils.nowStr();
        FluxOrder order = new FluxOrder();
        order.setId(id);
        order.setTradeId(paymentId);
        order.setState(0);
        order.setBuyTime(new Date());
        order.setUserId(userId);
        order.setPlatform("paypal");
        //已M为单位存储
        order.setFlux(flux * 1024);
        fluxOrderDAO.insert(order);
    }

}
