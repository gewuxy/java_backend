package cn.medcn.weixin.service;

import cn.medcn.weixin.dto.TemplateMessageDTO;

import java.util.Date;
import java.util.Map;

/**
 * Created by lixuan on 2017/7/18.
 */
public interface WXMessageService {

    String UNIT_NAME_KEY = "@unitName";

    String BIND_URL_KEY = "@bindURL";

    String DEFAULT_REPLY_ID = "default_reply";

    String SCENE_REPLY_ID = "scene_reply";

    String REPLY_CONTENT_KEY = "content";

    String AUTO_REPLY_XML_FILE_NAME = "auto_reply.xml";

    String TEMPLATE_MESSAGE_QUEUE_KEY = "wx_message_queue";

    String autoReply(String serverName, String openid, String sceneId, String pubUserName, boolean bindStatus);

    /**
     * 构建模板消息DTO对象
     * @param message 模板消息枚举类型
     * @param openid 用户OPENID
     * @param url 消息点击跳转地址
     * @param remark 消息附加内容
     * @param values 消息属性 如 会议名称 会议单位等
     * @return
     */
    TemplateMessageDTO build(TEMPLATE_MESSAGE message, String openid, String url, String remark, String...values);

    /**
     * 发送模板消息
     * @param message 模板消息枚举类型
     * @param openid 用户OPENID
     * @param url 消息点击跳转地址
     * @param remark 消息附加内容
     * @param values 消息属性 如 会议名称 会议单位等
     */
    void send(TEMPLATE_MESSAGE message, String openid, String url, String remark, String...values);

    void sendByMeetId(TEMPLATE_MESSAGE message, String openid, String meetId, String remark, String...values);

    void send(TemplateMessageDTO messageDTO);

    String formatMessageDate(Date date);

    String passiveResponse(Map<String,String> data);

    String menuReply(Map<String, String> data);

    enum TEMPLATE_MESSAGE{
        meet("您有一场会议即将开始！", "LltVhBQJT99pyRBwMXSFXFQXzcXWJZ4cQR7lPNPigT4"),
        exam("您有一场考试即将开始！", "PyCj0CUBtXT40TTNRWkq6L1Wx60U8vRmnGaipIQY4LI"),
        survey("您有一场调查问卷即将开始！", "");

        public String first;

        public String messageId;

        TEMPLATE_MESSAGE(String first, String messageId){
            this.first = first;
            this.messageId = messageId;
        }
    }
}
