package cn.medcn.meet.dto;

import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
public class AudioCourseDTO implements Serializable{
    private Integer id;

    private String title;

    private String category;

    // 封面url
    private String coverUrl;

    private List<AudioCourseDetailDTO> details;

    public static AudioCourseDTO build(AudioCourse course){
        AudioCourseDTO dto = new AudioCourseDTO();
        dto.setTitle(course.getTitle());
        dto.setCategory(course.getCategory());
        if(course.getDetails()!=null){
            List<AudioCourseDetailDTO> dtos = Lists.newArrayList();
            for(AudioCourseDetail detail:course.getDetails()){
                dtos.add(AudioCourseDetailDTO.build(detail));
            }
            dto.setDetails(dtos);
        }
        return dto;
    }


    public static void HandelCoverUrl(List<AudioCourseDTO> list, AudioCourseDTO courseDTO, String fileBase){
        if(list != null){
            for(AudioCourseDTO image : list){
                image.setCoverUrl(fileBase + image.getCoverUrl());
            }
        }
        if (courseDTO != null) {
            courseDTO.setCoverUrl(fileBase + courseDTO.getCoverUrl());
        }
    }
}
