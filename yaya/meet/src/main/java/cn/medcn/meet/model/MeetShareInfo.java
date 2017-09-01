package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/5/16.
 */
@Data
@NoArgsConstructor
@Entity
@Table (name = "t_meet_share_info")
public class MeetShareInfo implements Serializable{

    @Id
    private Integer id;

    // 会议id
    private String meetId;

    // 分享者id
    private Integer ownerId;

    // 是否分享
    private Boolean shareFlag;

    // 分享时间
    private Date shareTime;

    // 分享方式 0表示免费 1表示获取费用 2表示奖励费用
    private Integer shareType;

    // 奖励人数限制
    private Integer awardLimit;

    // 花费 奖励则为正 扣费则为负
    private Integer cost;

}
