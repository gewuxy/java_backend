package cn.medcn.oauth.api;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * Created by lixuan on 2017/9/15.
 */
public class WeChatApi extends DefaultApi20{

    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=medcn";

    private static final String SCOPE_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=medcn";

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. WeChat does not support OOB");
        if (config.hasScope()) {
            return String.format(SCOPE_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), config.getScope());
        } else {
            return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
        }
    }

}
