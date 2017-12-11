package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.CspUserPackageDAO;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspUserPackageService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/12/8.
 */
@Service
public class CspUserPackageServiceImpl extends BaseServiceImpl<CspUserPackage> implements CspUserPackageService {
    @Autowired
    protected CspUserPackageDAO userPackageDAO;

    @Override
    public Mapper<CspUserPackage> getBaseMapper() {
        return userPackageDAO;
    }

    /**
     * 判断是否有用户套餐信息
     * @param userId
     * @return
     */
    @Override
    public Boolean isNewUser(String userId) {
        CspUserPackage info = new CspUserPackage();
        info.setUserId(userId);
        Integer count = userPackageDAO.selectCount(info);
        return count > 0 ? false:true;
    }
}
