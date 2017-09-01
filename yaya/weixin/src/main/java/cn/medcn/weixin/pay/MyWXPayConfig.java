package cn.medcn.weixin.pay;

import java.io.InputStream;

/**
 * Created by lixuan on 2017/7/31.
 */
public class MyWXPayConfig extends WXPayConfig {

    protected String mchId;

    protected String payApiKey;

    protected String appId;

    protected String appBase;

    public MyWXPayConfig(String appId, String mchId, String payApiKey, String appBase){
        this.appBase = appBase;
        this.appId = appId;
        this.mchId = mchId;
        this.payApiKey = payApiKey;
    }

    /**
     * 获取 App ID
     *
     * @return App ID
     */
    @Override
    String getAppID() {
        return appId;
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    @Override
    String getMchID() {
        return mchId;
    }

    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    @Override
    String getKey() {
        return payApiKey;
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    InputStream getCertStream() {
        return this.getClass().getClassLoader().getResourceAsStream("/WEB-INF/cert/apiclient_cert.p12");
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    @Override
    IWXPayDomain getWXPayDomain() {
        IWXPayDomain payDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                DomainInfo domainInfo = new DomainInfo(appBase, true);
                return domainInfo;
            }
        };
        return payDomain;
    }
}
