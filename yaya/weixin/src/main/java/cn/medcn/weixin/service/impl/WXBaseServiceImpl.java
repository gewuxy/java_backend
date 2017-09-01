package cn.medcn.weixin.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.dto.Result;
import cn.medcn.weixin.service.WXTokenService;
import cn.medcn.weixin.util.ResultCheckUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.mapper.Mapper;

import java.util.Map;

/**
 * Created by lixuan on 2017/8/7.
 */
public abstract class WXBaseServiceImpl<T> extends BaseServiceImpl<T> {

    @Override
    public Mapper<T> getBaseMapper() {
        return null;
    }

    protected abstract WXTokenService getWxTokenService();


    protected String handleUrl(String url, String accessToken){
        String val = url + "?" + WeixinConfig.ACCESS_TOKEN_KEY + "=" + accessToken;
        return val;
    }


    protected String wxGet(String url, String accessToken, Map<String, Object> params) {
        params.put(WeixinConfig.ACCESS_TOKEN_KEY, accessToken);
        String response = HttpUtils.get(url, params);
        Result result = ResultCheckUtil.checkResult(response);
        if (result.getErrcode() != null && result.getErrcode() > 0) {
            //token超时
            accessToken = getWxTokenService().refreshGlobalAccessToken();
            params.put(WeixinConfig.ACCESS_TOKEN_KEY, accessToken);
            response = HttpUtils.get(url, params);
        }
        return response;
    }

    protected String wxPost(String url, String accessToken, Map<String, Object> params) {
        String finalUrl = handleUrl(url, accessToken);
        String response = HttpUtils.post(finalUrl, params, null);
        Result result = ResultCheckUtil.checkResult(response);
        if (result.getErrcode() != null && result.getErrcode().intValue() > 0) {
            //token超时
            accessToken = getWxTokenService().refreshGlobalAccessToken();
            finalUrl = handleUrl(url, accessToken);
            response = HttpUtils.post(finalUrl, params, null);
        }
        return response;
    }

    protected String wxPostJSON(String url, String accessToken, JSONObject jsonParams) {
        String finalUrl = handleUrl(url, accessToken);
        String response = HttpUtils.postJson(finalUrl, jsonParams);
        Result result = ResultCheckUtil.checkResult(response);
        if (result.getErrcode() != null && result.getErrcode().intValue() > 0) {
            //token超时
            accessToken = getWxTokenService().refreshGlobalAccessToken();
            finalUrl = handleUrl(url, accessToken);
            response = HttpUtils.postJson(finalUrl, jsonParams);
        }
        return response;
    }
}
