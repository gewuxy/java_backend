package cn.medcn.common.service;

import cn.jsms.api.JSMSClient;

/**
 * Created by lixuan on 2017/4/7.
 */
public enum JSMSClientCreator {

    YAYACLIENT("88391aa0f118d8646d1fb6e3", "52acc10b88c19bd680e92083");

    private String appkey;

    private String secret;

    private JSMSClient jsmsClient;

    JSMSClientCreator(String appkey, String secret){
        this.appkey = appkey;
        this.secret = secret;
    }

    public JSMSClient getClient(){
        if (jsmsClient == null){
            jsmsClient = new JSMSClient(secret, appkey);
        }
        return jsmsClient;
    }
}
