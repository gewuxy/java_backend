package cn.medcn.oauth.provider;

import cn.medcn.oauth.OAuthConstants;
import cn.medcn.oauth.config.OAuthServiceConfig;
import cn.medcn.oauth.decorator.OAuthServiceDecorator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixuan on 2017/9/15.
 */
public class OAuthDecoratorProvider {

    private static ConcurrentHashMap<Integer, OAuthServiceDecorator> decoratorMap = new ConcurrentHashMap<>();

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

        if (decoratorMap.get(serviceId) != null) {
            return decoratorMap.get(serviceId);
        } else {

            OAuthServiceConfig config = new OAuthServiceConfig();
            String appKey = null;
            String secret = null;
            switch (serviceId) {
                case 1 : // 微信
                    appKey = OAuthConstants.get("WeChat.app_key");
                    secret = OAuthConstants.get("WeChat.app_secret");
                    config.setApiKey(appKey);
                    config.setApiSecret(secret);
                    config.setCallback(callback);
                    config.setScope("snsapi_userinfo");
                    config.setServiceId(serviceId);
                    break;
                case 2 : // 微博
                    appKey = OAuthConstants.get("WeiBo.app_key");
                    secret = OAuthConstants.get("WeiBo.app_secret");
                    config.setApiKey(appKey);
                    config.setApiSecret(secret);
                    config.setCallback(callback);
                    config.setScope("snsapi_userinfo");
                   config.setServiceId(serviceId);
                    break;
                case 3 : // facebook

                    break;
                case 4 : // twitter

                    break;
                case 5 : // YaYa医师
                    appKey = OAuthConstants.get("YaYa.app_key");
                    secret = OAuthConstants.get("YaYa.app_secret");
                    config.setApiKey(appKey);
                    config.setApiSecret(secret);
                    config.setCallback(callback);
                    config.setScope("snsapi_userinfo");
                    config.setServiceId(serviceId);
                    break;
                default:
                    break;
            }
            OAuthServiceDecorator decorator = getDecorator(config);
            decoratorMap.put(serviceId, decorator);
            return getDecorator(config);
        }
    }
}
