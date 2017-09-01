package cn.medcn.jcms.realm;

import cn.medcn.common.utils.MD5Utils;
import cn.medcn.jcms.security.Principal;
import cn.medcn.user.model.AppMenu;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import com.google.common.collect.Sets;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lixuan on 2017/4/18.
 */
public class AppUserRealm extends AuthorizingRealm {


    @Autowired
    private AppUserService appUserService;

    /**
     * 为当前用户设置权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(principalCollection != null){
            Principal principal = (Principal) principalCollection.getPrimaryPrincipal();
            List<AppMenu> menuList = null;
            //加载用户权限
        }
        return info;
    }

    /**
     * 用户登录
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
        AppUser condition = new AppUser();
        condition.setUsername(token.getUsername());
        AppUser user = appUserService.selectOne(condition);
        if (user != null) {
            if (!user.getAuthed()){
                throw new AuthenticationException("该帐号未审核.");
            }
            Principal principal = Principal.build(user);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,
                    user.getPassword(), getName());
            if(!getCredentialsMatcher().doCredentialsMatch(token, info)){
                throw new AuthenticationException("密码不正确.");
            }
            if(user.getPubFlag() == null || user.getPubFlag() == false){
                throw new AuthenticationException("此系统只支持单位号登录");
            }
            //登录成功
            user.setLastLoginIp(((UsernamePasswordToken) authenticationToken).getHost());
            user.setLastLoginTime(new Date());
            appUserService.updateByPrimaryKeySelective(user);
            return info;
        } else {
            throw new AuthenticationException("该帐号不存在.");
        }
    }
}
