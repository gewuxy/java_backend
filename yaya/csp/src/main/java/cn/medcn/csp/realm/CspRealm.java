package cn.medcn.csp.realm;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.utils.*;
import cn.medcn.user.model.Principal;
import cn.medcn.meet.service.AudioService;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspPackageService;
import cn.medcn.user.service.CspUserService;
import org.apache.shiro.SecurityUtils;
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

    @Autowired
    protected RedisCacheUtils redisCacheUtils;

    @Autowired
    protected CspPackageService cspPackageService;

    @Autowired
    protected AudioService audioService;

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

        // 用户被冻结
        if (cspUser.getFrozenState() != null && cspUser.getFrozenState()) {
            throw new AuthenticationException(SpringUtils.getMessage("user.frozen.account"));
        }

        // 判断host为空时，用户使用邮箱+密码登录
        if (CheckUtils.isEmpty(token.getHost())){
            if (!MD5Utils.md5(password).equals(cspUser.getPassword())) {
                throw new AuthenticationException(SpringUtils.getMessage("user.error.password"));
            }
        }

        // 是否海外账号登录
        boolean abroad = cspUser.getAbroad() == null ? false : cspUser.getAbroad();
        boolean loginAbroad = LocalUtils.getLocalStr().equals(LocalUtils.Local.zh_CN.name()) ? false : true;
        if (!abroad && loginAbroad) {
            // 国内账号登录
            throw new AuthenticationException(SpringUtils.getMessage("cn.user.web.login.error"));

        }
        if (abroad && !loginAbroad) {
            // 海外账号登录
            throw new AuthenticationException(SpringUtils.getMessage("en.user.web.login.error"));
        }

        String userToken = cspUser.getToken();
        //检测用户是否有token，如果没有进行添加
        if(StringUtils.isEmpty(userToken)) {
            cspUser.setToken(UUIDUtil.getUUID());
            cspUserService.updateByPrimaryKeySelective(cspUser);
        }
        Principal principal = Principal.build(cspUser);
        CspPackage cspPackage = cspPackageService.findUserPackageById(cspUser.getId());
        principal.setPackageId(cspPackage == null ? null : cspPackage.getId());
        principal.setCspPackage(cspPackage);
        principal.setNewUser(cspPackage == null);
        //添加用户信息缓存
        redisCacheUtils.setCacheObject(Constants.TOKEN + "_" + userToken, principal, Constants.TOKEN_EXPIRE_TIME);

        if (CheckUtils.isEmpty(principal.getAvatar())) {
            principal.setAvatar(fileBase + FilePath.PORTRAIT.path + "/admin-userImg.png");
        } else if (!principal.getAvatar().startsWith("http")){
            principal.setAvatar(fileBase + principal.getAvatar());
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,
                CheckUtils.isEmpty(password) ? MD5Utils.md5(AUTH_DEFAULT_PASSWORD) : cspUser.getPassword(), getName());
        SecurityUtils.getSubject().getSession().setAttribute("principal", principal);
        return info;
    }
}
