package cn.medcn.csp.realm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * Created by lixuan on 2017/10/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    protected String id;

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return getId();
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
