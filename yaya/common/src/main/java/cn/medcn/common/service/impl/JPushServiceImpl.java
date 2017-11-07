package cn.medcn.common.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
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
import cn.medcn.common.Constants;
import cn.medcn.common.service.JPushClientCreator;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.TailorMadeUtils;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lixuan on 2017/4/7.
 */
@Service
public class JPushServiceImpl implements JPushService {

    private static Log log = LogFactory.getLog(JPushServiceImpl.class);

    private JPushClient getJPushClient() {
        Integer masterId = TailorMadeUtils.get();
        return JPushClientCreator.getInstance(masterId);
    }

    /**
     * 循环发送到所有注册过的客户端
     *
     * @param pushPayload
     * @return
     */
    protected int loopSend(PushPayload pushPayload) {
        int result = 0;
        for (JPushClientCreator creator : JPushClientCreator.values()) {
            try {
                JPushClient client = creator.getClient();
                PushResult pushResult = client.sendPush(pushPayload);
                if (pushResult.getResponseCode() == 200) {
                    result = 1;
                }
            } catch (APIConnectionException e) {
                e.printStackTrace();
            } catch (APIRequestException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 推送给指定用户,后台根据alias就能推送给指定用户
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
        LogUtils.debug(log, pushPayload.toString());
//            PushResult pushResult = getJPushClient().sendPush(pushPayload);
//            if (pushResult.getResponseCode() == 200) {
//                result = 1;
//            }
        return loopSend(pushPayload);
    }


    @Override
    public int sendMessageToAlias(String alias, Map<String, String> extrasParam) {
        PushPayload pushPayload = buildPushObject_all_message(alias, extrasParam);
        LogUtils.debug(log, pushPayload.toString());
        return loopSend(pushPayload);
    }

    /**
     * 发送给所有安卓用户
     *
     * @param notification_title 通知内容标题
     * @param msg_title          消息内容标题
     * @param msg_content        消息内容
     * @param extrasParam        扩展字段
     * @return 0推送失败，1推送成功
     */
    @Override
    public int sendToAllAndroid(String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
        PushPayload pushPayload = buildPushObject_android_all_alertWithTitle(notification_title, msg_title, msg_content, extrasParam);
        LogUtils.debug(log, pushPayload.toString());
        return loopSend(pushPayload);
    }

    /**
     * 发送给所有IOS用户
     *
     * @param notification_title 通知内容标题
     * @param msg_title          消息内容标题
     * @param msg_content        消息内容
     * @param extrasParam        扩展字段
     * @return 0推送失败，1推送成功
     */
    @Override
    public int sendToAllIos(String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
        PushPayload pushPayload = buildPushObject_ios_all_alertWithTitle(notification_title, msg_title, msg_content, extrasParam);
        LogUtils.debug(log, pushPayload.toString());
        return loopSend(pushPayload);
    }

    /**
     * 发送给所有用户
     *
     * @param notification_title 通知内容标题
     * @param msg_title          消息内容标题
     * @param msg_content        消息内容
     * @param extrasParam        扩展字段
     * @return 0推送失败，1推送成功
     */
    @Override
    public int sendToAll(String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
//        int result = 0;
//        try {
//            PushPayload pushPayload= buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasParam);
//            LogUtils.debug(log,pushPayload.toString());
//            PushResult pushResult=getJPushClient().sendPush(pushPayload);
//            if(pushResult.getResponseCode()==200){
//                result=1;
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return result;
        return 0;
    }


    /**
     * 清除唯一码对应的别名和tags
     *
     * @param registrationId
     */
    @Override
    public void clearRegistrationIdInfo(String registrationId) throws APIConnectionException, APIRequestException {
        getJPushClient().updateDeviceTagAlias(registrationId, true, true);
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
            getJPushClient().updateDeviceTagAlias(registrationId, alias, tags, null);
        }
    }

    @Override
    public TagAliasResult findTagAliasResultByRegistrationId(String registrationId) throws APIConnectionException, APIRequestException {
        TagAliasResult result = getJPushClient().getDeviceTagAlias(registrationId);
        return result;
    }

    @Override
    public AliasDeviceListResult findAliasDeviceResultByAlias(String alias) throws APIConnectionException, APIRequestException {
        AliasDeviceListResult result = getJPushClient().getAliasDeviceList(alias, "android,ios,winphone");
        return result;
    }

    /**
     * 根据用户名生成别名
     *
     * @param username
     * @return
     */
    @Override
    public String generateAlias(String username) {
        return MD5Utils.MD5Encode(username);
    }

    /**
     * 根据用户ID生成别名
     *
     * @param userId
     * @return
     */
    @Override
    public String generateAlias(Object userId) {
        return MD5Utils.MD5Encode(JINGXIN_JPUSH_ALIAS_PREFIX + userId);
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


    @Override
    public int sendCreditsChangeMessage(Integer userId, Integer credits) {
        String alias = generateAlias(userId);
        if (credits == null) {
            credits = 0;
        }
        Map<String, String> extraMap = Maps.newHashMap();
        extraMap.put("msgType", "2");
        extraMap.put("sendTime", System.currentTimeMillis() + "");
        extraMap.put("result", String.valueOf(credits));
        int result = sendMessageToAlias(alias, extraMap);
        return result;
    }


    @Override
    public int sendChangeMessage(Object userId, String msgType, Object param) {
        String alias = generateAlias(userId);
        Map<String, String> extraMap = Maps.newHashMap();
        extraMap.put("sendTime", System.currentTimeMillis() + "");
        extraMap.put("msgType", msgType);
        extraMap.put("result", String.valueOf(param));
        int result = sendMessageToAlias(alias, extraMap);
        return result;
    }

    public static PushPayload buildIosPushPlayLoad(String alias, String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .setAlert(notification_title)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
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
                                // .setContentAvailable(true)

                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
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
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build()
                )
                .build();
    }

    public static PushPayload buildPushObject_android_and_ios(String alias, String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .setAlert(notification_title)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
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
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
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
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build()
                )
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

    private static PushPayload buildPushObject_android_all_alertWithTitle(String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.android())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extrasParam)
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
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
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

    private static PushPayload buildPushObject_ios_all_alertWithTitle(String notification_title, String msg_title, String msg_content, Map<String, String> extrasParam) {
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.ios())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
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
                                // .setContentAvailable(true)

                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
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
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

    public static void main(String[] args) {
        JPushServiceImpl impl = new JPushServiceImpl();
//        if(base.sendToAll("testIos","testIos","this is a ios Dev test","")==1){
//            System.out.println("success");
//        }else {
//            System.out.println("error");
//        }
        //base.sendToRegistrationId("FC56D5F6-F648-4C9A-A300-5A9A1737CF6B", "test", "test", "haha", "");
    }
}
