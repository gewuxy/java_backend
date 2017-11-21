package cn.medcn.csp.admin.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.csp.admin.dao.AppManageDao;
import cn.medcn.csp.admin.service.AppManageService;
import cn.medcn.user.model.AppVersion;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 12:07 2017/11/10
 */
@Service
public class AppManageServiceImpl extends BaseServiceImpl<AppVersion> implements AppManageService{

    @Autowired
    private AppManageDao appManageDao;

    @Override
    public MyPage<AppVersion> findappManageListByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        List<AppVersion> appVersionList= appManageDao.findappManageListByPage();
        MyPage<AppVersion> page = new MyPage<AppVersion>();
        page.setDataList(appVersionList);
        return page;
    }

    @Override
    public AppVersion selectFirstApp() {
        return appManageDao.selectFirstApp();
    }

    @Override
    public Mapper<AppVersion> getBaseMapper() {
        return appManageDao;
    }
}
