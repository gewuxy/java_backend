package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspPackageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/8.
 */
public interface CspPackageService extends BaseService<CspPackage> {
    // 获取用户当前套餐版本
    CspPackage findUserPackageById(String userId);

    /**
     * 获取当前参数相对金额跟数量
     *
     * @param packageId
     * @param limitTime
     * @param currency
     * @return
     */
    Map<String, Object> getOrderParams(Integer packageId, Integer limitTime, Integer currency);

    /**
     * 获取所有套餐信息
     *
     * @return
     */
    List<CspPackage> findCspPackage();
}
