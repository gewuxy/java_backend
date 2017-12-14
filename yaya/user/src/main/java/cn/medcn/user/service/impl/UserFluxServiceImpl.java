package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dao.UserFluxDAO;
import cn.medcn.user.dao.UserFluxUsageDAO;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.model.UserFluxUsage;
import cn.medcn.user.service.UserFluxService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/10/26.
 */
@Service
public class UserFluxServiceImpl extends BaseServiceImpl<UserFlux> implements UserFluxService {

    @Autowired
    protected UserFluxDAO userFluxDAO;

    @Autowired
    protected UserFluxUsageDAO userFluxUsageDAO;

    @Override
    public Mapper<UserFlux> getBaseMapper() {
        return userFluxDAO;
    }

    @Override
    public UserFlux findByCourseId(String courseId) {

        return userFluxDAO.findByCourseId(Integer.valueOf(courseId));

    }

    /**
     * @param courseId
     * @param flux
     */
    @Override
    public void doDeduct(String courseId, int flux) {
        UserFlux userFlux = findByCourseId(courseId);
        if (userFlux != null) {
            userFlux.setFlux(userFlux.getFlux() - flux);
            userFluxDAO.updateByPrimaryKey(userFlux);

            //更新用户流量使用记录
            UserFluxUsage fluxUsage = findUsage(userFlux.getUserId(), String.valueOf(courseId));

            if (fluxUsage == null) {
                addUserFluxUsage(userFlux.getUserId(), String.valueOf(courseId), flux);
            } else {
                fluxUsage.setExpenseTime(new Date());
                fluxUsage.setExpense(fluxUsage.getExpense() + flux);
                fluxUsage.setExpireTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(VIDEO_DOWNLOAD_EXPIRE)));
                userFluxUsageDAO.updateByPrimaryKey(fluxUsage);
            }
        }

    }

    /**
     * 获取用户流量使用记录
     *
     * @param userId
     * @param courseId
     * @return
     */
    @Override
    public UserFluxUsage findUsage(String userId, String courseId) {
        UserFluxUsage cond = new UserFluxUsage();
        cond.setUserId(userId);
        cond.setMeetingId(courseId);

        UserFluxUsage userFluxUsage = userFluxUsageDAO.selectOne(cond);
        return userFluxUsage;
    }

    /**
     * 创建用户流量使用记录
     *
     * @param userId
     * @param courseId
     * @return
     */
    @Override
    public UserFluxUsage addUserFluxUsage(String userId, String courseId, int flux) {
        UserFluxUsage userFluxUsage = new UserFluxUsage();
        userFluxUsage.setId(StringUtils.nowStr());
        userFluxUsage.setMeetingId(courseId);
        userFluxUsage.setUserId(userId);
        userFluxUsage.setExpense(flux);
        userFluxUsage.setExpenseTime(new Date());
        userFluxUsage.setExpireTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(VIDEO_DOWNLOAD_EXPIRE)));

        userFluxUsageDAO.insert(userFluxUsage);

        return userFluxUsage;
    }

    @Override
    public int updateVideoDownloadCount(UserFluxUsage usage) {
        return userFluxUsageDAO.updateByPrimaryKeySelective(usage);
    }
}
