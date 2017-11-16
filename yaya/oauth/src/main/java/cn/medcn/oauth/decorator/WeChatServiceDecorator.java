package cn.medcn.oauth.decorator;

import cn.medcn.oauth.config.OAuthServiceConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.medcn.oauth.api.WeChatApi;
import cn.medcn.oauth.dto.OAuthUser;
import cn.medcn.oauth.provider.ThirdPartyPlatform;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.*;

/**
 * Created by lixuan on 2017/9/15.
 */
public class WeChatServiceDecorator extends OAuthServiceDecorator {

    protected static final String APP_ID = "appid";

    protected static final String APP_SECRET = "secret";

    protected static final String GRAN_TYPE = "grant_type";

    protected final static String USER_INFO_ACCESS_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    public WeChatServiceDecorator(){

    }

    private DefaultApi20 api = new WeChatApi();


    /**
     * 微信需要重写获取accessToken方法
     * @param requestToken
     * @param verifier
     * @return
     */
    @Override
    public Token getAccessToken(Token requestToken, Verifier verifier) {

        OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        request.addQuerystringParameter(APP_ID, config.getApiKey());
        request.addQuerystringParameter(APP_SECRET, config.getApiSecret());
        request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
        request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        request.addQuerystringParameter(GRAN_TYPE, "authorization_code");
        if(config.hasScope()) request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
        Response response = request.send();
        return api.getAccessTokenExtractor().extract(response.getBody());
    }

    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        String rawResponse = accessToken.getRawResponse();
        String openId = JSON.parseObject(rawResponse).getString("openid");
        OAuthRequest request = new OAuthRequest(Verb.GET, String.format(USER_INFO_ACCESS_URL, accessToken.getToken(), openId));
        Response response = request.send();

        String body = response.getBody();
        JSONObject jsonObject = JSON.parseObject(body);

        OAuthUser user = new OAuthUser();
        user.setUid(jsonObject.getString("openid"));
        user.setNickname(jsonObject.getString("nickname"));
        user.setGender("sex");
        user.setPlatformId(ThirdPartyPlatform.we_chat.ordinal() + 1);
        user.setCity(jsonObject.getString("city"));
        user.setProvince(jsonObject.getString("province"));
        user.setCountry(jsonObject.getString("country"));
        user.setIconUrl(jsonObject.getString("headimgurl"));

        return user;
    }
}
