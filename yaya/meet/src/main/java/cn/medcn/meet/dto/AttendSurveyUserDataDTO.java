package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by Liuchangling on 2017/7/14.
 * 参与问卷调查的用户数据
 */
@Data
@NoArgsConstructor
public class AttendSurveyUserDataDTO {
    // 用户ID
    private Integer userId;
    // 会议名称
    private String meetName;
    // 用户姓名
    private String nickname;
    // 是否参加问卷
    private Boolean attend;
    // 医院
    private String unitName;
    // 科室
    private String subUnitName;
    // 医院级别
    private String level;
    // 职称
    private String title;
    // 省份
    private String province;
    // 城市
    private String city;
    // 分组ID
    private Integer groupId;
    // 分组
    private String groupName;
    // 提交问卷时间
    private Date submitTime;
    // 用户题目选择答案
    private List QuestionOptItemDTO;

}
