package cn.medcn.oauth.provider;

import cn.medcn.oauth.api.WeChatApi;
import cn.medcn.oauth.api.YaYaApi;
import cn.medcn.oauth.decorator.*;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.SinaWeiboApi20;
import org.scribe.builder.api.TwitterApi;

/**
 * Created by lixuan on 2017/9/15.
 */
public enum ThirdPartyPlatform {

    we_chat(WeChatApi.class, WeChatServiceDecorator.class),
    wei_bo(SinaWeiboApi20.class, WeiBoServiceDecorator.class),
    twitter(TwitterApi.class, TwitterServiceDecorator.class),
    facebook(FacebookApi.class, FacebookServiceDecorator.class),
    YaYa(YaYaApi.class, YaYaServiceDecorator.class);


    public Class apiClazz;

    public Class decoratorClazz;

    ThirdPartyPlatform(Class apiClazz, Class decoratorClazz) {
        this.apiClazz = apiClazz;
        this.decoratorClazz = decoratorClazz;
    }


    public static ThirdPartyPlatform getThirdPartyPlatform(int id){
        if (id > ThirdPartyPlatform.values().length){
            throw new RuntimeException(String.format("illegal serverId %d", id));
        }
        return ThirdPartyPlatform.values()[id - 1];
    }

}
