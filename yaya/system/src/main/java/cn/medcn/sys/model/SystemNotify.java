package cn.medcn.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** csp 通知
 * Created by LiuLP on 2017/5/2.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_system_notify")
public class SystemNotify implements Serializable {

    @Id
    private String id;

    /**公告标题*/
    private String title;

    /**通知内容*/
    private String content;

    /**通知类型0表示针对国内用户  2表示针对海外用户 1表示针对个人*/
    private Integer notifyType;

    /**接收者id**/
    private String acceptId;

    //发送时间
    private Date sendTime;

    //发送者id
    private String senderId;

    //发送者姓名
    private String senderName;

    //是否已读
    private Boolean isRead;

    @Transient
    private String sendTimeStr;

    @Transient
    private String nickName;

    public static void tranEnglishTime(List<SystemNotify> list){
        DateFormat format = new SimpleDateFormat("MMM d", Locale.ENGLISH);
        for(SystemNotify notify:list){
            notify.setSendTimeStr(format.format(notify.getSendTime()));
        }
    }


}
