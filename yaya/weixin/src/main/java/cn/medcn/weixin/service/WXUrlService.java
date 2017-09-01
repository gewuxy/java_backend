package cn.medcn.weixin.service;

/**
 * Created by lixuan on 2017/7/25.
 */
public interface WXUrlService {

    String URL_PARAM_FIRST_SEPRATOR = "?";

    String URL_PARAM_SEPRATOR = "&";

    String generateWXURL(String subURL);
}
