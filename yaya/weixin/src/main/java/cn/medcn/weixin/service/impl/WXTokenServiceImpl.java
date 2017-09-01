package cn.medcn.weixin.service.impl;

import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.FileUtils;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.JsonUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.dto.SceneDTO;
import cn.medcn.weixin.service.WXTokenService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by lixuan on 2017/7/18.
 */
@Service
public class WXTokenServiceImpl extends WXBaseServiceImpl implements WXTokenService {

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    protected WXTokenService wxTokenService;

    @Value("${WeChat.app_id}")
    private String appId;

    @Value("${WeChat.app_secret}")
    private String secret;

    @Value("${WeChat.Server.app_id}")
    private String serverAppId;

    @Value("${WeChat.Server.app_secret}")
    private String serverAppSecret;

    @Value("${app.file.upload.base}")
    protected String fileUploadBase;


    @Override
    protected WXTokenService getWxTokenService() {
        return this;
    }

    /**
     * 通过url获取token并替换redis中的旧值
     * @return
     */
    @Override
    public String getAccessTokenByUrl() {
        String accessToken = getTokenByParams(appId, secret);
        cacheToken(WeixinConfig.WE_CHAT_ACCESS_TOKEN_KEY, accessToken);
        return accessToken;
    }

    @Override
    public String getGlobalAccessToken() {
        String globalAccessToken = (String) redisCacheUtils.getCacheObject(WeixinConfig.WE_CHAT_GLOBAL_ACCESS_TOKEN_KEY);
        if (StringUtils.isEmpty(globalAccessToken)){
            globalAccessToken = refreshGlobalAccessToken();
        }
        return globalAccessToken;
    }

    @Override
    public String refreshGlobalAccessToken() {
        String globalToken = getTokenByParams(serverAppId, serverAppSecret);
        cacheToken(WeixinConfig.WE_CHAT_GLOBAL_ACCESS_TOKEN_KEY, globalToken);
        return globalToken;
    }

    @Override
    public void cacheToken(String tokenCacheKey, String accessToken) {
        redisCacheUtils.setCacheObject(tokenCacheKey, accessToken);
    }

    /**
     * 获取全局token
     * @param currentAppId
     * @param currentSecret
     * @return
     */
    @Override
    public String getTokenByParams(String currentAppId, String currentSecret) {
        Map<String,Object> params = Maps.newHashMap();
        params.put(WeixinConfig.PARAM_GRANT_TYPE_KEY,WeixinConfig.GRANT_TYPE_GET_ACCESS_TOKEN);
        params.put(WeixinConfig.PARAM_APPID_KEY,currentAppId);
        params.put(WeixinConfig.PARAM_SECRET_KEY,currentSecret);
        String accessTokenStr = HttpUtils.get(WeixinConfig.GET_ACCESS_TOKEN_URL,params);
        String accessToken = (String) JsonUtils.getValue(accessTokenStr, WeixinConfig.ACCESS_TOKEN_KEY);
        return accessToken;
    }


    /**
     * 生成二维码
     *
     * @param sceneId
     * @return
     */
    @Override
    public String generateQR_code(String sceneId) {
        String accessToken = wxTokenService.getGlobalAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action_name", "QR_LIMIT_STR_SCENE");
        SceneDTO sceneDTO = new SceneDTO(sceneId);
        Map<String, Object> sceneMap = Maps.newHashMap();
        sceneMap.put("scene", sceneDTO);
        jsonObject.put("action_info", sceneMap);
        String url = WeixinConfig.QRCODE_CREATE_URL;

        String response = wxGet(url, accessToken, jsonObject);
        return (String) JsonUtils.getValue(response, "ticket");
    }


    @Override
    public void downloadQR_code(String ticket, String sceneId) {
        String downloadUrl = WeixinConfig.QRCODE_SHOW_URL+"?ticket="+ticket;
        String saveDir = fileUploadBase+ FilePath.QRCODE+ File.separator;
        FileUtils.downloadNetWorkFile(downloadUrl, saveDir, sceneId+"."+ FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix);
    }
}
