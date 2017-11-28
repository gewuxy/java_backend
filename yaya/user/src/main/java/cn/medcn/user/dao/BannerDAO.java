package cn.medcn.user.dao;

import cn.medcn.common.pagination.Pageable;
import cn.medcn.user.model.Advert;
import cn.medcn.user.model.Banner;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/20.
 */
public interface BannerDAO extends Mapper<Banner>{
    List<Banner> findBannerList(Map<String, Object> params);
}
