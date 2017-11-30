package cn.medcn.user.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.BannerDAO;
import cn.medcn.user.model.Banner;
import cn.medcn.user.service.BannerService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：jianliang
 * @Date: Creat in 14:26 2017/11/23
 */
@Service
public class BannerServiceImpl extends BaseServiceImpl<Banner> implements BannerService{

    @Autowired
    private BannerDAO bannerDAO;

    @Override
    public Mapper<Banner> getBaseMapper() {
        return bannerDAO;
    }

    @Override
    public MyPage<Banner> findBannerList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<Banner> page = MyPage.page2Mypage((Page) bannerDAO.findBannerList(pageable.getParams()));
        return page;
    }
}
