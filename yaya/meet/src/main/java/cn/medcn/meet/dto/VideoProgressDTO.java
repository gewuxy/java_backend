package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/6/14.
 */
@Data
@NoArgsConstructor
public class VideoProgressDTO implements Serializable {

    // 视频ID
    private Integer id;

    // 视频名称
    private String name;

    // 视频时长
    private Integer duration;

    // 姓名
    private String nickname;

    // 观看时长
    private Integer usedtime;

    // 观看进度
    private String viewProgress;

 }
