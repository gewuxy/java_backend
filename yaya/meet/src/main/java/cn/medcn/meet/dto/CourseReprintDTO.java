package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/25.
 */
@Data
@NoArgsConstructor
public class CourseReprintDTO implements Serializable {

    private Integer id;

    private String title;

    private String category;

    private String pubUserName;

    private Integer credits;

    private Date createTime;

    private Integer owner;

    private Boolean reprinted;

    private Integer shareType;
}
