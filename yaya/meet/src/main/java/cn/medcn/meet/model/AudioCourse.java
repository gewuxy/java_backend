package cn.medcn.meet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_audio_course")
public class AudioCourse implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String title;
    /**资源类型*/
    private String category;
    /**是否发布*/
    private Boolean published;

    private Date createTime;
    /**所有者ID*/
    private Integer owner;
    /**是否共享*/
    private Boolean shared;
    /**共享类型 0表示免费 1表示收费 2表示奖励*/
    private Integer shareType;
    /**转载需要的象数*/
    private Integer credits;
    /**原始资料ID 转载来的资源才有的属性 非转载资源为0*/
    private Integer primitiveId;

    protected Integer playType;
    //csp投稿人ID
    protected String cspUserId;
    //课件来源 0或者空为YaYa 1表示csp
    protected Integer sourceType;
    //课件描述
    protected String info;
    //新增的csp课件分类ID
    protected Integer categoryId;
    // 是否删除(csp可以删除会议)
    protected Boolean deleted;
    //会议是否被锁定
    protected Boolean locked;
    //是否是引导课件
    protected Boolean guide;
    //课件密码
    protected String password;

    protected Boolean starRateFlag;

    @Transient
    @JsonIgnore
    private String userName;

    @Transient
    private List<AudioCourseDetail> details;

    //直播开始时间
    @Transient
    private Date startTime;


    //该会议的持有者id
    @Transient
    private String userId;


    public enum PlayType{
        normal(0),//录播
        live_ppt(1),//ppt直播
        live_video(2);//视频直播

        private Integer type;

        public Integer getType(){
            return type;
        }

        PlayType(Integer type){
            this.type = type;
        }
    }


    public enum SourceType{
        YaYa,
        csp;
    }
}
