package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspPackage;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/8.
 */
public interface CspPackageService extends BaseService<CspPackage> {
    // 获取用户当前套餐版本
    CspPackage findUserPackageById(String userId);
    List<CspPackage> findAllPackage();
    Map<String, Object> getOrderParams(Integer packageId, Integer limitTime, String currency);
}
