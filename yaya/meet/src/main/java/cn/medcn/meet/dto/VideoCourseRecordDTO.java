package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/6/14.
 */
@Data
@NoArgsConstructor
public class VideoCourseRecordDTO implements Serializable{
    // 视频ID
    private Integer id;

    // 视频名称
    private String name;

    // 视频时长
    private Integer duration ;

    // 视频观看总人数
    private Integer totalCount ;
}
