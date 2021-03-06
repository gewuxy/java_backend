import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.service.PushService;
import cn.medcn.meet.model.JpushMessage;
import cn.medcn.meet.model.MeetNotify;
import cn.medcn.meet.service.JpushMessageService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by lixuan on 2017/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class JpushTest {

    @Autowired
    private JPushService jPushService;

    @Autowired
    protected JpushMessageService jpushMessageService;

    @Autowired
    private PushService cspPushService;

    @Test
    public void testSend(){
        JpushMessage jpushMessage = new JpushMessage();
        jpushMessage.setMsgType(1);
        jpushMessage.setSenderName("敬信单位号");
        jpushMessage.setMeetId("17070414412888880003");
        jpushMessage.setContent("测试推送消息内容");
        jpushMessage.setTitle("测试推送消息标题");
        jpushMessage.setMeetName("123123123");

       jPushService.sendToAlias(jPushService.generateAlias(288733), "test", "", "hahaha", JpushMessage.generateExtras(jpushMessage));
    }


//    @Test
//    public void testFindAlias() throws APIConnectionException, APIRequestException {
//        TagAliasResult result = jPushService.findTagAliasResultByRegistionId("13165ffa4e399a406bc");
//        System.out.println(result.alias);
//
//    }


    @Test
    public void testFindTageAlis() throws APIConnectionException, APIRequestException {
        //String username = "tongyipeng@medcn.cn"''
        String username = "wucaixiang@medcn.cn";
        AliasDeviceListResult result = jPushService.findAliasDeviceResultByAlias(jPushService.generateAlias(64));
        System.out.println("查询RegId ....");
        for(String registionId : result.registration_ids){
            System.out.println("zhenghang关联的ID="+registionId);
        }
    }

    @Test
    public void testTestAlias() throws APIConnectionException, APIRequestException {
        //160a3797c8028f125b6
        String alias = jPushService.generateAlias("zhenghang@medcn.cn");
        String registionId = "13165ffa4e3e702a127";
        jPushService.bindAliasAndTags(registionId, alias, null);
    }


    @Test
    public void testCleanAlias() throws APIConnectionException, APIRequestException {
        String username = "wucaixiang@medcn.cn";
        jPushService.cleanJpushByAlias(jPushService.generateAlias(username));
        AliasDeviceListResult result = jPushService.findAliasDeviceResultByAlias(jPushService.generateAlias(1200059));
        for(String registionId : result.registration_ids){
            System.out.println("zhenghang关联的ID="+registionId);
        }
    }


    @Test
    public void testSendMultiMessage(){
        JpushMessage message = new JpushMessage();
        message.setMsgType(JpushMessage.MessageType.meetNotify.ordinal());
        message.setReceiver(1200011);
        message.setNotifyType(MeetNotify.NotifyType.meet.ordinal());
        message.setContent("请按时参加会议,切勿错过哦~");
        message.setFlat(JpushMessage.SendFlat.all.ordinal());
        message.setMeetId("17062316283826530327");
        message.setSendType(JpushMessage.SendType.single.ordinal());
        message.setSender(625207);
        message.setTitle(MeetNotify.NotifyType.meet.title);
        jpushMessageService.appendToQueue(message);
    }


    @Test
    public void testSendMultiGroupMessage(){
        JpushMessage message = new JpushMessage();
        message.setGroupId(356);
        message.setMsgType(JpushMessage.MessageType.meetNotify.ordinal());
        message.setNotifyType(MeetNotify.NotifyType.meet.ordinal());
        message.setContent("请按时参加会议,切勿错过哦~");
        message.setFlat(JpushMessage.SendFlat.all.ordinal());
        message.setMeetId("17062316283826530327");
        message.setSendType(JpushMessage.SendType.group.ordinal());
        message.setSender(237746);
        message.setTitle(MeetNotify.NotifyType.meet.title);
        jpushMessageService.appendToQueue(message);
    }

    @Test
    public void testCSPSend(){
        Map<String, String> extraMap = Maps.newHashMap();
        extraMap.put("sendTime", System.currentTimeMillis() + "");
        extraMap.put("msgType", "3");
        extraMap.put("result", "test@qq.com");
        int result = cspPushService.sendMessageToAlias("13165ffa4e0d71f3d85",extraMap);
        System.out.println(result);

    }
}
