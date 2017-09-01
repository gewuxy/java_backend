package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/25.
 */
@Data
@NoArgsConstructor
public class CourseSharedDTO implements Serializable{

    private Integer id;

    private String title;

    private Integer reprintCount;

    private Boolean shared;
}
