package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.sys.dao.SystemNotifyDAO;
import cn.medcn.sys.service.SysNotifyService;
import cn.medcn.user.dao.CspPackageDAO;
import cn.medcn.user.dao.CspPackageOrderDAO;
import cn.medcn.user.dao.CspUserPackageDAO;
import cn.medcn.user.dao.CspUserPackageHistoryDAO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserPackageHistoryService;
import cn.medcn.user.service.CspUserPackageService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
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

    @Override
    public void createOrder(String userId, String orderNo,String currency, Integer packageId, Integer num, Float money, String payType) {
        CspPackageOrder order = new CspPackageOrder();
        order.setNum(num);
        order.setUserId(userId);
        order.setShouldPay(money);
        order.setCurrencyType(currency.equals("CN")?Constants.NUMBER_ZERO:Constants.NUMBER_ONE);
        order.setId(StringUtils.nowStr());
        order.setTradeId(orderNo);
        order.setPlatForm(payType);
        order.setPackageId(packageId);
        order.setCreateTime(new Date());
        order.setState(Constants.NUMBER_ZERO);
        packageOrderDAO.insertSelective(order);
    }

    @Override
    public void updateOrderAndUserPackageInfo(CspPackageOrder order) {
        //更新已支付状态
        order.setState(Constants.NUMBER_ONE);
        packageOrderDAO.updateByPrimaryKey(order);
        CspUserPackage userPk = cspUserPackageDAO.selectByPrimaryKey(order.getUserId());
        Integer packageId = order.getPackageId();
        Integer oldPackage = null;
        Date currentTime = new Date();
        //开始时间
        Date start = CalendarUtils.nextDateStartTime();
        //是否是年费套餐
        boolean yearType = yearPay(packageId,order.getShouldPay());
        if(userPk == null){ //添加用户套餐信息
            Date end  = yearType ? CalendarUtils.calendarDay(start,order.getNum() * 365):CalendarUtils.calendarDay(start,order.getNum() * 30);
            cspUserPackageDAO.insertSelective(CspUserPackage.build(order.getUserId(),start,end,packageId,Constants.NUMBER_ONE));
        }else{ // 更新用户套餐信息
            oldPackage = userPk.getPackageId();
            Date end  = yearType ? CalendarUtils.calendarDay(userPk.getPackageEnd(),order.getNum() * 365):CalendarUtils.calendarDay(userPk.getPackageEnd(),order.getNum() * 30);
            cspUserPackageDAO.updateByPrimaryKey(CspUserPackage.build(order.getUserId(),start,end,packageId,Constants.NUMBER_ONE));
        }
        //添加用户套餐历史信息
        cspUserPackageHistoryService.addUserHistoryInfo(order.getUserId(),oldPackage,packageId,Constants.NUMBER_ONE);

        //推送购买成功消息
        StringBuffer content = new StringBuffer();
        content.append("您已成为会讲").append(CspPackage.TypeId.values()[packageId].getLabel()).append("会员用户，有效期").append(yearType ? order.getNum() + "年":order.getNum() + "个月");
        sysNotifyService.addNotify(order.getUserId(),"购买成功",content+"。",local("user.notify.sender"));
    }

    @Override
    public Boolean yearPay(Integer packageId,float money){
        if(packageId == CspPackage.TypeId.PROFESSIONAL.getId()){ //专业版
            CspPackage cspPackage = cspPackageDAO.selectByPrimaryKey(packageId);
            if(money >= cspPackage.getYearRmb()  ) {//年费
                return true;
            }
        }
        return false;
    }

}

