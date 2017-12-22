package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.CspOrderPlatFromDTO;
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
    List<CspPackageOrder> selectAbroadAndHomeMoney();

    /**
     * 根据币种查找订单
     * @param
     * @return
     */
    MyPage<CspPackageOrderDTO> findOrderListByCurrencyType(Pageable pageable);

    /**
     * 获取对应时间段的交易成功总额
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    Integer findOrderSuccessSum(Integer type, String startTime, String endTime);


    /**
     * 获取各币种资金总额
     *
     * @return
     */
    List<Map<String,Object>> totalMoney();

    /**
     * 获取每日资金总额
     *
     * @return
     */
    List<Map<String,Object>> orderCapitalStati();

    /**
     * 获取各渠道资金
     *
     * @return
     */
    List<CspOrderPlatFromDTO> getCapitalByDay();
}
