package cn.medcn.oauth.api;

import cn.medcn.oauth.OAuthConstants;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * Created by lixuan on 2017/9/25.
 */
public class YaYaApi extends DefaultApi20 {


    private static final String AUTHORIZE_URL = OAuthConstants.get("YaYa.authorize.url");

    private static final String ACCESS_TOKEN_END_POINT = OAuthConstants.get("YaYa.oauth.access_token");

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_END_POINT;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. WeChat does not support OOB");
        return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
    }
}
