package cn.medcn.common.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.device.TagAliasResult;

import java.util.Map;
import java.util.Set;

/**
 * Created by lixuan on 2017/11/7.
 */
public interface PushService {

    String ALIAS_PREFIX = "jingxin_jpush_";


    JPushClient getClient();

    /**
     * 推送给指定用户
     * @param alias 别名
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasParam 扩展字段
     * @return 0推送失败，1推送成功
     */
    int sendToAlias(String alias, String notification_title, String msg_title, String msg_content, Map<String,String> extrasParam) ;

    int sendChangeMessage(Object userId, String msgType, Object param);

    int sendMessageToAlias(String alias, Map<String,String> extrasParam);

    /**
     * 清除唯一码对应的别名和tags
     * @param registrationId
     */
    void clearRegistrationIdInfo(String registrationId) throws APIConnectionException, APIRequestException;

    /**
     * 给registrationId绑定用户信息
     * @param registrationId
     * @param alias
     * @param tages
     */
    void bindAliasAndTags(String registrationId, String alias, Set<String> tages) throws APIConnectionException, APIRequestException;

    TagAliasResult findTagAliasResultByRegistrationId(String registrationId) throws APIConnectionException, APIRequestException;

    AliasDeviceListResult findAliasDeviceResultByAlias(String alias) throws APIConnectionException, APIRequestException;

    /**
     * 根据用户ID生成别名
     * @param userId
     * @return
     */
    String generateAlias(Object userId);


    void cleanJpushByAlias(String alias) throws APIConnectionException, APIRequestException;

}
