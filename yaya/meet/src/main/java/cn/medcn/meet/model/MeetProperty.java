package cn.medcn.meet.model;

import cn.medcn.common.utils.CheckUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_prop")
public class MeetProperty implements Serializable{

    public static final String ARRAY_SEPARATOR = ",";

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
    /**指定参与组的数组*/
    @Transient
    protected Integer[] groupIds;
    /**指定参与组的id以逗号分隔的字符串*/
    protected String groups;
    /**参与人员限制类型 0表示不限制 1表示按地域科室 2表示按用户群组*/
    private Integer memberLimitType;
    /**指定科室参与*/
    private String specifyDepart;


    public String getGroups(){
        if (groupIds == null) {
            return groups;
        } else {
            StringBuffer buffer = new StringBuffer();
            for (int index = 0 ; index < groupIds.length; index ++) {
                buffer.append(index == groupIds.length - 1 ? "" : ARRAY_SEPARATOR);
                buffer.append(groupIds[index]);
            }
            return buffer.toString();
        }
    }

    public Integer[] getGroupIds(){
        if (CheckUtils.isEmpty(groups)) {
            return null;
        } else {
            String[] array = groups.split(ARRAY_SEPARATOR);
            Integer[] groupArray = new Integer[array.length];
            for (int index = 0; index < array.length; index ++) {
                groupArray[index] = Integer.valueOf(array[index]);
            }
            return groupArray;
        }
    }

    public enum MemberLimit{
        NOT_LIMIT(0,"没有限制"),
        LIMIT_BY_LOCATION(1,"根据地域科室限制"),
        LIMIT_BY_GROUP(2,"根据群组限制");

        private Integer type;

        private String desc;

        public Integer getType(){
            return type;
        }

        public String getDesc(){
            return desc;
        }

        MemberLimit(Integer type,String desc){
            this.type = type;
            this.desc = desc;
        }
    }
}
