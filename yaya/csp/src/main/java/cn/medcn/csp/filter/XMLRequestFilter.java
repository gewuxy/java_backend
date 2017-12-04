package cn.medcn.csp.filter;

import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.ResponseUtils;
import cn.medcn.common.utils.SpringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lixuan on 2017/12/1.
 * 用来判断被Shiro拦截的Ajax请求是否已经超时
 */
public class XMLRequestFilter extends FormAuthenticationFilter {

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
