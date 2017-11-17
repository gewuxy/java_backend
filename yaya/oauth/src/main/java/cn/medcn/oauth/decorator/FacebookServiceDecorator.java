package cn.medcn.oauth.decorator;

import cn.medcn.oauth.dto.OAuthUser;
import cn.medcn.oauth.provider.ThirdPartyPlatform;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lixuan on 2017/9/15.
 */
public class FacebookServiceDecorator extends OAuthServiceDecorator {

    protected static String  GET_USER_INFO_URL = "https://graph.facebook.com/me?access_token=%s";

    @Override
    public OAuthUser getOAuthUser(Token accessToken)  {
        String uid = (String) JSON.parseObject(accessToken.getRawResponse()).get("uid");
        OAuthRequest request = null;
        try {
            request = new OAuthRequest(Verb.GET, String.format(GET_USER_INFO_URL, URLEncoder.encode(accessToken.getToken(),"utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Response response = request.send();

        String body = response.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);

        OAuthUser user = new OAuthUser();
        user.setUid(jsonObject.getString("id"));
        user.setNickname(jsonObject.getString("name"));
        user.setGender(jsonObject.getString("gender"));
        user.setIconUrl(jsonObject.getString("picture"));
        user.setPlatformId(ThirdPartyPlatform.facebook.ordinal() + 1);
        return user;
    }
}
