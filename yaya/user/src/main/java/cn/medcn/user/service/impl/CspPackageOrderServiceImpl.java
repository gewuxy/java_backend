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
import cn.medcn.user.dao.CspUserPackageDetailDAO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspUserPackageDetailService;
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
    protected CspUserPackageDetailDAO cspUserPackageDetailDAO;

    @Autowired
    protected CspUserPackageDetailService cspUserPackageDetailService;

    @Autowired
    protected SysNotifyService sysNotifyService;

    @Override
    public Mapper<CspPackageOrder> getBaseMapper() {
        return packageOrderDAO;
    }

    @Override
    public void createOrder(String userId, String orderNo,String currency, Integer packageId, Integer num, Float money, String payType) {
        CspPackageOrder order = new CspPackageOrder();
        order.setNum(num);
        order.setUserId(userId);
        order.setPayMoney(money);
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
        Date start = new Date();
        boolean isYearType = yearPay(packageId,order.getShouldPay());
        if(userPk == null){ //添加用户套餐信息
            CspUserPackage cspUserPackage = new CspUserPackage();
            cspUserPackage.setUserId(order.getUserId());
            cspUserPackage.setPackageStart(start);
            Date end  = isYearType ? CalendarUtils.calendarYear(order.getNum()):CalendarUtils.calendarMonth(order.getNum());
            cspUserPackage.setPackageEnd(end);
            cspUserPackage.setPackageId(packageId);
            cspUserPackage.setUpdateTime(start);
            cspUserPackage.setSourceType(Constants.NUMBER_ONE);
            cspUserPackageDAO.insert(cspUserPackage);
        }else{ // 更新用户套餐信息
            oldPackage = userPk.getPackageId();
            userPk.setUpdateTime(start);
            userPk.setPackageId(packageId);
            userPk.setPackageStart(start);
            Date end  = isYearType ? CalendarUtils.calendarYear(userPk.getPackageEnd(),order.getNum()):CalendarUtils.calendarMonth(userPk.getPackageEnd(),order.getNum());
            userPk.setPackageEnd(end);
            userPk.setSourceType(Constants.NUMBER_ONE);
            cspUserPackageDAO.updateByPrimaryKey(userPk);
        }
        //添加用户套餐历史信息
        cspUserPackageDetailService.addUserHistoryInfo(order.getUserId(),oldPackage,packageId,Constants.NUMBER_ONE);

        //推送购买成功消息
        StringBuffer content = new StringBuffer();
        content.append("您已成为会讲").append(CspPackage.TypeId.values()[packageId].getLabel()).append("会员用户，有效期").append(isYearType ? order.getNum() + "年":order.getNum() + "个月");
        sysNotifyService.addNotify(order.getUserId(),"购买成功",content+"。","敬信科技团队");
    }

    @Override
    public Boolean yearPay(Integer packageId,float money){
        boolean isYearType = false;
        if(packageId == CspPackage.TypeId.PROFESSIONAL.getId()){ //专业版
            CspPackage cspPackage = cspPackageDAO.selectByPrimaryKey(packageId);
            if(money >= cspPackage.getYearRmb()  ) {//年费
                isYearType = true;
            }
        }
        return isYearType;
    }
}

