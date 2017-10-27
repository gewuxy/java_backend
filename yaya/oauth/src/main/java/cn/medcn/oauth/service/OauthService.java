package cn.medcn.oauth.service;

import cn.medcn.oauth.dto.OAuthUser;

/**
 * Created by LiuLP on 2017/10/26/026.
 */
public interface OauthService {

     /**
      * 跳转到授权页面
      * @param thirdPartyId
      * @return
      */
     String jumpThirdPartyAuthorizePage(Integer thirdPartyId);

     /**
      * 获取用户信息
      * @param code
      * @param thirdPartyId
      * @return
      */
     OAuthUser getOauthUser(String code, Integer thirdPartyId);
}
