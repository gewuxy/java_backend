package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by Liuchangling on 2017/7/16.
 * 参与考试的用户数据
 */
@Data
@NoArgsConstructor
public class AttendExamHistoryDTO {

    // 用户ID
    private Integer userId;
    // 姓名
    private String nickname;
    // 医院
    private String unitName;
    // 科室
    private String subUnitName;
    // 提交考题时间
    private Date submitTime;
    // 成绩
    private Integer score;
    // 是否参与考试
    private Boolean attend;
    // 会议名称
    private String meetName;
    // 分组
    private String groupName;
    // 省份
    private String province;
    // 城市
    private String city;
    // 区
    private String zone;
    // 职称
    private String title;
    // 医院级别
    private String level;
    // 考试次数
    private Integer finishTimes;
    // 选择题目答案记录
  //  private List QuestionItemDTO;
}
