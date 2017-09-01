package cn.medcn.api.interceptor;

import cn.medcn.api.wexin.security.Principal;
import cn.medcn.api.wexin.security.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.utils.CookieUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.config.WeixinConfig;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lixuan on 2017/8/4.
 */
public class WXAuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Resource
    private AppUserService appUserService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String unionId = CookieUtils.getCookieValue(httpServletRequest, WeixinConfig.COOKIE_NAME_UNION_ID);
        String openId = CookieUtils.getCookieValue(httpServletRequest, WeixinConfig.COOKIE_NAME_OPEN_ID);
        AppUser appUser = appUserService.findUserByUnoinId(unionId);
        if (appUser != null){
            appUser.setOpenid(openId);
            Principal principal = Principal.build(appUser);
            SecurityUtils.setUserInfo(principal);
        } else {
            httpServletResponse.sendRedirect("/weixin/bind");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
