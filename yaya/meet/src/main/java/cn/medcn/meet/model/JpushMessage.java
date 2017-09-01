package cn.medcn.meet.model;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_jpush_message")
public class JpushMessage implements Serializable{

    public static final String JPUSH_MESSAGE_SENDTIME_KEY ="sendTime";

    public static final String JPUSH_MESSAGE_MSG_TYPE_KEY = "msgType";

    public static final String JPUSH_MESSAGE_MEET_ID_KEY = "meetId";

    public static final String JPUSH_MESSAGE_SENDER_KEY = "senderName";

    public static final String JPUSH_MESSAGE_TITLE_KEY = "title";

    public static final String JPUSH_MESSAGE_CONTENT_KEY = "content";

    public static final String JPUSH_MESSAGE_MEET_NAME_KEY = "meetName";

    public static final String JPUSH_MESSAGE_XS_CHANGE_RESULT_KEY = "result";

    @Id
    private Long id;
    /**消息标题*/
    private String title;
    /**消息内容*/
    private String content;
    /**发送时间*/
    private Date sendTime;
    /**发送者ID*/
    private Integer sender;
    /**接受者ID*/
    private Integer receiver;
    /**0表示普通消息 */
    private Integer msgType;
    /**会议ID*/
    private String meetId;
    /**发送结果 0表示失败 1表示成功*/
    private Integer state;
    /**更改通知的情况下需要传递更改之后的值*/
    protected String result;

    /**消息推送的平台*/
    protected Integer flat;


    public enum MessageType{
        generalNotify("普通消息", true),
        meetNotify("会议消息", true),
        creditChangedNotify("象数更改", false);

        public String label;

        public boolean alert;

        MessageType(String label, boolean alert){
            this.label = label;
            this.alert = alert;
        }
    }

    /**
     * 发送结果状态
     */
    public enum SendState{
        fail("失败"),
        success("成功");

        public String state;

        SendState(String state){
            this.state = state;
        }
    }

    /**
     * 消息发送平台
     */
    public enum SendFlat{
        jPush("APP"),
        weChat("微信"),
        all("全部");

        public String type;

        SendFlat(String type){
            this.type = type;
        }
    }

    @Transient
    private String senderName;

    @Transient
    private Integer groupId;

    @Transient
    private String meetName;

    @Transient
    protected Integer notifyType;

    @Transient
    protected Integer sendType;

    /**
     * 标识消息是单发还是群发
     */
    public enum SendType{
        single,
        group;
    }


    public static Map<String, String> generateExtras(JpushMessage jpushMessage){
        Map<String, String> map = Maps.newHashMap();
        if(jpushMessage != null){
            map.put(JPUSH_MESSAGE_SENDER_KEY, jpushMessage.getSenderName());
            map.put(JPUSH_MESSAGE_SENDTIME_KEY, System.currentTimeMillis()+"");
            map.put(JPUSH_MESSAGE_MEET_ID_KEY, jpushMessage.getMeetId() == null?"":jpushMessage.getMeetId());
            map.put(JPUSH_MESSAGE_MSG_TYPE_KEY, jpushMessage.getMsgType()+"");
            map.put(JPUSH_MESSAGE_TITLE_KEY, jpushMessage.getTitle() == null?"":jpushMessage.getTitle());
            map.put(JPUSH_MESSAGE_CONTENT_KEY, jpushMessage.getContent());
            map.put(JPUSH_MESSAGE_MEET_NAME_KEY, jpushMessage.getMeetName() == null?"":jpushMessage.getMeetName());
        }
        return map;
    }

    public static Map<String, String> generateCreditsChangeExtras(JpushMessage jpushMessage){
        Map<String, String> map = Maps.newHashMap();
        if(jpushMessage != null){
            map.put(JPUSH_MESSAGE_SENDTIME_KEY, System.currentTimeMillis()+"");
            map.put(JPUSH_MESSAGE_MSG_TYPE_KEY, jpushMessage.getMsgType()+"");
            map.put(JPUSH_MESSAGE_CONTENT_KEY, jpushMessage.getContent());
            map.put(JPUSH_MESSAGE_XS_CHANGE_RESULT_KEY, jpushMessage.getResult());
        }
        return map;
    }
}
