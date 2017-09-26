package cn.medcn.oauth;

import cn.medcn.common.utils.PropertyUtils;

import java.util.Properties;

/**
 * Created by lixuan on 2017/9/26.
 */
public class OAuthConstants {

    protected final static Properties properties = PropertyUtils.read("oauth.properties");

    public static String get(String key){
        return String.valueOf(properties.get(key));
    }
}
