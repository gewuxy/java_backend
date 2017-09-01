package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/7/31.
 */
@Data
@NoArgsConstructor
@Entity
@Table (name = "t_meet_learning_record")
public class MeetLearningRecord implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id ;
    // 功能ID 1:ppt 2:视频 3:考试 4:问卷 5:签到
    private Integer functionId	;
    // 会议ID
    private String meetId ;
    // 用户ID
    private Integer userId ;
    // 学习进度百分比
    private Integer completeProgress;
    // 学习用时(ppt内容和视频学习)；提交时间（考试、问卷）、签到时间
    private Long usedTime;

    public enum functionName{
        PPT(1,"微课"),
        VIDEO(2, "视频"),
        EXAM(3,"考试"),
        SURVEY(4, "问卷"),
        SIGN(5, "签到");

        private Integer funId;

        private String funName;

        public Integer getFunId() {
            return funId;
        }

        public String getFunName() {
            return funName;
        }

        public static functionName getFunctionById(Integer funId){
            switch (funId){
                case 1:
                    return PPT;
                case 2:
                    return VIDEO;
                case 3:
                    return EXAM;
                case 4:
                    return SURVEY;
                case 5:
                    return SIGN;
                default:
                    return null;
            }
        }

        functionName(Integer funId, String funName){
            this.funId = funId;
            this.funName = funName;
        }
    }
}
