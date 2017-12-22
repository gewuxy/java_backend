package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.CspPackageOrderDTO;
import cn.medcn.user.model.CspPackageOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/8.
 */
public interface CspPackageOrderService extends BaseService<CspPackageOrder> {

    Integer updateOrderAndUserPackageInfo(CspPackageOrder order);

    void createOrder(String userId, String orderNo,Integer currency, Integer packageId, Integer num, Float money, String payType);

    Boolean yearPay(Integer packageId,float money);

    Double selectNewMoney();

    /**
     * 美元和人民币的资金总额
     * @return
     */
    Map<Integer,Float> selectAbroadAndHomeMoney();

    /**
     * 根据币种查找订单
     * @param
     * @return
     */
    List<CspPackageOrderDTO> findOrderListByCurrencyType(int type);
}
