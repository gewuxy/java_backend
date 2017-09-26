package cn.medcn.oauth.decorator;

import cn.medcn.common.utils.PropertyUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.medcn.oauth.dto.OAuthUser;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

/**
 * Created by lixuan on 2017/9/25.
 */
public class YaYaServiceDecorator extends OAuthServiceDecorator {

    public static final int SERVICE_ID = 5;

    protected final static String USER_INFO_ACCESS_URL = PropertyUtils.readKeyValue("oauth.properties", "YaYa.oauth.user_info");


    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, String.format(USER_INFO_ACCESS_URL, accessToken.getToken(), accessToken.getSecret()));
        Response response = request.send();
        String body = response.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        JSONObject userJson = jsonObject.getJSONObject("data");
        OAuthUser user = new OAuthUser();
        user.setUid(userJson.getString("uid"));
        user.setNickname(userJson.getString("nickname"));
        user.setGender(userJson.getString("gender"));
        user.setPlatformId(SERVICE_ID);
        user.setCity(userJson.getString("city"));
        user.setProvince(userJson.getString("province"));
        user.setIconUrl(userJson.getString("avatar"));

        return user;
    }

}
