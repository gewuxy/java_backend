package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.model.ReportPackage;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public interface CspUserPackageService extends BaseService<CspUserPackage> {

    void doModifyUserPackage(List<CspUserPackage> userPackageList);

    List<CspUserPackage> findCspUserPackageList();

    void addStanardInfo(String userId);

    void modifySendOldUser(String userId);

    int selectEdition(Integer packageId,Integer location);

    List<Map<String,Object>> getTodayPackageInfo();

    void insertReportPackage(ReportPackage packages);
}
