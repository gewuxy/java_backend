package cn.medcn.weixin.service;

/**
 * Created by lixuan on 2017/7/18.
 */
public interface WXTokenService {


    String getAccessTokenByUrl();

    String getGlobalAccessToken();

    String refreshGlobalAccessToken();

    void cacheToken(String tokenCacheKey, String accessToken);

    String getTokenByParams(String appId, String secret);

    /**
     * 生成二维码
     * @param sceneId
     * @return
     */
    String generateQR_code(String sceneId);

    void downloadQR_code(String ticket, String sceneId);
}
