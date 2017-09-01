package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/6/7.
 */
@Data
@NoArgsConstructor
public class JpushMessageDTO implements Serializable{
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
    /**是否已读*/
    private Integer read;

    private Integer state;
    /**会议名称*/
    private String meetName;
    /**接收者姓名*/
    private String receiveName;
}
