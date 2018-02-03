package cn.medcn.meet.model;

import cn.medcn.common.utils.CalendarUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/20.
 * 会议实体类
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet")
public class Meet implements Serializable{

    @Id
    private String id;
    /**会议名称*/
    private String meetName;
    /**会议拥有者*/
    private Integer ownerId;
    /**会议拥有者登录名*/
    private String owner;
    /**会议拥有者真实姓名 如公众号名称*/
    private String organizer;
    /**会议科室*/
    private String meetType;
    /**会议状态0表示草稿 1表示未开始 2表示进行中 3表示已结束 4表示已撤销/已删除 5表示未发布（从草稿复制过来） 6表示已关闭*/
    private Short state;
    /**会议简介*/
    private String introduction;

    /**是否共享*/
    private Boolean shared;
    /**会议原生ID 被转载的会议ID*/
    private String primitiveId;
    /**历史遗留数据ID*/
    private Integer oldId;
    /**创建时间*/
    private Date createTime;
    /**发布时间*/
    private Date publishTime;
    /**会议封面*/
    private String coverUrl;
    /**是否是推荐会议*/
    private Boolean tuijian;

    private Integer lecturerId;

    /**会议主讲者*/
    @Transient
    private Lecturer lecturer;

    @Transient
    private MeetProperty meetProperty;

    @Transient
    private MeetSetting meetSetting;

    @Transient
    private String stateName;

    @Transient
    private String duration;

    @Transient
    private String folderId;

    //appUser id
    @Transient
    private Integer userId;

    //appUser nickName
    @Transient
    private String nickName;

    @Transient
    private String lecturerName;

    @Transient
    private String headimg;

    @Transient
    private String title;

    @Transient
    private String hospital;

    @Transient
    private String depart;

    @Transient
    private String imgPath;

    public String getDuration(){
        if(meetProperty != null && meetProperty.getEndTime() != null && meetProperty.getStartTime() != null){
            long time = (meetProperty.getEndTime().getTime() - meetProperty.getStartTime().getTime())/1000;
            return CalendarUtils.formatTime(time);
        }
        return "0小时";
    }

    public String getStateName(){
        return Meet.MeetType.values()[this.state].getLabel();
    }

    public enum MeetType{
        DRAFT(Short.valueOf("0"), "草稿"),
        NOT_START(Short.valueOf("1"), "未开始"),
        IN_USE(Short.valueOf("2"), "进行中"),
        OVER(Short.valueOf("3"), "已结束"),
        CANCEL(Short.valueOf("4"), "已撤销"),//已删除
        UN_PUBLISHED(Short.valueOf("5"), "未发布"),
        CLOSED(Short.valueOf("6"), "已关闭");

        private Short state;

        private String label;

        public Short getState() {
            return state;
        }

        public String getLabel() {
            return label;
        }

        MeetType(Short state, String label){
            this.state = state;
            this.label = label;
        }

    }

}
