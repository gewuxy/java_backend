package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.sys.dao.SystemNotifyDAO;
import cn.medcn.sys.service.SysNotifyService;
import cn.medcn.user.dao.CspPackageDAO;
import cn.medcn.user.dao.CspPackageOrderDAO;
import cn.medcn.user.dao.CspUserPackageDAO;
import cn.medcn.user.dao.CspUserPackageHistoryDAO;
import cn.medcn.user.dto.CspOrderPlatFromDTO;
import cn.medcn.user.dto.CspPackageOrderDTO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserPackageHistoryService;
import cn.medcn.user.service.CspUserPackageService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 套餐操作
 *
 * Created by Liuchangling on 2017/12/8.
 */
@Service
public class CspPackageOrderServiceImpl extends BaseServiceImpl<CspPackageOrder> implements CspPackageOrderService {

    @Autowired
    protected CspPackageOrderDAO packageOrderDAO;

    @Autowired
    protected CspPackageDAO cspPackageDAO;

    @Autowired
    protected  CspUserPackageDAO cspUserPackageDAO;

    @Autowired
    protected SystemNotifyDAO systemNotifyDAO;

    @Autowired
    protected CspUserPackageHistoryDAO cspUserPackageHistoryDAO;

    @Autowired
    protected CspUserPackageHistoryService cspUserPackageHistoryService;

    @Autowired
    protected SysNotifyService sysNotifyService;

    @Autowired
    protected CspUserPackageService cspUserPackageService;

    @Override
    public Mapper<CspPackageOrder> getBaseMapper() {
        return packageOrderDAO;
    }

    /**
     * 创建套餐订单
     *
     * @param userId
     * @param orderNo
     * @param currency
     * @param packageId
     * @param num
     * @param money
     * @param payType
     */
    @Override
    public void createOrder(String userId, String orderNo, Integer currency, Integer packageId, Integer num, Float money, String payType,Integer packageType) {
        CspPackageOrder order = new CspPackageOrder();
        order.setNum(num);
        order.setUserId(userId);
        order.setShouldPay(money);
        order.setCurrencyType(currency);
        order.setId(StringUtils.nowStr());
        order.setTradeId(orderNo);
        order.setPlatForm(payType);
        order.setPackageId(packageId);
        order.setCreateTime(new Date());
        order.setState(Constants.NUMBER_ZERO);
        order.setPackageType(packageType);
        packageOrderDAO.insertSelective(order);
    }

    /**
     * 更新订单状态，更新用户套餐信息，添加套餐历史信息
     *
     * @param order
     */
    @Override
    public Integer updateOrderAndUserPackageInfo(CspPackageOrder order) {
        //更新已支付状态
        order.setState(Constants.NUMBER_ONE);
        packageOrderDAO.updateByPrimaryKey(order);
        CspUserPackage userPk = cspUserPackageDAO.selectByPrimaryKey(order.getUserId());
        Integer packageId = order.getPackageId();
        Integer oldPackageId = null;
        Date end = null;
        Boolean unLimited = false;
        //开始时间
        Date start = CalendarUtils.nextDateStartTime();
        //是否是年费套餐
        boolean yearType = yearPay(packageId, order.getShouldPay());
        if (userPk == null) { //添加用户套餐信息
            end = yearType ? CalendarUtils.calendarDay(start, order.getNum() * CalendarUtils.DEFAULT_YEAR) : CalendarUtils.calendarDay(start, order.getNum() * CalendarUtils.DEFAULT_MONTH);
            cspUserPackageDAO.insertSelective(CspUserPackage.build(order.getUserId(), start, end, packageId, Constants.NUMBER_ONE,unLimited));
        } else { // 更新用户套餐信息
            oldPackageId = userPk.getPackageId();
            Date endStart = CalendarUtils.nextDateStartTime();
            //结束时间不为空从以前结束时间开始算
            if (userPk.getPackageEnd() != null) endStart = userPk.getPackageEnd();
            //判断是否是专业版无期限进行的购买行为
            if(oldPackageId == CspPackage.TypeId.PROFESSIONAL.getId() && userPk.getUnlimited() == true){
                unLimited = true;
            }
            end = yearType ? CalendarUtils.calendarDay(endStart, order.getNum() * CalendarUtils.DEFAULT_YEAR) : CalendarUtils.calendarDay(endStart, order.getNum() * CalendarUtils.DEFAULT_MONTH);
            cspUserPackageDAO.updateByPrimaryKey(CspUserPackage.build(order.getUserId(), start, end, packageId, Constants.NUMBER_ONE,unLimited));
        }
        //添加用户套餐历史信息
        cspUserPackageHistoryService.addUserHistoryInfo(order.getUserId(), oldPackageId, packageId, Constants.NUMBER_ONE);
        //推送购买成功消息
        addNotify(oldPackageId,order.getUserId(),start,end,packageId,order.getTradeId());
        return oldPackageId;
    }

