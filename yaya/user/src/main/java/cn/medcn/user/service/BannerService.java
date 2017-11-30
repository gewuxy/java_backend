package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.Banner;

/**
 * @Author：jianliang
 * @Date: Creat in 14:25 2017/11/23
 */
public interface BannerService extends BaseService<Banner>{
    MyPage<Banner> findBannerList(Pageable pageable);
}
