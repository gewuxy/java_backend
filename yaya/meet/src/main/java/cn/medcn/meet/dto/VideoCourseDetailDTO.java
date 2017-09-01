package cn.medcn.meet.dto;

import cn.medcn.meet.model.VideoCourseDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/17.
 */
@Data
@NoArgsConstructor
public class VideoCourseDetailDTO implements Serializable {

    private Integer id;
    /**父ID*/
    private Integer preId;
    /**文件夹或者视频名称*/
    private String name;
    /**0表示文件夹 1表示视频*/
    private Integer type;
    /**所属course的ID*/
    private Integer courseId;
    /**视频路径 type为视频的时候才有效*/
    private String url;
    /**视频类型 0表示内部链接 1表示外部链接*/
    private Integer videoType;
    /**视频时长*/
    private Integer duration;


    public static VideoCourseDetailDTO build(VideoCourseDetail detail){
        VideoCourseDetailDTO dto = new VideoCourseDetailDTO();
        if(detail != null){
            dto.setId(detail.getId());
            dto.setUrl(detail.getUrl());
            dto.setVideoType(detail.getVideoType());
            dto.setType(detail.getType());
            dto.setName(detail.getName());
            dto.setCourseId(detail.getCourseId());
            dto.setPreId(detail.getPreId());
        }
        return dto;
    }
}
