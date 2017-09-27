package cn.medcn.oauth.config;

import cn.medcn.common.utils.CheckUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/9/15.
 */
@Data
@NoArgsConstructor
public class OAuthServiceConfig {

    protected String apiKey;//应用ID

    protected String apiSecret;//密钥

    protected String callback;//第三方平台在用户登录及授权操作通过后

    protected String scope;//申请的权限范围

    protected int serviceId;//第三方平台在咱们应用中的ID号


    public OAuthServiceConfig(String apiKey, String apiSecret, String callback, String scope,
                              int serviceId) {
        super();
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.callback = callback;
        this.serviceId = serviceId;
        this.scope = scope;
    }

    public boolean hasScope(){
        return CheckUtils.isNotEmpty(scope);
    }

}
