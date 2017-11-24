package cn.medcn.jbms.realm;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.jbms.security.Principal;
import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SysMenuService;
import cn.medcn.sys.service.SystemUserService;
import com.google.common.collect.Sets;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lixuan on 2017/4/18.
 */
public class SystemUserRealm extends AuthorizingRealm {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 为当前用户设置权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (principalCollection != null) {
            Principal principal = (Principal) principalCollection.getPrimaryPrincipal();
            List<SystemMenu> menuList = null;
            try {
                if (principal.isAdmin()) {
                    menuList = sysMenuService.findRootMenus();
                } else {
                    menuList = sysMenuService.findMenusByRole(principal.getRoleId());
                }
            } catch (SystemException e) {
                e.printStackTrace();
            }
            Set<String> permissions = Sets.newHashSet();
            if (menuList != null) {
                for (SystemMenu menu : menuList) {
                    if (!StringUtils.isEmpty(menu.getPerm())) {
                        permissions.add(menu.getPerm());
                        //System.out.println(menu.getPerm());
                    }
                }
                info.setStringPermissions(permissions);
            }
        }
        return info;
    }

    /**
     * 用户登录
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String password = new String(token.getPassword());
        token.setPassword(MD5Utils.MD5Encode(password).toCharArray());

        // 校验用户名密码
        SystemUser condition = new SystemUser();
        condition.setUserName(token.getUsername());
        SystemUser user = systemUserService.selectOne(condition);
        if (user != null) {
            if (!user.getActive()) {
                throw new AuthenticationException("该帐号未激活.");
            }
            Principal principal = Principal.build(user);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,
                    user.getPassword(), getName());
            if (!getCredentialsMatcher().doCredentialsMatch(token, info)) {
                throw new AuthenticationException("密码不正确.");
            }
            //登录成功
            user.setLastLoginIp(((UsernamePasswordToken) authenticationToken).getHost());
            user.setLastLoginDate(new Date());
            systemUserService.updateByPrimaryKeySelective(user);
            return info;
        } else {
            throw new AuthenticationException("该帐号不存在.");
        }
    }


}
