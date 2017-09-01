package cn.medcn.meet.dto;

import cn.medcn.meet.model.MeetAudio;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
public class MeetAudioDTO implements Serializable {

    private Integer id;

    private String meetId;

    private Integer moduleId;

    private Integer courseId;

    private AudioCourseDTO course;

    public static MeetAudioDTO build(MeetAudio meetAudio){
        MeetAudioDTO dto = new MeetAudioDTO();
        if(meetAudio!=null){
            dto.setMeetId(meetAudio.getMeetId());
            dto.setModuleId(meetAudio.getModuleId());
            dto.setId(meetAudio.getId());
            dto.setCourseId(meetAudio.getCourseId());
            if(meetAudio.getCourse()!=null){
                AudioCourseDTO courseDTO = AudioCourseDTO.build(meetAudio.getCourse());
                dto.setCourse(courseDTO);
            }
        }
        return dto;
    }
}
