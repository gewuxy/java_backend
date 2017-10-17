package cn.medcn.csp.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.csp.security.Principal;
import org.apache.shiro.SecurityUtils;

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
}
