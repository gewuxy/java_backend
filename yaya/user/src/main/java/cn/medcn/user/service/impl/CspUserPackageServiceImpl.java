package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dao.CspUserPackageDAO;
import cn.medcn.user.dao.CspUserPackageHistoryDAO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.model.CspUserPackageHistory;
import cn.medcn.user.service.CspUserPackageService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Liuchangling on 2017/12/8.
 */
@Service
public class CspUserPackageServiceImpl extends BaseServiceImpl<CspUserPackage> implements CspUserPackageService {
    @Autowired
    protected CspUserPackageDAO userPackageDAO;

    @Autowired
    protected CspUserPackageHistoryDAO packageHistoryDAO;

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

    /**
     * 定时获取套餐过期的用户
     * @return
     */
    @Override
    public List<CspUserPackage> findCspUserPackageList() {
        return userPackageDAO.findUserPackages();
    }


    /**
     * 定时获取套餐过期的用户
     * 将套餐降为标准版 同时将用户发布的会议加锁
     */
    @Override
    public void doModifyUserPackage(List<CspUserPackage> userPackageList) {
        Integer beforePackageId ;
        for (CspUserPackage userPackage : userPackageList) {
            // 变更之前的套餐id
            beforePackageId = userPackage.getPackageId();

            userPackage.setPackageId(CspPackage.TypeId.STANDARD.getId());
            userPackage.setPackageStart(null);
            userPackage.setPackageEnd(null);
            userPackage.setUpdateTime(new Date());
            userPackage.setSourceType(CspUserPackageHistory.modifyType.EXPIRE_DOWNGRADE.ordinal());
            userPackageDAO.updateByPrimaryKey(userPackage);

            // 记录用户套餐变更明细
            doAddUserPackageDetail(userPackage, beforePackageId);
        }
    }

    /**
     * 保存用户套餐变更明细
     * @param userPackage
     * @param beforePackageId
     */
    private void doAddUserPackageDetail(CspUserPackage userPackage, Integer beforePackageId) {
        CspUserPackageHistory history = new CspUserPackageHistory();
        history.setId(StringUtils.nowStr());
        history.setUserId(userPackage.getUserId());
        history.setBeforePackageId(beforePackageId);
        history.setAfterPackageId(userPackage.getPackageId());
        history.setUpdateTime(new Date());
        history.setUpdateType(CspUserPackageHistory.modifyType.EXPIRE_DOWNGRADE.ordinal());
        packageHistoryDAO.insert(history);
    }


}
