package cn.medcn.meet.dto;

import cn.medcn.meet.model.Live;
import cn.medcn.meet.model.MeetAudio;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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

    //评论数
    private Integer count;

    //直播音频webSocket连接地址
    private String socketUrl;


    protected Integer liveState;//直播状态

    protected Date serverTime;//传递给前端的服务器当前时间


    public static MeetAudioDTO build(MeetAudio meetAudio){
        MeetAudioDTO dto = new MeetAudioDTO();
        if(meetAudio!=null){
            dto.setMeetId(meetAudio.getMeetId());
            dto.setModuleId(meetAudio.getModuleId());
            dto.setId(meetAudio.getId());
            dto.setServerTime(new Date());
            dto.setCourseId(meetAudio.getCourseId());
            if(meetAudio.getCourse()!=null){
                AudioCourseDTO courseDTO = AudioCourseDTO.build(meetAudio.getCourse());
                dto.setCourse(courseDTO);
            }
        }
        return dto;
    }
}
