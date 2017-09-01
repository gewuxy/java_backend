package cn.medcn.common.service.impl;

import cn.jsms.api.JSMSClient;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.model.SMSPayload;
import cn.medcn.common.service.JSMSClientCreator;
import cn.medcn.common.service.JSmsService;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/4/7.
 */
@Service
public class JSmsServiceImpl implements JSmsService {

    private JSMSClient getJSMSClient(){
        return JSMSClientCreator.YAYACLIENT.getClient();
    }

    /**
     * 利用默认短信模板发送短信验证码
     *
     * @param mobile
     * @param msgTmpId
     * @return
     */
    @Override
    public String send(String mobile, Integer msgTmpId) throws Exception {
        SMSPayload.Builder builder = new SMSPayload.Builder();
        builder.setMobildNumber(mobile);
        builder.setTempId(msgTmpId);
        SMSPayload smsPayload = builder.build();
        SendSMSResult result = getJSMSClient().sendSMSCode(smsPayload);
        return result.getMessageId();
    }

    /**
     * 检测验证码是否合法
     *
     * @param msgId
     * @param code
     * @return
     */
    @Override
    public boolean verify(String msgId, String code) throws Exception {
        ValidSMSResult result = getJSMSClient().sendValidSMSCode(msgId,code);
        return result.getIsValid();
    }
}
