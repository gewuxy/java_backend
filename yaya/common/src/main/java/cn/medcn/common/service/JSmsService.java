package cn.medcn.common.service;

/**
 * Created by lixuan on 2017/3/30.
 */
public interface JSmsService {

    public static final Integer DEFAULT_TEMPLATE_ID = 40198;

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
