package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/8/9.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_notify")
public class MeetNotify implements Serializable{

    public static final String DEFAULT_MEET_NOTIFY_CONTENT = "请准时参加，不要错过哦~";

    @Id
    protected Integer id;
    //会议ID
    protected String meetId;
    //通知状态 是否已经通知 0表示未通知 1表示已通知 2表示已取消
    protected Integer state;
    //通知时间戳
    protected Long notifyTime;
    //目标用户ID
    protected Integer targetId;
    //通知类型 0表示会议提醒 1表示考试提醒 2表示问卷提醒
    protected Integer notifyType;
    //通知内容
    protected String content;

    public String getNotifyTypeLabel(){
        if(state == null){
            return NotifyType.values()[0].title;
        }
        return NotifyType.values()[state].title;
    }


    public enum NotifyState{
        initial("未发送"),
        notified("已发送"),
        cancel("已取消");

        public String state;

        NotifyState(String state){
            this.state = state;
        }
    }

    public enum NotifyType{
        meet("会议提醒"),
        exam("考试提醒"),
        survey("问卷提醒");

        public String title;

        NotifyType(String title){
            this.title = title;
        }
    }

}
