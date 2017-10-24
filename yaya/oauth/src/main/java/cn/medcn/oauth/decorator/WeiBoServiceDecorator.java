package cn.medcn.oauth.decorator;

import cn.medcn.oauth.provider.ThirdPartyPlatform;
import com.alibaba.fastjson.JSONObject;
import cn.medcn.oauth.dto.OAuthUser;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

/**
 * Created by lixuan on 2017/9/15.
 */
public class WeiBoServiceDecorator extends OAuthServiceDecorator {

    protected final static String USER_ACCESS_URL = "https://api.weibo.com/2/users/show.json?access_token=%s&uid=%s";

    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, String.format(USER_ACCESS_URL, accessToken.getToken(), accessToken.getSecret()));
        Response response = request.send();

        String body = response.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);

        OAuthUser user = new OAuthUser();
        user.setUid(jsonObject.getString("id"));
        user.setNickname(jsonObject.getString("screen_name"));
        user.setProvince(jsonObject.getString("province"));
        user.setCity(jsonObject.getString("city"));
        user.setCountry("cn");
        user.setGender(jsonObject.getString("gender"));
        user.setIconUrl(jsonObject.getString("profile_image_url"));
        user.setPlatformId(ThirdPartyPlatform.wei_bo.ordinal() + 1);
        return user;
    }
}
