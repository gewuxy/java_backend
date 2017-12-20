package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.CspUserPackage;

import java.util.Date;
import java.util.List;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public interface CspUserPackageService extends BaseService<CspUserPackage> {

    void doModifyUserPackage(List<CspUserPackage> userPackageList);

    List<CspUserPackage> findCspUserPackageList();

    void addStanardInfo(String userId);

    void modifyOldUser(CspUserPackage cspUserPackage, String userId);

    int selectPremiumEdition();

    int selectProfessionalEdition();
}
