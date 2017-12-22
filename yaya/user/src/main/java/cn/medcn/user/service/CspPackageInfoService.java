package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.CspPackageInfo;

import java.util.List;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public interface CspPackageInfoService extends BaseService<CspPackageInfo>{
    List<CspPackageInfo> selectByPackageId(Integer id);


}
