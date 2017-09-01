package cn.medcn.meet.dto;

import cn.medcn.meet.model.Meet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/22.
 */
@Data
@NoArgsConstructor
public class MeetListInfoDTO implements Serializable {

    private String id;

    private String meetName;

    private Date createTime;

    private Date publishTime;

    private Date startTime;

    private Short state;

    private String stateName;

    private Integer attendCount;

    public String getStateName(){
        return Meet.MeetType.values()[this.state].getLabel();
    }
}
