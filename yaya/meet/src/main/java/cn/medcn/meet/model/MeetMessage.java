package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/25.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_message")
public class MeetMessage implements Serializable {

    @Id
    private Long id;

    private String meetId;

    private String message;

    private Integer senderId;

    private Date sendTime;
    /**消息类型0表示文字消息 1表示图片消息 2表示音频消息*/
    private Integer msgType;

    @Transient
    private String meetName;
}
