package cn.medcn.csp.dto;

import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.model.MeetWatermark;
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

    protected MeetWatermark watermark;

    public static void main(String[] args) {
        Integer a = new Integer(1);
        Integer b = new Integer(2);
        System.out.println(a == b);
    }
}
