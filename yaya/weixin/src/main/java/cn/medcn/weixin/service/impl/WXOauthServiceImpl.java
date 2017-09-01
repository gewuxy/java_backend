package cn.medcn.weixin.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.dto.OAuthDTO;
import cn.medcn.weixin.dto.Result;
import cn.medcn.weixin.model.WXUserInfo;
import cn.medcn.weixin.service.WXOauthService;
import cn.medcn.weixin.service.WXTokenService;
import cn.medcn.weixin.util.ResultCheckUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by LiuLP on 2017/7/21/021.
 */
@Service
public class WXOauthServiceImpl extends WXBaseServiceImpl implements WXOauthService {

    private static  final Log log = LogFactory.getLog(WXOauthServiceImpl.class);

    @Value("${WeChat.app_id}")
    private String appId;

    @Value("${WeChat.app_secret}")
    private String secret;

    @Autowired
    private WXTokenService wxTokenService;

    @Value("${WeChat.Server.app_id}")
    private String serverAppId;

    @Value("${WeChat.Server.app_secret}")
    private String serverAppSecret;

    @Value("${app.yaya.base}")
    private String appBaseUrl;

    @Override
    protected WXTokenService getWxTokenService() {
        return wxTokenService;
    }

    /**
     * 通过code获取授权openid
     * @param code
     * @return
     */
    @Override
    public OAuthDTO getOpenIdAndTokenByCode(String code) throws SystemException {
        OAuthDTO oAuthDTO = oauth(appId, secret, code);
        return oAuthDTO ;
    }

    /**
     * 通过网页授权token和openid获取微信用户基本信息
     * @param openId
     * @return
     */
    @Override
    public WXUserInfo getUserInfoByOpenIdAndToken(String openId,String token) throws SystemException{
        Map<String,Object> params = Maps.newHashMap();
        params.put(WeixinConfig.OPENID_KEY,openId);
        params.put(WeixinConfig.ACCESS_TOKEN_KEY,token);
        String userInfoStr = HttpUtils.get(WeixinConfig.GET_USERINFO_BY_OAUTH2_URL,params);
        Result result = ResultCheckUtil.checkResult(userInfoStr);
        if(result.getErrcode() != null && WeixinConfig.TOKEN_EXPIRE_CODE == result.getErrcode()){
            throw new SystemException("token过期");
        }else if(result.getErrcode() != null){
            throw new SystemException("err:"+result.getErrcode());
        }
        try {
            userInfoStr = new String(userInfoStr.getBytes(WeixinConfig.WX_ENCODING), WeixinConfig.TARGET_ENCODING);
        } catch (UnsupportedEncodingException e) {
            LogUtils.error(log,e.getMessage());
            throw new SystemException("非法的编码");
        }
        WXUserInfo userInfo = JSON.parseObject(userInfoStr,WXUserInfo.class);  //userInfo不为null
        return userInfo;
    }

    @Override
    public OAuthDTO serverOauth(String code) throws SystemException {
        return oauth(serverAppId, serverAppSecret, code);
    }

    private OAuthDTO oauth(String currentAppId, String currentAppSecret, String code) throws SystemException {
        Map<String,Object> params = Maps.newHashMap();
        params.put(WeixinConfig.PARAM_APPID_KEY,currentAppId);
        params.put(WeixinConfig.PARAM_SECRET_KEY,currentAppSecret);
        params.put(WeixinConfig.CODE_KEY,code);
        params.put(WeixinConfig.PARAM_GRANT_TYPE_KEY,WeixinConfig.OAUTH2_GRANT_TYPE_GET_ACCESS_TOKEN);
        String accessTokenStr = HttpUtils.get(WeixinConfig.OAUTH2_ACCESS_TOKEN_URL,params);
        Result result = ResultCheckUtil.checkResult(accessTokenStr);
        if(result.getErrcode() != null){
            throw new SystemException("err:"+result.getErrcode());
        }
        OAuthDTO oAuthDTO = JSON.parseObject(accessTokenStr, OAuthDTO.class);
        return oAuthDTO;
    }

    @Override
    public WXUserInfo getWXUserInfo(String openId) throws SystemException {
        String token = wxTokenService.getGlobalAccessToken();
        Map<String,Object> params = Maps.newHashMap();
        params.put(WeixinConfig.OPENID_KEY,openId);
        //params.put(WeixinConfig.ACCESS_TOKEN_KEY,token);
        String userInfoStr = wxGet(WeixinConfig.GET_USERINFO_URL, token , params);
        try {
            userInfoStr = new String(userInfoStr.getBytes(WeixinConfig.WX_ENCODING), WeixinConfig.TARGET_ENCODING);
        } catch (UnsupportedEncodingException e) {
            LogUtils.error(log,e.getMessage());
            throw new SystemException("非法的编码");
        }
        WXUserInfo userInfo = JSON.parseObject(userInfoStr,WXUserInfo.class);
        return userInfo;
    }

    /**
     * 生成微信认证链接
     *
     * @param redirect
     * @param scope
     * @return
     */
    @Override
    public String generateOAUTHURL(String redirect, String scope) throws SystemException {
        StringBuffer builder = new StringBuffer();
        try {
            builder.append(WeixinConfig.OAUTH_URL);
            builder.append("?"+WeixinConfig.PARAM_APPID_KEY+"="+serverAppId);
            builder.append("&"+WeixinConfig.PARAM_REDIRECT_URL_KEY+"="+ URLEncoder.encode(appBaseUrl+redirect,"utf-8"));
            builder.append("&"+WeixinConfig.PARAM_RESPONE_TYPE_KEY+"=code");
            builder.append("&"+WeixinConfig.PARAM_SCOPE_KEY+"="+scope);
            builder.append("&"+WeixinConfig.PARAM_STATE_KEY+"="+WeixinConfig.DEFAULT_STATE+"#"+WeixinConfig.WE_CHAT_REDIRECT);
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("非法的编码");
        }

        return builder.toString();
    }
}
