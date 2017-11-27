package cn.medcn.common.service.impl;

import cn.jsms.api.common.SMSClient;
import cn.medcn.common.provider.SMSClientProvider;
import cn.medcn.common.service.SMSService;
import org.springframework.stereotype.Service;

import static cn.medcn.common.Constants.JI_GUANG_APP_KEY;
import static cn.medcn.common.Constants.JI_GUANG_SECRET;

/**
 * Created by lixuan on 2017/11/7.
 */
@Service("cspSmsService")
public class CspSMSServiceImpl extends CommonSMSServiceImpl implements SMSService {

    @Override
    public SMSClient getClient() {
        return SMSClientProvider.getClient(JI_GUANG_APP_KEY, JI_GUANG_SECRET);
    }
}
