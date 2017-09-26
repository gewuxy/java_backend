package cn.medcn.oauth.decorator;

import cn.medcn.oauth.dto.OAuthUser;
import org.scribe.model.Token;

/**
 * Created by lixuan on 2017/9/15.
 */
public class FacebookServiceDecorator extends OAuthServiceDecorator {
    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        return null;
    }
}
