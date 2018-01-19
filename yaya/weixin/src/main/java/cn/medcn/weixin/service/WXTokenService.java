package cn.medcn.weixin.service;

import cn.medcn.common.excptions.SystemException;

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

    /**
     * 获取微信小程序access_token
     * @param appId
     * @param secret
     * @return
     */
    String getMiniAccessToken(String appId, String secret) throws SystemException;
}
