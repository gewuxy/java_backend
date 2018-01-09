package cn.medcn.csp.filter;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.*;
import cn.medcn.user.model.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lixuan on 2017/12/1.
 * 用来判断被Shiro拦截的Ajax请求是否已经超时
 */
public class XMLRequestFilter extends FormAuthenticationFilter {

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse res, Object mappedValue) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (isAjax(request)) {
            Subject subject = getSubject(request, response);
            if (subject == null || !subject.isAuthenticated()) {
                response.setHeader("session_status", "timeout");
                ResponseUtils.writeJson(response, APIUtils.error(APIUtils.ERROR_CODE_UNAUTHED, SpringUtils.getMessage("user.error.not_authed")));
                return false;
            }
            return false;
        } else {
            return super.onAccessDenied(request, response, mappedValue);
        }
    }


    @Override
    public boolean onPreHandle(ServletRequest req, ServletResponse res, Object mappedValue) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Principal principal = getCachedPrincipal();
        if (isAjax(request)) {
            if (principal != null && !principal.getActive()) {
                ResponseUtils.writeJson(response, APIUtils.error(APIUtils.ERROR_CODE_UNAUTHED, SpringUtils.getMessage("user.unActive.email")));
                return false;
            }
        } else {
            if (principal != null && !principal.getActive()) {
                //强制用户登出
                Subject subject = SecurityUtils.getSubject();
                subject.logout();
                //跳转到登录页面并提示
                String loginUrl = getLoginUrl() + "?error_code=freeze";
                WebUtils.issueRedirect(request, response, loginUrl);
                return false;
            }
        }
        return super.onPreHandle(request, response, mappedValue);
    }


    protected Principal getCachedPrincipal(){
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        if (principal != null) {
            principal = (Principal) redisCacheUtils.getCacheObject(Constants.TOKEN + "_" + principal.getToken());
        }
        return principal;
    }

    /**
     * 判断请求是否是ajax请求
     * @param request
     * @return
     */
    protected boolean isAjax(HttpServletRequest request){
        String xmlRequestSymbol = request.getHeader("x-requested-with");

        return CheckUtils.isNotEmpty(xmlRequestSymbol);
    }
}
