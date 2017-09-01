package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by LiuLP on 2017/6/22/022.
 */
@Data
@NoArgsConstructor
public class PPTAudioDTO {

    private Integer courseId;

    private String meetId;

    private Integer moduleId;

    private String details;



}
