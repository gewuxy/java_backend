package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspPackageDAO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.service.CspPackageService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
