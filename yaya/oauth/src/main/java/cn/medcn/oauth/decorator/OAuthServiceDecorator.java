package cn.medcn.oauth.decorator;

import cn.medcn.oauth.config.OAuthServiceConfig;
import cn.medcn.oauth.dto.OAuthUser;
import cn.medcn.oauth.provider.OAuthServiceProvider;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.io.UnsupportedEncodingException;

/**
 * Created by lixuan on 2017/9/15.
 */
public abstract class OAuthServiceDecorator {

    protected OAuthService oAuthService;

    protected OAuthServiceConfig config;


    public OAuthServiceDecorator(){}

    public OAuthServiceDecorator(OAuthServiceConfig config){
        this.config = config;
        this.oAuthService = OAuthServiceProvider.getService(config);
    }

    public void setOAuthService(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    public void setConfig(OAuthServiceConfig config) {
        this.config = config;
    }

    public abstract OAuthUser getOAuthUser(Token accessToken);

    public String getAuthorizeUrl(){
        return oAuthService.getAuthorizationUrl(null);
    }

    public Token getAccessToken(Token requestToken, Verifier verifier){
        return oAuthService.getAccessToken(requestToken, verifier);
    }

    public String getAccessToken(String code){
        Token token = oAuthService.getAccessToken(null, new Verifier(code));
        return token == null ? null : token.getToken();
    }

}
