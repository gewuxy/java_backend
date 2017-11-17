package cn.medcn.oauth.decorator;

import cn.medcn.common.utils.HttpUtils;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixuan on 2017/9/15.
 */
public class FacebookServiceDecorator extends OAuthServiceDecorator {


    @Override
    public OAuthUser getOAuthUser(Token accessToken)  {

        return null;
    }


}
