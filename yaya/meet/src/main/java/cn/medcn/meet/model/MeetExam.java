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
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_exam")
public class MeetExam implements Serializable{
    @Id
    private Integer id;
    /**会议ID*/
    private String meetId;
    /**模块ID*/
    private Integer moduleId;
    /**用时*/
    private Integer usetime;
    /**试卷ID*/
    private Integer paperId;
    /**试题乱序*/
    private Boolean questionReorder;
    @Transient
    private ExamPaper examPaper;

    private Date startTime;

    private Date endTime;
    /**时间限制类型 0表示用时 1表示时间段*/
    private Integer timeLimitType;
    /**及格分数*/
    private Integer passScore;
    /**可重考次数*/
    private Integer resitTimes;
}
