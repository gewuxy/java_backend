package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dao.CspUserPackageDetailDAO;
import cn.medcn.user.model.CspUserPackageDetail;
import cn.medcn.user.service.CspUserPackageDetailService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/12/12.
 */
@Service
public class CspUserPackageDetailServiceImpl extends BaseServiceImpl<CspUserPackageDetail> implements CspUserPackageDetailService {

    @Autowired
    private CspUserPackageDetailDAO packageDetailDAO;

    @Override
    public Mapper<CspUserPackageDetail> getBaseMapper() {
        return packageDetailDAO;
    }

    @Override
    public void addUserHistoryInfo(String userId,Integer oldId,Integer newId,Integer updateType){
        CspUserPackageDetail detail = new CspUserPackageDetail();
        detail.setBeforePackageId(oldId);
        detail.setUserId(userId);
        detail.setAfterPackageId(newId);
        detail.setId(StringUtils.nowStr());
        detail.setUpdateTime(new Date());
        detail.setUpdateType(updateType);
        packageDetailDAO.insert(detail);
    }
}
