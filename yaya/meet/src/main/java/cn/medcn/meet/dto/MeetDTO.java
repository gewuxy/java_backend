package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/6/8.
 */
@Data
@NoArgsConstructor
public class MeetDTO implements Serializable {
    // 会议ID
    private String id;

    // 会议名称
    private String meetName;

    // 会议科室
    private String meetType;

    // 会议类型
    private String moduleName;
    private String moduleNames;

    // 会议时长
    private String meetTime;

    // 会议模块ID
    private Integer moduleId;

    // 会议功能ID
    private Integer functionId;



}
