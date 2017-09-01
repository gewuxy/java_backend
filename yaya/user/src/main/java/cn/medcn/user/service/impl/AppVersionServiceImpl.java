package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.AppVersionDAO;
import cn.medcn.user.model.AppVersion;
import cn.medcn.user.service.AppVersionService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/6/8.
 */
@Service
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersion> implements AppVersionService {

    @Autowired
    private AppVersionDAO appVersionDAO;

    @Override
    public Mapper<AppVersion> getBaseMapper() {
        return appVersionDAO;
    }


    /**
     * 查找最新的版本信息
     *
     * @param appType
     * @param driveTag
     * @return
     */
    @Override
    public AppVersion findNewly(String appType, String driveTag) {
        AppVersion appVersion = appVersionDAO.findNewly(appType, driveTag);
        return appVersion;
    }
}
