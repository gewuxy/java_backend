package cn.medcn.csp.interceptor;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.*;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lixuan on 2017/9/27.
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //客户端将token从header中传过来 作为每次请求的权限认证标识
        String token = httpServletRequest.getHeader(Constants.TOKEN);
        //String token = "eb3b4a00c831429cb16d1b5dd00d8db6";

        if (CheckUtils.isEmpty(token)) {
            ResponseUtils.writeJson(httpServletResponse, APIUtils.error(APIUtils.ERROR_CODE_UNAUTHED, SpringUtils.getMessage("user.unauthed")));
            return false;
        }

        String cacheKey = Constants.TOKEN+"_"+token;
        Principal principal = (Principal) redisCacheUtils.getCacheObject(cacheKey);
        if(principal == null){
            ResponseUtils.writeJson(httpServletResponse, APIUtils.error(APIUtils.ERROR_CODE_UNAUTHED, SpringUtils.getMessage("user.unauthed")));
            return false;
        }
        redisCacheUtils.setCacheObject(cacheKey, principal, Constants.TOKEN_EXPIRE_TIME);
        SecurityUtils.set(principal);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
