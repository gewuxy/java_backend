package cn.medcn.csp.dto;

import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.Live;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/10/24.
 */
@Data
@NoArgsConstructor
public class CspAudioCourseDTO implements Serializable {

    protected AudioCourse course;

    protected Live live;

}
