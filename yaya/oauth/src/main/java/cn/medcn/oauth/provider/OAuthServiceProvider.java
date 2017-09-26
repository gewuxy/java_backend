package cn.medcn.oauth.provider;

import cn.medcn.oauth.config.OAuthServiceConfig;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixuan on 2017/9/15.
 */
public class OAuthServiceProvider {

    private static ConcurrentHashMap<Integer, OAuthService> serviceMap = new ConcurrentHashMap<>();

    private OAuthServiceProvider(){}

    public static OAuthService getService(OAuthServiceConfig config){
        OAuthService service = serviceMap.get(config.getServiceId());
        if (service == null) {
            service = createService(config);
        }
        return service;
    }


    protected static OAuthService createService(OAuthServiceConfig config) {

        return new ServiceBuilder().provider(ThirdPartyPlatform.getThirdPartyPlatform(config.getServiceId()).apiClazz)
                .apiKey(config.getApiKey())
                .apiSecret(config.getApiSecret())
                .callback(config.getCallback())
                .scope(config.getScope())
                .build();
    }
}
