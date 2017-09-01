package cn.medcn.meet.dto;

import cn.medcn.meet.model.VideoCourse;
import cn.medcn.meet.model.VideoCourseDetail;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/5/17.
 */
@Data
@NoArgsConstructor
public class VideoCourseDTO implements Serializable {

    private Integer id;

    private String title;

    private List<VideoCourseDetailDTO> details;

    public static VideoCourseDTO build(VideoCourse course){
        VideoCourseDTO dto = new VideoCourseDTO();
        if(course != null){
            dto.setId(course.getId());
            dto.setTitle(course.getTitle());
            if(course.getDetails() != null){
                List<VideoCourseDetailDTO>  dtodetails = Lists.newArrayList();
                for(VideoCourseDetail detail: course.getDetails()){
                    dtodetails.add(VideoCourseDetailDTO.build(detail));
                }
                dto.setDetails(dtodetails);
            }
        }
        return dto;
    }
}
