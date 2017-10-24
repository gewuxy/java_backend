package cn.medcn.csp.realm;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.csp.security.Principal;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lixuan on 2017/10/16.
 */
public class CspRealm extends AuthorizingRealm {

    @Autowired
    protected CspUserService cspUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CspUserInfo cspUser = null;
        final String defaultPwd = "123456";
        String password = null;
        if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            password = new String(token.getPassword());
            if (!CheckUtils.isEmpty(password)) {
                token.setPassword(MD5Utils.MD5Encode(password).toCharArray());
            } else {
                token.setPassword(MD5Utils.MD5Encode(defaultPwd).toCharArray());
            }
            cspUser = cspUserService.findByLoginName(token.getUsername());
        } else {
            ThirdPartyToken token = (ThirdPartyToken) authenticationToken;
            cspUser = cspUserService.selectByPrimaryKey(token.getId());
        }

        //账号不存在
        if (cspUser == null) {
            throw new AuthenticationException(SpringUtils.getMessage("user.error.nonentity"));
        }

        //用户未激活
        if (cspUser.getActive() == null || !cspUser.getActive()) {
            throw new AuthenticationException(SpringUtils.getMessage("user.unActive.email"));
        }

        if (!CheckUtils.isEmpty(password)){
            if (!MD5Utils.md5(password).equals(cspUser.getPassword())) {
                throw new AuthenticationException(SpringUtils.getMessage("user.error.password"));
            }
        }

        Principal principal = Principal.build(cspUser);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,
                CheckUtils.isEmpty(password) ? MD5Utils.md5(defaultPwd) : cspUser.getPassword(), getName());

        return info;
    }
}
