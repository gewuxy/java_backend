package cn.medcn.common.provider;

import cn.jpush.api.JPushClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixuan on 2017/11/7.
 */
public class PushClientProvider {

    private static Map<String, JPushClient> clientHolder = new ConcurrentHashMap<>();

    public static JPushClient getClient(String appKey, String secret){
        JPushClient client = null;
        synchronized (clientHolder) {
            client = clientHolder.get(appKey);
            if (client == null) {
                client = new JPushClient(secret, appKey);
                clientHolder.put(appKey, client);
            }
        }
        return client;
    }
}
