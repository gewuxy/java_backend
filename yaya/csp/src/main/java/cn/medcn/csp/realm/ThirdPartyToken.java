package cn.medcn.csp.realm;

import cn.medcn.common.utils.MD5Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by lixuan on 2017/10/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyToken implements AuthenticationToken {

    public static final String AUTH_DEFAULT_PASSWORD = "UIwe2389xc@!";

    protected String id;

    @Override
    public Object getPrincipal() {
        return getId();
    }

    @Override
    public Object getCredentials() {
        return MD5Utils.md5(AUTH_DEFAULT_PASSWORD);
    }
}
