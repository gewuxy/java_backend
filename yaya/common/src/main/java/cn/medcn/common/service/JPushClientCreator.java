package cn.medcn.common.service;

import cn.jpush.api.JPushClient;
import cn.jsms.api.JSMSClient;
import cn.medcn.common.Constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixuan on 2017/4/7.
 */
public enum JPushClientCreator {

    YAYACLIENT("88391aa0f118d8646d1fb6e3", "52acc10b88c19bd680e92083"),

    MARY_CLIENT("948085aa03fc30293fec9fff", "da5668db7b73e7e0c1afbef0");

    private String appkey;

    private String secret;


    private static Map<String, JPushClient> clientMap = new ConcurrentHashMap<>();

    JPushClientCreator(String appkey, String secret){
        this.appkey = appkey;
        this.secret = secret;
    }


    public JPushClient getClient(){
        if (clientMap.get(appkey) == null) {
            JPushClient client = new JPushClient(secret, appkey);
            clientMap.put(appkey, client);
            return client;
        } else {
            return clientMap.get(appkey);
        }
    }

    public static JPushClient getInstance(Integer masterId){
        if (masterId == null) {
            return YAYACLIENT.getClient();
        } else if (masterId == Constants.MARY_MASTER_ID){
            return MARY_CLIENT.getClient();
        }
        return null;
    }
}
