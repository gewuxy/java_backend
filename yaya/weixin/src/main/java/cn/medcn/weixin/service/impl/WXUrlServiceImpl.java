package cn.medcn.weixin.service.impl;

import cn.medcn.weixin.service.WXUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/7/25.
 */
@Service
public class WXUrlServiceImpl implements WXUrlService {

    @Value("${app.yaya.base}")
    private String appBase;

    @Override
    public String generateWXURL(String subURL) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(appBase);
        stringBuffer.append(subURL);
        //todo
        //这里可能需要考虑安全问题  对参数进行加密
        return stringBuffer.toString();
    }
}
