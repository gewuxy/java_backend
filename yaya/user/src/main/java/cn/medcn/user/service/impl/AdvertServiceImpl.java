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
}
