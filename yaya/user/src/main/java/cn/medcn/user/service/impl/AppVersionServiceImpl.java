package cn.medcn.user.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.AppVersionDAO;
import cn.medcn.user.model.AppVersion;
import cn.medcn.user.service.AppVersionService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 查看APP上架列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<AppVersion> findappManageListByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        List<AppVersion> appVersionList= appVersionDAO.findappManageListByPage();
        MyPage<AppVersion> page = new MyPage<AppVersion>();
        page.setDataList(appVersionList);
        return page;
    }

    /**
     * 根据type查询最新的app下载地址
     * @param appType
     * @return
     */
    @Override
    public List<AppVersion> findAppDownloadUrl(String appType){
        return appVersionDAO.findAppDownloadUrl(appType);
    }
}
