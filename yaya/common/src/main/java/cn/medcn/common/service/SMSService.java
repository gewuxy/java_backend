package cn.medcn.common.service;

import cn.jsms.api.common.SMSClient;

/**
 * Created by lixuan on 2017/11/7.
 */
public interface SMSService {

    SMSClient getClient();

    /**
     * 利用默认短信模板发送短信验证码
     * @param mobile
     * @param msgTmpId
     * @return
     */
    String send(String mobile, Integer msgTmpId) throws Exception;



    /**
     * 检测验证码是否合法
     * @param msgId
     * @param code
     * @return
     */
    boolean verify(String msgId, String code) throws Exception;
}