    /**
     * 判断缴费是否是年费类型
     *
     * @param packageId
     * @param money
     * @return
     */
    @Override
    public Boolean yearPay(Integer packageId, float money) {
        if (packageId == CspPackage.TypeId.PROFESSIONAL.getId()) { //专业版
            CspPackage cspPackage = cspPackageDAO.selectByPrimaryKey(packageId);
            if (money >= cspPackage.getYearRmb()) {//年费
                return true;
            }
        }
        return false;
    }

    @Override
    public Double selectNewMoney() {
        return cspPackageDAO.selectNewMoney();
    }

    /**
     * 购买成功推送消息
     *
     * @param userId
     * @param start
     * @param end
     * @param packageId
     * @param orderId
     */
    public void addNotify(Integer oldPackageId, String userId, Date start, Date end, Integer packageId, String orderId) {
        String packageDesc = CspPackage.getLocalPackage(packageId);
        String content = "";
        String title = local("package.notify.pay.success");
        try {
            Integer betweenDay = CalendarUtils.daysBetween(start, end);
            if (betweenDay > CalendarUtils.DEFAULT_YEAR) {
                Integer yearNum = betweenDay / CalendarUtils.DEFAULT_YEAR;
                Integer extra = betweenDay - CalendarUtils.DEFAULT_YEAR * yearNum;
                if (oldPackageId != null) { //续费
                    title = local("package.notify.keep.success");
                    content = local("package.keep,success.notify.year", new Object[]{packageDesc, yearNum, extra, orderId});
                } else {//购买
                    content = local("package.buy,success.notify.year", new Object[]{packageDesc, yearNum, extra, orderId});
                }
            } else {
                if (oldPackageId != null) { //续费
                    title = local("package.notify.keep.success");
                    content = local("package.keep,success.notify.day", new Object[]{packageDesc, betweenDay, orderId});
                } else {     // 购买
                    content = local("package.buy,success.notify.day", new Object[]{packageDesc, betweenDay, orderId});
                }
            }
        } catch (ParseException e) {
            e.getMessage();
        }
        sysNotifyService.addNotify(userId, title, content, local("user.notify.sender"));
    }


    /**
     * 美元和人民币的资金总额
     * @return
     */
    @Override
    public List<CspPackageOrder> selectAbroadAndHomeMoney() {

        return  packageOrderDAO.selectAbroadAndHomeMoney();
    }

    /**
     * 根据币种查找订单
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CspPackageOrderDTO> findOrderListByCurrencyType(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        return MyPage.page2Mypage((Page)packageOrderDAO.findOrderListByCurrencyType(pageable.getParams()));
    }

    /**
     * 获取对应时间段的交易成功总额
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Integer findOrderSuccessSum(Integer type, String startTime, String endTime) {
        return packageOrderDAO.findOrderSuccessSum(type,startTime,endTime);
    }



    @Override
    public List<Map<String,Object>> totalMoney() {
        return packageOrderDAO.totalMoney();
    }

    @Override
    public List<Map<String, Object>> orderCapitalStati() {
        return packageOrderDAO.orderCapitalStati();
    }

    @Override
    public List<CspOrderPlatFromDTO> getCapitalByDay() {
        return packageOrderDAO.getCapitalByDay();
    }
}

