package cn.medcn.csp.dto;

import cn.medcn.meet.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2018/1/10.
 */
@Data
@NoArgsConstructor
public class CspCourseInfoDTO implements Serializable {
    //课件信息
    protected AudioCourse course;
    //直播或者投屏用的websocket地址
    protected String wsUrl;
    //直播信息
    protected Live live;
    //录播信息
    protected AudioCoursePlay record;
    //背景图加背景音乐
    protected AudioCourseTheme theme;

    protected Date serverTime = new Date();

    public CspCourseInfoDTO(AudioCourse course, String wsUrl){
        this.course = course;
        this.wsUrl = wsUrl;
    }

}
