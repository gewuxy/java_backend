package cn.medcn.weixin.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.BaseService;
import cn.medcn.weixin.model.WXUserInfo;

/**
 * Created by lixuan on 2017/7/24.
 */
public interface WXUserInfoService extends BaseService<WXUserInfo> {

    void addWXUser(WXUserInfo wxUserInfo) throws SystemException;

    WXUserInfo findWXUserInfo(String unionid);

    Integer checkBindStatus(String openid);

    void doUnSubscribe(String openid);

    WXUserInfo findWxUserInfoByAppUserId(Integer userId);
}
