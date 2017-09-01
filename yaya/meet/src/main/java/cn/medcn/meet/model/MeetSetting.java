package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Liuchangling on 2017/8/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_setting")
public class MeetSetting implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    // 会议ID
    private String meetId;
    // 会议属性设置方式：0支付象数  1赠送象数 2 I类学分 3 II类学分
    private Integer propMode;
    // 奖励或支付的象数值；或奖励的学分数
    private Integer propValue;
    // 奖励人数限制
    private Integer rewardLimit;
    // 属性类型 1象数 2学分
    private Integer propType;

    @Transient
    private Integer requiredXs;   // 是否奖励(1)或支付(0)象数
    @Transient
    private Integer xsCredits;    // 奖励或支付象数值
    @Transient
    private Integer awardLimit;   // 奖励人数限制
    @Transient
    private Integer rewardCredit; // 奖励学分 一类学分(2) 二类学分(3)
    @Transient
    private Integer eduCredits;   // 奖励学分值
    @Transient
    private Integer awardCreditLimit; // 奖励学分人数限制

    public enum modeLabel {
        PAY_XS(0, "支付象数"),
        REWARD_XS(1, "奖励象数"),
        ONE_CREDIT(2, "I类学分"),
        TWO_CREDIT(3, "II类学分");

        private Integer propMode;
        private String label;

        public Integer getPropMode() {
            return propMode;
        }

        public String getLabel() {
            return label;
        }

        modeLabel(Integer propMode, String label) {
            this.propMode = propMode;
            this.label = label;
        }
    }

    public String getModeLabel() {
        return MeetSetting.modeLabel.values()[this.propMode].getLabel();
    }

    public enum propTypeLabel {
        XS(1, "象数"),
        CREDIT(2, "学分");

        private Integer propType;
        private String label;

        public Integer getPropType() {
            return propType;
        }

        public String getLabel() {
            return label;
        }

        propTypeLabel(Integer propType, String label) {
            this.propType = propType;
            this.label = label;
        }
    }

    public static MeetSetting buildMeetSetting(List<MeetSetting> settingList) {
        MeetSetting meetSetting = new MeetSetting();
        for (MeetSetting setting : settingList){
            if (setting.getPropType().intValue() == propTypeLabel.XS.getPropType().intValue()) {
                meetSetting.setRequiredXs(setting.getPropMode());
                meetSetting.setXsCredits(setting.getPropValue());
                meetSetting.setAwardLimit(setting.getRewardLimit());
            } else {
                meetSetting.setRewardCredit(setting.getPropMode());
                meetSetting.setEduCredits(setting.getPropValue());
                meetSetting.setAwardCreditLimit(setting.getRewardLimit());
            }
        }
        return meetSetting;
    }
}
