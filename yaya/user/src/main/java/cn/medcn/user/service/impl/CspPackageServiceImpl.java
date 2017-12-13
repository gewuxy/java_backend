package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspPackageDAO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.service.CspPackageService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/8.
 */
@Service
public class CspPackageServiceImpl extends BaseServiceImpl<CspPackage> implements CspPackageService {
    @Autowired
    protected CspPackageDAO cspPackageDAO;

    @Override
    public Mapper<CspPackage> getBaseMapper() {
        return cspPackageDAO;
    }

    // 获取用户当前套餐版本
    public CspPackage findUserPackageById(String userId) {
        return cspPackageDAO.findUserPackageById(userId);
    }

    @Override
    public List<CspPackage> findAllPackage() {
        return cspPackageDAO.findAllPackage();
    }

    @Override
    public Map<String, Object> getOrderParams(Integer packageId, Integer limitTime, String currency) {
        CspPackage pk = cspPackageDAO.selectByPrimaryKey(packageId);
        float money = (float) 0.00;
        Integer num = 1;
        if(limitTime % 12 == 0  ){
            money = currency.equals("CN") ? pk.getYearRmb() * (limitTime / 12) :pk.getYearUsd() * (limitTime / 12);
            num = limitTime / 12;
        }else{
            money = currency.equals("CN") ? pk.getMonthRmb() * limitTime : pk.getMonthUsd() * limitTime;
            num = limitTime;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("money",(float)Math.round(money*10000)/10000);
        map.put("num",num);
        return map;
    }
}
