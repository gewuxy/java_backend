package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/6/8.
 */
public interface AppVersionService extends BaseService<AppVersion> {
    /**
     * 查找最新的版本信息
     * @param appType
     * @param driveTag
     * @return
     */
    AppVersion findNewly(String appType, String driveTag);

    /**
     * 查看App上架列表
     * @param pageable
     * @return
     */
    MyPage<AppVersion> findappManageListByPage(Pageable pageable);

    List<AppVersion> findAppDownloadUrl(String appType);
}
