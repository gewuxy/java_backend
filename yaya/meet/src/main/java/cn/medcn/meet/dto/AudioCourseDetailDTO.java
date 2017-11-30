package cn.medcn.meet.dto;

import cn.medcn.meet.model.AudioCourseDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
public class AudioCourseDetailDTO implements Serializable{
    private Integer id;
    /**排序号*/
    private Integer sort;
    /**ppt图片地址*/
    private String imgUrl;
    /**音频地址*/
    private String audioUrl;
    /**视频地址*/
    private String videoUrl;

    protected Boolean temp;

    public static AudioCourseDetailDTO build(AudioCourseDetail detail){
        AudioCourseDetailDTO dto = new AudioCourseDetailDTO();
        dto.setAudioUrl(detail.getAudioUrl()==null?"":detail.getAudioUrl());
        dto.setImgUrl(detail.getImgUrl()==null?"":detail.getImgUrl());
        dto.setSort(detail.getSort());
        dto.setTemp(detail.getTemp());
        dto.setVideoUrl(detail.getVideoUrl());
        dto.setId(detail.getId());
        return dto;
    }
}
