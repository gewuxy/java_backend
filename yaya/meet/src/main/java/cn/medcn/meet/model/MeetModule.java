package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_module")
public class MeetModule implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    /**模块名称*/
    private String moduleName;
    /**功能ID*/
    private Integer functionId;
    /**会议ID*/
    private String meetId;
    /**是否是主模块*/
    private Boolean mainFlag;
    /**模块是否可用*/
    private Boolean active;

    public enum ModuleFunction{
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

        public static ModuleFunction getFunctionById(Integer funId){
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

        ModuleFunction(Integer funId, String funName){
            this.funId = funId;
            this.funName = funName;
        }
    }
}
