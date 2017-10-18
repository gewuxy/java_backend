package cn.medcn.csp.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.csp.security.Principal;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lixuan on 2017/10/17.
 */
public class CspBaseController extends BaseController {

    /**
     * 获取web端用户认证信息
     * @return
     */
    protected Principal getWebPrincipal(){
        return (Principal) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 根据请求生成ws地址
     * @param request
     * @return
     */
    protected String genWsUrl(HttpServletRequest request, Integer courseId){
        StringBuffer buffer = new StringBuffer();
        buffer.append(request.getScheme().toLowerCase().equals("https") ? "wss" : "ws");
        buffer.append("://").append(request.getServerName()).append(":").append(request.getServerPort());
        buffer.append("/live/order?courseId=").append(courseId
        );
        return buffer.toString();
    }
}
