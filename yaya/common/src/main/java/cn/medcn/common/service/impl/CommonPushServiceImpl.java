package cn.medcn.common.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.medcn.common.service.PushService;
import cn.medcn.common.utils.MD5Utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lixuan on 2017/11/7.
 */
public abstract class CommonPushServiceImpl implements PushService {


    /**
     * 推送给指定用户
     *
     * @param alias              别名
     * @param notification_title 通知内容标题
     * @param msg_title          消息内容标题
     * @param msg_content        消息内容
     * @param extrasParam        扩展字段
     * @return 0推送失败，1推送成功
     */
    @Override
    public int sendToAlias(String alias, String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
        PushPayload pushPayload = buildPushObject_all_registrationId_alertWithTitle(alias, notification_title, msg_title, msg_content, extrasParam);
        return doSend(pushPayload);
    }

    protected int doSend(PushPayload pushPayload){
        PushResult pushResult = null;
        try {
            pushResult = getClient().sendPush(pushPayload);
            if (pushResult.getResponseCode() == 200) {
                return 1;
            } else {
                return 0;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int sendMessageToAlias(String alias, Map<String, String> extrasParam) {
        PushPayload pushPayload = buildPushObject_all_message(alias, extrasParam);
        return doSend(pushPayload);
    }

    /**
     * 清除唯一码对应的别名和tags
     *
     * @param registrationId
     */
    @Override
    public void clearRegistrationIdInfo(String registrationId) throws APIConnectionException, APIRequestException {
        getClient().updateDeviceTagAlias(registrationId, true, true);
    }

    /**
     * 给registrationId绑定用户信息
     *
     * @param registrationId
     * @param alias
     * @param tags
     */
    @Override
    public void bindAliasAndTags(String registrationId, String alias, Set<String> tags) throws APIConnectionException, APIRequestException {
        AliasDeviceListResult result = findAliasDeviceResultByAlias(alias);
        List<String> registrationIds = result.registration_ids;
        boolean existedAlias = false;
        if (registrationIds.size() > 1) {//有重复别名的时候
            for (String regId : registrationIds) {
                if (regId.equals(registrationId)) {
                    existedAlias = true;
                } else {
                    clearRegistrationIdInfo(regId);
                }
            }
        }
        if (!existedAlias) {
            getClient().updateDeviceTagAlias(registrationId, alias, tags, null);
        }
    }

    @Override
    public TagAliasResult findTagAliasResultByRegistrationId(String registrationId) throws APIConnectionException, APIRequestException {
        TagAliasResult result = getClient().getDeviceTagAlias(registrationId);
        return result;
    }

    @Override
    public AliasDeviceListResult findAliasDeviceResultByAlias(String alias) throws APIConnectionException, APIRequestException {
        AliasDeviceListResult result = getClient().getAliasDeviceList(alias, "android,ios,winphone");
        return result;
    }


    /**
     * 根据用户ID生成别名
     *
     * @param userId
     * @return
     */
    @Override
    public String generateAlias(Object userId) {
        return MD5Utils.MD5Encode(ALIAS_PREFIX + userId);
    }

    @Override
    public void cleanJpushByAlias(String alias) throws APIConnectionException, APIRequestException {
        AliasDeviceListResult result = findAliasDeviceResultByAlias(alias);
        if (result.registration_ids != null && result.registration_ids.size() > 0) {
            for (String regId : result.registration_ids) {
                clearRegistrationIdInfo(regId);
            }
        }
    }


    private static PushPayload buildPushObject_all_registrationId_alertWithTitle(String alias, String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();

        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert("")
                                .setTitle(msg_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extrasParam)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extrasParam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                .setContentAvailable(false)
                                .build()
                        )
                        .build()
                )
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtras(extrasParam)
                        .build())
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
                        .build())

                .build();

    }

    /**
     * build自定义消息
     *
     * @param alias
     * @param extrasParam
     * @return
     */
    protected static PushPayload buildPushObject_all_message(String alias, Map<String, String> extrasParam) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setMsgContent("")
                        .setTitle("")
                        .addExtras(extrasParam)
                        .build())
                .build();
    }
}
