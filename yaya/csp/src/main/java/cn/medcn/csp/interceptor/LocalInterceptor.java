package cn.medcn.csp.interceptor;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.CookieUtils;
import cn.medcn.common.utils.LocalUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.medcn.common.Constants.*;

/**
 * Created by lixuan on 2017/9/26.
 */
public class LocalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String local = httpServletRequest.getHeader(LOCAL_KEY);

        String abroad = httpServletRequest.getHeader(ABROAD_KEY);

        if (CheckUtils.isEmpty(local)) {
            local = CookieUtils.getCookieValue(httpServletRequest, LOCAL_KEY);
            if (CheckUtils.isEmpty(local)) {
                local = DEFAULT_LOCAL;
                CookieUtils.setCookie(httpServletResponse, LOCAL_KEY, DEFAULT_LOCAL);
            }
        }

        LocalUtils.setAbroad(CheckUtils.isEmpty(abroad) ? false : ("1".equals(abroad) ? true : false));

        LocalUtils.setLocalStr(local);
        LocalUtils.set(LocalUtils.getByKey(local));

        String osType = httpServletRequest.getHeader(Constants.APP_OS_TYPE_KEY);
        LocalUtils.setOsTypeLocal(CheckUtils.isEmpty(osType) ? Constants.OS_TYPE_ANDROID : osType);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
