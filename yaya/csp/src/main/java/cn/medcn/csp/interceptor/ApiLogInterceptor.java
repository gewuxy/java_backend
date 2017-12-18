package cn.medcn.csp.interceptor;

import cn.medcn.common.Constants;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.user.model.CspLog;
import cn.medcn.user.model.OsType;
import cn.medcn.user.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * CSP APP端日志拦截器
 * Created by lixuan on 2017/12/15.
 */
public class ApiLogInterceptor implements HandlerInterceptor {

    @Autowired
    protected LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long start = System.currentTimeMillis();
        request.setAttribute(Constants.LOG_START_KEY, start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long end = System.currentTimeMillis();
        Long start = (Long) request.getAttribute(Constants.LOG_START_KEY);
        Integer useTime = ((Long)(end-start)).intValue();

        Principal principal = SecurityUtils.get();

        if (principal != null) {
            CspLog appLog = new CspLog();
            appLog.setAction(request.getRequestURI());
            appLog.setUserId(principal == null ? "0" : principal.getId());
            appLog.setUseTime(useTime);
            appLog.setLogTime(new Date());
            appLog.setAppVersion(request.getHeader(Constants.APP_VERSION_KEY));
            appLog.setOsType(request.getHeader(Constants.APP_OS_TYPE_KEY));
            appLog.setOsVersion(request.getHeader(Constants.APP_OS_VERSION_KEY));
            logService.saveCspLog(appLog);
        }

    }
}
