package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.AdvertDAO;
import cn.medcn.user.dao.BannerDAO;
import cn.medcn.user.model.Advert;
import cn.medcn.user.model.Banner;
import cn.medcn.user.service.AdvertService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuLP on 2017/4/20.
 */
@Service
public class AdvertServiceImpl extends BaseServiceImpl<Advert> implements AdvertService{

    @Autowired
    private AdvertDAO advertDAO;
    @Autowired
    private BannerDAO bannerDAO;

    @Override
    public Mapper getBaseMapper() {
        return advertDAO;
    }


    @Override
    public List<Banner> selectBanner(Banner banner) {
        return bannerDAO.select(banner);
    }

    /**
     * 根据国内外标识 获取启动广告
     *
     * @param abroad
     * @return
     */
    @Override
    public Advert findCspAdvert(Integer abroad) {
        Advert cond = new Advert();
        cond.setActive(true);
        cond.setAbroad(abroad);
        cond.setAppId(Advert.AdvertApp.CSP.ordinal());
        return advertDAO.selectOne(cond);
    }
}
