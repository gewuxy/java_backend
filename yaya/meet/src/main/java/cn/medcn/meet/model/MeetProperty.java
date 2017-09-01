package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_prop")
public class MeetProperty implements Serializable{

    @Id
    private Integer id;
    /**会议ID*/
    private String meetId;

    private Date startTime;

    private Date endTime;
    /**参与人数限制*/
    private Integer attendLimit;
    /**联系人*/
    private String linkman;
    /**需求或奖励学分 0表示需求 1表示奖励*/
    private Integer eduCredits;
    /**需求或者奖励学分值*/
    private Integer xsCredits;
    /**奖励人数限制*/
    private Integer awardLimit;
    /**是否禁言会议*/
    private Boolean talkForbid;
    /**指定省份参与*/
    private String specifyProvince;
    /**指定城市参与*/
    private String specifyCity;
    /**指定组参与*/
    private Integer groupId;
    /**参与人员限制类型 0表示不限制 1表示按地域科室 2表示按用户群组*/
    private Integer memberLimitType;
    /**指定科室参与*/
    private String specifyDepart;
}
