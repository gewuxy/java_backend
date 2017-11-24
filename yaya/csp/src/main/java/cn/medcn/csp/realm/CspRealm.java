package cn.medcn.csp.realm;

import cn.medcn.common.ctrl.FilePath;
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
import org.springframework.beans.factory.annotation.Value;


/**
 * Created by lixuan on 2017/10/16.
 */
public class CspRealm extends AuthorizingRealm {

    public static final String AUTH_DEFAULT_PASSWORD = "UIwe2389xc@!";

    public static final String LOGIN_MOBILE = "mobile";

    @Autowired
    protected CspUserService cspUserService;

    @Value("${app.file.base}")
    protected String fileBase;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CspUserInfo cspUser = null;
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String password = token.getPassword() == null ? null : new String(token.getPassword());

        if (!CheckUtils.isEmpty(password)) {
            token.setPassword(MD5Utils.MD5Encode(password).toCharArray());
        } else {
            token.setPassword(MD5Utils.MD5Encode(AUTH_DEFAULT_PASSWORD).toCharArray());
        }

        if (!CheckUtils.isEmpty(token.getHost()) && !token.getHost().equals(LOGIN_MOBILE)){
            cspUser = cspUserService.selectByPrimaryKey(token.getUsername());
        } else {
            cspUser = cspUserService.findByLoginName(token.getUsername());
        }

        // 用户不存在
        if (cspUser == null) {
            throw new AuthenticationException(SpringUtils.getMessage("user.error.nonentity"));
        }

        // 用户未激活
        if (cspUser.getActive() == null || !cspUser.getActive()) {
            throw new AuthenticationException(SpringUtils.getMessage("user.unActive.email"));
        }

        if (CheckUtils.isEmpty(token.getHost())){
            if (!MD5Utils.md5(password).equals(cspUser.getPassword())) {
                throw new AuthenticationException(SpringUtils.getMessage("user.error.password"));
            }
        }

        Principal principal = Principal.build(cspUser);

        if (CheckUtils.isEmpty(principal.getAvatar())) {
            principal.setAvatar(fileBase + FilePath.PORTRAIT.path + "/admin-userImg.png");
        } else if (!principal.getAvatar().startsWith("http")){
            principal.setAvatar(fileBase + principal.getAvatar());
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,
                CheckUtils.isEmpty(password) ? MD5Utils.md5(AUTH_DEFAULT_PASSWORD) : cspUser.getPassword(), getName());

        return info;
    }
}
