package cn.medcn.api.interceptor;

import cn.medcn.api.dto.Principal;
import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.TailorMadeUtils;
import cn.medcn.user.model.AppInfo;
import cn.medcn.user.model.AppLog;
import cn.medcn.user.service.LogService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/2.
 */
public class LogInterceptor implements HandlerInterceptor {

    @Resource
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String masterId = httpServletRequest.getHeader(Constants.MASTER_ID_KEY);
        TailorMadeUtils.set(CheckUtils.isEmpty(masterId) ? null : Integer.valueOf(masterId));
        Long start = System.currentTimeMillis();
        httpServletRequest.setAttribute(Constants.LOG_START_KEY, start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Long start = (Long) httpServletRequest.getAttribute(Constants.LOG_START_KEY);
        Long end = System.currentTimeMillis();
        Integer useTime = ((Long)(end-start)).intValue();
        AppLog log = new AppLog();
        log.setAction(httpServletRequest.getRequestURI());
        log.setAppId(AppInfo.YAYA.getAppId());
        log.setUserId(principal == null?0:principal.getId());
        log.setLogTime(new Date());
        log.setUseTime(useTime);
        log.setAppVersion(httpServletRequest.getHeader(Constants.APP_VERSION_KEY));
        log.setOsType(httpServletRequest.getHeader(Constants.APP_OS_TYPE_KEY));
        log.setOsVersion(httpServletRequest.getHeader(Constants.APP_OS_VERSION_KEY));
        //log.setAppVersion(Constants.APP_VERSION_MANAGER);
        log.setLogIp(httpServletRequest.getRemoteAddr());
        logService.pushToQueue(log);
    }
}
