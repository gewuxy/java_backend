package cn.medcn.common.service.impl;

import cn.jpush.api.JPushClient;
import cn.medcn.common.provider.PushClientProvider;
import cn.medcn.common.service.PushService;
import org.springframework.stereotype.Service;

import static cn.medcn.common.Constants.JI_GUANG_APP_KEY;
import static cn.medcn.common.Constants.JI_GUANG_SECRET;


/**
 * Created by lixuan on 2017/11/7.
 */
@Service("cspPushService")
public class CspPushServiceImpl extends CommonPushServiceImpl implements PushService{


    @Override
    public JPushClient getClient() {
        return PushClientProvider.getClient(JI_GUANG_APP_KEY, JI_GUANG_SECRET);
    }


}
