package cn.medcn.csp.interceptor;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.CookieUtils;
import cn.medcn.common.utils.LocalUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Locale;

import static cn.medcn.common.Constants.DEFAULT_LOCAL;

/**
 * Created by lixuan on 2017/9/26.
 */
public class LocalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String local = httpServletRequest.getHeader("_local");
        if (CheckUtils.isEmpty(local)) {
            local = CookieUtils.getCookieValue(httpServletRequest, "_local");
            if (CheckUtils.isEmpty(local)) {
                local = DEFAULT_LOCAL;
            }
        }
        LocalUtils.setLocalStr(local);
        LocalUtils.set(LocalUtils.getByKey(local));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
