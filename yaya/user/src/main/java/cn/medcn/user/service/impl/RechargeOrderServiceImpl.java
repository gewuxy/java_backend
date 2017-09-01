package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.goods.dto.RechargeDTO;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.user.dao.RechargeOrderDAO;
import cn.medcn.user.model.RechargeOrder;
import cn.medcn.user.service.RechargeOrderService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lixuan on 2017/8/8.
 */
@Service
public class RechargeOrderServiceImpl extends BaseServiceImpl<RechargeOrder> implements RechargeOrderService {

    @Autowired
    protected RechargeOrderDAO rechargeOrderDAO;

    @Autowired
    protected CreditsService creditsService;

    @Override
    public Mapper<RechargeOrder> getBaseMapper() {
        return rechargeOrderDAO;
    }


    /**
     * 生成订单
     * @param userId
     * @param outTradeNo
     * @param cost
     * @return
     */
    @Override
    public RechargeOrder generateOrder(Integer userId, String outTradeNo, float cost) {
        RechargeOrder rechargeOrder = new RechargeOrder();
        rechargeOrder.setUserId(userId);
        rechargeOrder.setCreateTime(new Date());
        rechargeOrder.setOrderNo(outTradeNo);
        rechargeOrder.setStatus(0);
        rechargeOrder.setPrice(cost);
        return rechargeOrder;
    }

    /**
     * 创建订单
     *
     * @param userId
     * @param outTradeNo
     * @param cost
     * @return
     */
    @Override
    public RechargeOrder createRechargeOrder(Integer userId, String outTradeNo, float cost) {
        RechargeOrder rechargeOrder = generateOrder(userId, outTradeNo, cost);
        rechargeOrderDAO.insert(rechargeOrder);
        return rechargeOrder;
    }

    @Override
    public void modifyOrderPayed(String outTradeNo, String thirdPartyTradeNo) throws Exception {
        RechargeOrder rechargeOrder = getRechargeOrder(outTradeNo);
        if (rechargeOrder != null && Constants.NUMBER_ONE != rechargeOrder.getStatus()){
            rechargeOrder.modifyPayed();
            RechargeDTO rechargeDTO = new RechargeDTO();
            rechargeDTO.setUserId(rechargeOrder.getUserId());
            rechargeDTO.setCredits(Math.round(rechargeOrder.getPrice()) * 10);
            creditsService.executeRecharge(rechargeDTO);

            rechargeOrder.setUpdateTime(new Date());
            rechargeOrder.setTradeNo(thirdPartyTradeNo);
            rechargeOrderDAO.updateByPrimaryKey(rechargeOrder);
        }
    }

    /**
     * 根据订单号查找订单
     *
     * @param outTradeNo
     * @return
     */
    @Override
    public RechargeOrder getRechargeOrder(String outTradeNo) {
        RechargeOrder condition = new RechargeOrder();
        condition.setOrderNo(outTradeNo);
        RechargeOrder rechargeOrder = rechargeOrderDAO.selectOne(condition);
        return rechargeOrder;
    }
}
