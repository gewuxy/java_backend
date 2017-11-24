package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.Advert;
import cn.medcn.user.model.Banner;

import java.util.List;

/**
 * Created by LiuLP on 2017/4/20.
 */
public interface AdvertService extends BaseService<Advert>{


    List<Banner> selectBanner(Banner banner);

    /**
     * 根据国内外标识 获取启动广告
     * @param abroad
     * @return
     */
    Advert findCspAdvert(Integer abroad);

}
