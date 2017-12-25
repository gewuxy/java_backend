package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dao.CspPackageDAO;
import cn.medcn.user.dao.CspUserPackageDAO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspPackageService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketPermission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Liuchangling on 2017/12/8.
 */
@Service
public class CspPackageServiceImpl extends BaseServiceImpl<CspPackage> implements CspPackageService {
    @Autowired
    protected CspPackageDAO cspPackageDAO;

    @Autowired
    protected CspUserPackageDAO cspUserPackageDAO;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Override
    public Mapper<CspPackage> getBaseMapper() {
        return cspPackageDAO;
    }

    // 获取用户当前套餐版本
    public CspPackage findUserPackageById(String userId) {
        return cspPackageDAO.findUserPackageById(userId);
    }

    /**
     * 获取当前参数的价格及订单数量信息
     *
     * @param packageId
     * @param limitTime
     * @param currency
     * @return
     */
    @Override
    public Map<String, Object> getOrderParams(Integer packageId, Integer limitTime, Integer currency) {
        CspPackage pk = cspPackageDAO.selectByPrimaryKey(packageId);
        float money = 0.00f;  //金额
        Integer num = 1;     //数量
        Integer packageType = 0;    //是否年费  0：月费  1：年费
        if (limitTime % 12 == 0) {
            packageType = 1;
            money = currency == 0 ? pk.getYearRmb() * (limitTime / 12) : pk.getYearUsd() * (limitTime / 12);
            num = limitTime / 12;
        } else {
            money = currency == 0 ? pk.getMonthRmb() * limitTime : pk.getMonthUsd() * limitTime;
            num = limitTime;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("money", (float) Math.round(money * 10000) / 10000);
        map.put("num", num);
        map.put("packageType", packageType);
        return map;
    }

    /**
     * 获取所有套餐信息
     *
     * @return
     */
    @Override
    public List<CspPackage> findCspPackage() {
        return cspPackageDAO.findCspPackage();
    }
}
