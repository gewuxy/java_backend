package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspUserPackage;

import java.util.List;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public interface CspUserPackageService extends BaseService<CspUserPackage> {

    Boolean isNewUser(String UserId);

    void doModifyUserPackage(List<CspUserPackage> userPackageList);

    List<CspUserPackage> findCspUserPackageList();
}
