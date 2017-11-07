package cn.medcn.common.provider;

import cn.jsms.api.common.SMSClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixuan on 2017/11/7.
 */
public class SMSClientProvider {

    private static Map<String, SMSClient> clientHolder = new ConcurrentHashMap<>();

    public static SMSClient getClient(String appKey, String secret){
        SMSClient client = null;
        synchronized (clientHolder) {
            client = clientHolder.get(appKey);
            if (client == null) {
                client = new SMSClient(secret, appKey);
                clientHolder.put(appKey, client);
            }
        }

        return client;
    }
}
