package cn.medcn.weixin.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.weixin.dto.OAuthDTO;
import cn.medcn.weixin.model.WXUserInfo;

import java.util.Map;

/**
 * Created by lixuan on 2017/7/18.
 */
public interface WXOauthService {

    /**
     * 通过code获取授权openid
     * @param code
     * @return
     */
    OAuthDTO getOpenIdAndTokenByCode(String code) throws SystemException;

    /**
     * 通过code获取授权openid
     * @param code
     * @param appKey
     * @param appSecret
     * @return
     */
    OAuthDTO getOpenIdAndTokenByCode(String code, String appKey, String appSecret) throws SystemException;

    /**
     * 通过全局token和openid获取微信用户的昵称
     * @param openId
     * @return
     */
    WXUserInfo getUserInfoByOpenIdAndToken(String openId,String token) throws SystemException;

    OAuthDTO serverOauth(String code) throws SystemException;

    WXUserInfo getWXUserInfo(String openId) throws SystemException;

    /**
     * 生成微信认证链接
     * @param redirect
     * @param scope
     * @return
     */
    String generateOAUTHURL(String redirect, String scope) throws SystemException;
}
