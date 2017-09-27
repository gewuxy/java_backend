package cn.medcn.meet.dto;

import cn.medcn.meet.model.MeetMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/25.
 */
@Data
@NoArgsConstructor
public class MeetMessageDTO implements Serializable{

    private Integer id;

    private String message;

    private String meetId;

    private Integer senderId;

    private String sender;

    private Date sendTime;
    //0表示普通消息 1表示直播 2表示同步
    private Integer msgType;

    private String headimg;

    private String meetName;


    public enum MessageType{
        normal,//普通消息
        live,//直播指令
        sync;//同步指令
    }

    public static MeetMessage buildToMessage(MeetMessageDTO dto){
        MeetMessage message = new MeetMessage();
        message.setMsgType(dto.msgType);
        message.setSenderId(dto.senderId);
        message.setMeetId(dto.getMeetId());
        message.setSendTime(new Date());
        message.setMessage(dto.getMessage());
        return message;
    }

}

