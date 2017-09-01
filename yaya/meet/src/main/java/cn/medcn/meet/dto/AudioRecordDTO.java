package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/6/10.
 * ppt观看记录
 */
@Data
@NoArgsConstructor
public class AudioRecordDTO implements Serializable {
    // 用户ID
    private Integer id;

    // 用户姓名
    private String nickName;

    // 医院
    private String unitName;

    // 科室
    private String subUnitName;

    // 分组
    private String groupName;

    // ppt页码
    private Integer sort;

    // ppt页数 用户观看的总页数
    private Integer pptCount;

    // ppt 音频总时长
    private Integer duration;

    // 观看时长
    private Integer usedtime;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 是否观看完
    private Boolean finished;

    // 用户头像
    private String headimg;

    // 医院级别
    private String level;

    // 省份
    private String province;

    // 城市
    private String city;

    // 总时长
    private Integer time;

    // 职称
    private String title;

    // 会议名称
    private String meetName;

}
