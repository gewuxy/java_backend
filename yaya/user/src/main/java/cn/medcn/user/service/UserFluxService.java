package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.model.UserFluxUsage;

/**
 * Created by lixuan on 2017/10/26.
 */
public interface UserFluxService extends BaseService<UserFlux> {


    int VIDEO_DOWNLOAD_EXPIRE = 10;//视频下载超时天数

    UserFlux findByCourseId(String courseId);

    /**
     * 扣除用户流量
     * @param courseId
     * @param flux
     */
    void doDeduct(String courseId, int flux);

    /**
     * 获取用户流量使用记录
     * @param userId
     * @param courseId
     * @return
     */
    UserFluxUsage findUsage(String userId, String courseId);

    /**
     * 创建用户流量使用记录
     * @param userId
     * @param courseId
     * @return
     */
    UserFluxUsage addUserFluxUsage(String userId, String courseId, int flux);
}
