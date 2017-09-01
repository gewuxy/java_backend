package cn.medcn.meet.dto;

import cn.medcn.meet.model.MeetPosition;
import cn.medcn.meet.model.MeetSign;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/17.
 */
@Data
@NoArgsConstructor
public class PositionDTO implements Serializable {

    private Integer id;

    private String meetId;

    private Integer moduleId;

    private String positionName;
    /**签到结束时间*/
    private Date endTime;

    private Date signTime;

    private Boolean finished;


    public static PositionDTO buildFromPostion(MeetPosition position){
        PositionDTO dto = new PositionDTO();
        if(position != null){
            dto.setId(position.getId());
            dto.setMeetId(position.getMeetId());
            dto.setModuleId(position.getModuleId());
            dto.setPositionName(position.getPositionName());
            dto.setEndTime(position.getEndTime());
            dto.setFinished(false);
        }
        return dto;
    }

    public static PositionDTO buildFromSign(MeetSign sign){
        PositionDTO dto = new PositionDTO();
        if(sign != null){
            dto.setId(sign.getPositionId());
            dto.setMeetId(sign.getMeetId());
            dto.setSignTime(sign.getSignTime());
            dto.setFinished(true);
        }
        return dto;
    }
}
