package cn.medcn.jcms.interceptor;

import cn.medcn.common.Constants;
import cn.medcn.jcms.security.Principal;
import cn.medcn.user.model.AppInfo;
import cn.medcn.user.model.AppLog;
import cn.medcn.user.service.LogService;
import org.apache.shiro.SecurityUtils;
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
        Long start = System.currentTimeMillis();
        httpServletRequest.setAttribute(Constants.LOG_START_KEY, start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
//        Long start = (Long) httpServletRequest.getAttribute(Constants.LOG_START_KEY);
//        Long end = System.currentTimeMillis();
//        Integer useTime = ((Long)(end-start)).intValue();
//        AppLog log = new AppLog();
//        log.setAction(httpServletRequest.getRequestURI());
//        log.setAppId(AppInfo.YAYA.getAppId());
//        log.setUserId(principal == null?0:principal.getId());
//        log.setLogTime(new Date());
//        log.setUseTime(useTime);
//        log.setOsType(httpServletRequest.getHeader("os_type"));
//        log.setLogIp(httpServletRequest.getRemoteAddr());
//        log.setAppVersion("manager");
        //logService.pushToQueue(log);
    }
}
