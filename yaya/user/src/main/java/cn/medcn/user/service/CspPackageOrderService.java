package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspPackageOrder;

/**
 * Created by Liuchangling on 2017/12/8.
 */
public interface CspPackageOrderService extends BaseService<CspPackageOrder> {

    void updateOrderAndUserPackageInfo(CspPackageOrder order);

    void createOrder(String userId, String orderNo,String currency, Integer packageId, Integer num, Float money, String payType);
}
