package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/7/31.
 */
@Data
@NoArgsConstructor
@Entity
@Table (name = "t_meet_reward_history")
public class MeetRewardHistory implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    // 用户ID
    private Integer userId;
    // 会议ID
    private String meetId;
    // 奖励数值
    private Integer rewardPoint;
    // 奖励类型 1：象数 2：学分
    private Integer rewardType;
    // 奖励时间
    private Date rewardTime;

    public enum rewardLabel{
        XS(1,"象数"),
        CREDIT(2,"学分");

        private Integer rewardType;
        private String label;

        public Integer getRewardType() {
            return rewardType;
        }

        public String getLabel() {
            return label;
        }

        rewardLabel(Integer rewardType,String label){
            this.rewardType = rewardType;
            this.label = label;
        }
    }

    public String getRewardLable(){
        return MeetRewardHistory.rewardLabel.values()[this.rewardType].getLabel();
    }

}
