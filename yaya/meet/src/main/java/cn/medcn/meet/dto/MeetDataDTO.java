package cn.medcn.meet.dto;

import cn.medcn.meet.model.Meet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/5/16.
 */
@Data
@NoArgsConstructor
public class MeetDataDTO implements Serializable{

    private String id;

    // 会议名称
    private String meetName;

    // 会议状态
    private Integer state;

    // 参会人数
   // private Integer attendCount;

    // 发布时间
    private Date publishTime;

    // 创建时间
    private Date createTime;

    // 会议模块名称
    private String moduleName;


    private String stateName;

    public String getStateName(){
        return Meet.MeetType.values()[state].getLabel();
    }



}
