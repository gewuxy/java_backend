package cn.medcn.oauth.service.impl;

import cn.medcn.oauth.OAuthConstants;
import cn.medcn.oauth.decorator.OAuthServiceDecorator;
import cn.medcn.oauth.dto.OAuthUser;
import cn.medcn.oauth.provider.OAuthDecoratorProvider;
import cn.medcn.oauth.service.OauthService;
import cn.medcn.user.model.BindInfo;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.springframework.stereotype.Service;

/**
 * Created by LiuLP on 2017/10/26/026.
 */
@Service
public class OauthServiceImpl implements OauthService {

    /**
     * 跳转第三方授权登录页面
     * @param thirdPartyId
     * @return
     */
    @Override
    public String jumpThirdPartyAuthorizePage(Integer thirdPartyId) {
        String callback = OAuthConstants.get("Oauth.callback")+ "?thirdPartyId=" + thirdPartyId;
        // 获取授权url
        OAuthServiceDecorator decorator = OAuthDecoratorProvider.getDecorator(thirdPartyId, callback);
        // 跳转授权url
        return decorator.getAuthorizeUrl();
    }

    /**
     * 获取用户信息
     * @param code
     * @param thirdPartyId
     * @return
     */
    @Override
    public OAuthUser getOauthUser(String code, Integer thirdPartyId) {
        Token accessToken = null;
        // 获取授权成功token
        String callback = OAuthConstants.get("Oauth.callback")+ "?thirdPartyId=" + thirdPartyId;
        OAuthServiceDecorator decorator = OAuthDecoratorProvider.getDecorator(thirdPartyId, callback);
        accessToken = decorator.getAccessToken(accessToken, new Verifier(code));
        // 获取授权成功后用户数据
        return decorator.getOAuthUser(accessToken);
    }


}
