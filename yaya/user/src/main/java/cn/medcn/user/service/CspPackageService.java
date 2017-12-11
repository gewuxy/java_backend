package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspPackage;

/**
 * Created by Liuchangling on 2017/12/8.
 */
public interface CspPackageService extends BaseService<CspPackage> {
    // 获取用户当前套餐版本
    CspPackage findUserPackageById(String userId);
}
