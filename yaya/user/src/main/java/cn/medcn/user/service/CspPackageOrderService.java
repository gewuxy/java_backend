package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.CspOrderPlatFromDTO;
import cn.medcn.user.dto.CspPackageOrderDTO;
import cn.medcn.user.model.CspPackageOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/8.
 */
public interface CspPackageOrderService extends BaseService<CspPackageOrder> {

    Integer updateOrderAndUserPackageInfo(CspPackageOrder order);

    void createOrder(String orderId,String userId, String orderNo,Integer currency, Integer packageId, Integer num, Float money, String payType,Integer packageType);

    Boolean yearPay(Integer packageId,float money);

    Double selectNewMoney(Integer currencyType);

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
    List<Map<String,Object>> orderCapitalStati(Integer grain,Integer abroad,Date startTime,Date endTime);

    /**
     * 获取各渠道资金
     *
     * @return
     */
    MyPage<Map<String,Object>> getCapitalByDay(Pageable pageable);

    CspOrderPlatFromDTO getTotalCapital(Pageable pageable);
    String getLocalPackage(Integer packageId);

    CspOrderPlatFromDTO getTotalCapital(Integer grain, Integer abroad, Date date, Date date1);

    /**
     * 获取转化率
     * @param date
     * @param date1
     * @return
     */
    List<Map<String,Object>> transfStats( Date date, Date date1);

    /**
     * 获取续费率
     * @param date
     * @param date1
     * @return
     */
    List<Map<String,Object>> renewStats(Date date, Date date1);
}
