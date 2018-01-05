package cn.medcn.api.interceptor;

import cn.medcn.common.utils.CookieUtils;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.service.WXOauthService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检测微信链接是否有cookie信息
 * 没有cookie信息则跳转到登录界面
 * Created by lixuan on 2017/7/25.
 */
public class WXCookieInterceptor implements HandlerInterceptor {

    private static final Log log = LogFactory.getLog(WXCookieInterceptor.class);

    @Resource
    private WXOauthService wxOauthService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String unionId = CookieUtils.getCookieValue(httpServletRequest, WeixinConfig.COOKIE_NAME_UNION_ID);
        String openId = CookieUtils.getCookieValue(httpServletRequest, WeixinConfig.COOKIE_NAME_OPEN_ID);
        String oauthUrl = wxOauthService.generateOAUTHURL("weixin/oauth", WeixinConfig.SCOPE_TYPE_USERINFO);
        if (StringUtils.isEmpty(unionId) || StringUtils.isEmpty(openId)){
            try {
                CookieUtils.setCookie(httpServletResponse, WeixinConfig.REDIRECT_HISTORY, httpServletRequest.getRequestURI(), 0);
                httpServletResponse.sendRedirect(oauthUrl);
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }

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
