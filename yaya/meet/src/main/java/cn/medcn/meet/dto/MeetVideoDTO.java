package cn.medcn.meet.dto;

import cn.medcn.meet.model.MeetVideo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/17.
 */
@Data
@NoArgsConstructor
public class MeetVideoDTO implements Serializable {

    private Integer id;

    private String meetId;

    private Integer moduleId;

    private VideoCourseDTO course;

    private Integer usedtime;

    public static MeetVideoDTO build(MeetVideo video){
        MeetVideoDTO dto = new MeetVideoDTO();
        if(video != null){
            dto.setId(video.getId());
            dto.setMeetId(video.getMeetId());
            dto.setModuleId(video.getModuleId());
            dto.setCourse(VideoCourseDTO.build(video.getCourse()));
        }
        return dto;
    }
}
