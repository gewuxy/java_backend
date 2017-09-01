package cn.medcn.api.interceptor;

import cn.medcn.api.dto.Principal;
import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.SpringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * Created by lixuan on 2017/4/20.
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisCacheUtils<Principal> redisCacheUtils;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //客户端将token从header中传过来 作为每次请求的权限认证标识
        String token = httpServletRequest.getHeader(Constants.TOKEN);
        //String token = "eb3b4a00c831429cb16d1b5dd00d8db6";
        String cacheKey = Constants.TOKEN+"_"+token;
        Principal principal = redisCacheUtils.getCacheObject(cacheKey);
        if(principal == null){
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            Writer writer = httpServletResponse.getWriter();
            writer.write(APIUtils.error(APIUtils.ERROR_CODE_UNAUTHED,SpringUtils.getMessage("user.unauthed")));
            writer.flush();
            return false;
        }
        redisCacheUtils.setCacheObject(cacheKey, principal, Constants.TOKEN_EXPIRE_TIME);
        SecurityUtils.setUserInfo(principal);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
