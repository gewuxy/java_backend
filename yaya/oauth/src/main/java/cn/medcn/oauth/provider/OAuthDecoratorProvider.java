package cn.medcn.oauth.provider;

import cn.medcn.oauth.OAuthConstants;
import cn.medcn.oauth.config.OAuthServiceConfig;
import cn.medcn.oauth.decorator.OAuthServiceDecorator;

/**
 * Created by lixuan on 2017/9/15.
 */
public class OAuthDecoratorProvider {

    private OAuthDecoratorProvider(){

    }

    public static OAuthServiceDecorator getDecorator(OAuthServiceConfig config){
        Class clazz = ThirdPartyPlatform.getThirdPartyPlatform(config.getServiceId()).decoratorClazz;
        try {
            OAuthServiceDecorator decorator = (OAuthServiceDecorator) clazz.newInstance();
            decorator.setOAuthService(OAuthServiceProvider.getService(config));
            return decorator;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    //todo 未完善其他三方平台创建
    public static OAuthServiceDecorator getDecorator(int serviceId, String callback){
        OAuthServiceConfig config = new OAuthServiceConfig();
        switch (serviceId) {
            case 1 :

                break;
            case 2 :

                break;
            case 3 :

                break;
            case 4 :

                break;
            case 5 :
                String appKey = OAuthConstants.get("YaYa.app_key");
                String secret = OAuthConstants.get("YaYa.app_secret");
                config.setApiKey(appKey);
                config.setApiSecret(secret);
                config.setCallback(callback);
                config.setScope("snsapi_userinfo");
                config.setServiceId(serviceId);
                break;
            default:
                break;
        }
        return getDecorator(config);
    }
}
