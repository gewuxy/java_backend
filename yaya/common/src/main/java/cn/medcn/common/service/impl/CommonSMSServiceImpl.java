package cn.medcn.common.service.impl;

import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.model.SMSPayload;
import cn.medcn.common.service.SMSService;

/**
 * Created by lixuan on 2017/11/7.
 */
public abstract class CommonSMSServiceImpl implements SMSService{

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
        SendSMSResult result = getClient().sendSMSCode(smsPayload);
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
        ValidSMSResult result = getClient().sendValidSMSCode(msgId,code);
        return result.getIsValid();
    }
}
