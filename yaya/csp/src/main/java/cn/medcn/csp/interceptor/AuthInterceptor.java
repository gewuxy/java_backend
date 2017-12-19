package cn.medcn.csp.interceptor;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.*;
import cn.medcn.csp.CspConstants;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
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

    @Resource
    protected CspUserService cspUserService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //客户端将token从header中传过来 作为每次请求的权限认证标识
        String token = httpServletRequest.getHeader(Constants.TOKEN);

        if (CheckUtils.isEmpty(token)) {
            ResponseUtils.writeJson(httpServletResponse, APIUtils.error(APIUtils.ERROR_CODE_UNAUTHED, SpringUtils.getMessage("user.error.not_authed")));
            return false;
        }

        String cacheKey = Constants.TOKEN+"_"+token;
        Principal principal = (Principal) redisCacheUtils.getCacheObject(cacheKey);
        if(principal == null){
            CspUserInfo cond = new CspUserInfo();
            cond.setToken(token);
            cond.setActive(true);
            CspUserInfo user = cspUserService.selectOne(cond);
            if (user == null) {
                ResponseUtils.writeJson(httpServletResponse, APIUtils.error(APIUtils.ERROR_CODE_UNAUTHED, SpringUtils.getMessage("user.error.not_authed")));
                return false;
            } else {
                principal = Principal.build(user);
            }

        }

        //判断用户是否已经被冻结
        if (principal.getActive() != null && !principal.getActive()) {
            ResponseUtils.writeJson(httpServletResponse, APIUtils.error(APIUtils.ERROR_CODE_UNAUTHED, SpringUtils.getMessage("user.error.not_authed")));
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
