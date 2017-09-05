package cn.medcn.meet.dto;

import cn.medcn.meet.model.MeetLearningRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/8/10.
 */
@Data
@NoArgsConstructor
public class AttendMeetUserDetailDTO {
    // 用户ID
    private Integer id;
    // 会议ID
    private String meetId;
    // 参与日期
    private Date startTime;
    // 姓名
    private String nickName;
    // 医院
    private String unitName;
    // 科室
    private String subUnitName;
    // 手机
    private String mobile;
    // 联系邮箱
    private String username;
    // CME卡号
    private String cmeId;
    // 医院等级
    private String level;
    // 职称
    private String title;
    // 省份
    private String province;
    // 城市
    private String city;
    // 分组
    private String groupName;
    // 会议名称
    private String meetName;
    // 功能模块ID
    private Integer functionId;
    // 学习百分比
    private Integer completeProgress;
    // 学习用时(ppt内容和视频学习)；提交时间（考试、问卷）、签到时间
    private Long usedTime;

}
